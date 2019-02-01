package com.eebbk.bfc.sdk.behavior.cache;

import com.eebbk.bfc.sdk.behavior.cache.entity.PolicyInfo;
import com.eebbk.bfc.sdk.behavior.cache.factory.PolicyFactory;
import com.eebbk.bfc.sdk.behavior.cache.interfaces.ICachePolicy;
import com.eebbk.bfc.sdk.behavior.cache.policy.TimeCachePolicy;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.control.report.CountManager;
import com.eebbk.bfc.sdk.behavior.control.report.NotifyAgent;
import com.eebbk.bfc.sdk.behavior.control.report.entity.NotifyReportInfo;
import com.eebbk.bfc.sdk.behavior.db.DBManager;
import com.eebbk.bfc.sdk.behavior.db.entity.Record;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hesn
 * @function 缓存管理
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public class CacheManager {
    /** 策略列表 */
    private final List<ICachePolicy> mPolicyList = new ArrayList<ICachePolicy>();
    /** 无视缓存，需要直接入库的数据类型白名单 */
    private final Set<Integer> mEventTypeWhiteList = new HashSet<Integer>();
    private static CacheManager mInstance;
    private static final String TAG = "CacheManager";
    private static final String PUSH_DB_LOCK = "pushDBLock";

    public static CacheManager getInstance() {
        if(mInstance != null){
            return mInstance;
        }
        synchronized (CacheManager.class) {
            if (null == mInstance) {
                mInstance = new CacheManager();
            }
        }
        return mInstance;
    }

    /** 初始化 */
    public void init(){
        initPolicy();
        //初始化统计数量
        CountManager.getInstance().clean();
        CountManager.getInstance().addTotal(DBManager.getInstance().getRecordcount());
    }

    /**
     * 初始化策略
     */
    public void initPolicy(){
        //初始化策略
        setPolicy(ConfigAgent.getBehaviorConfig().cacheConfig.policyTypes);
        //初始化直接入库类型白名单
        setIgnoreCacheEventType(EType.TYPE_EXCEPTION);
    }

    /**
     * 数据添加到缓存
     * @param event
     */
    public synchronized void cache(IEvent event){
        if(event == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CACHE_EVENT_NULL_POINTER);
            return;
        }
        LogUtils.i(TAG, "插入一条行为采集信息:" + event.eventName());
        //数量统计
        CountManager.getInstance().addByEventType(event.eventType());
        //调式模式
        if(debugMode(event)){
            return;
        }
        //是否直接入库
        if(ignoreCache(event)){
            NotifyReportInfo info = new NotifyReportInfo(CountManager.getInstance().getCounts());
            if(info.getTotal() >= ConfigAgent.getBehaviorConfig().reportConfig.reportModeConfig.quantity){
                NotifyAgent.onChange(info);
            }
            return;
        }
        //保存到缓存池
        CachePool.getInstance().cache(event);
        if(save2DBAble(mPolicyList)){
            //所有数据入库
            push2DB();
            //通知上传模块数据变动
            NotifyAgent.onChange(new NotifyReportInfo(CountManager.getInstance().getCounts()));
        }
    }

    /**
     * 获取所有数据
     * @return
     */
    public List<Record> getData(int size){
        synchronized (TAG){
            push2DB();
            DBManager.getInstance().deleteRepeatRecord();
            List<Record> list = DBManager.getInstance().getRecordList(size);
            CountManager.getInstance().clean();
            return list;
        }
    }

    /**
     * 设置是否需要缓存
     * @param cacheable
     */
    public void setCacheable(boolean cacheable){
        ConfigAgent.getBehaviorConfig().cacheConfig.usable = cacheable;
    }

    /**
     * 所有缓存数据保存到数据库
     */
    public void push2DB(){
        synchronized (PUSH_DB_LOCK){
            if(CachePool.getInstance().isEmpty()){
                return;
            }
            List<IEvent> cacheList = CachePool.getInstance().getAll();
            CachePool.getInstance().clear();
            DBManager.getInstance().insertEvents(cacheList);
        }
    }

    /**
     * 添加缓存策略
     * @param policyTypes
     */
    public void setPolicy(int... policyTypes){
        clearAllPolicy(mPolicyList);
        if(isArrayEmpty(policyTypes)){
            return;
        }
        for (int policyType : policyTypes) {
            if(containsPolicy(mPolicyList, policyType)){
                //已经存在此策略，无需重复添加
                LogUtils.bfcWLog(TAG, "已经设置过缓存模式类型为:" + policyType + ",无需重复设置");
              continue;
            }
            ICachePolicy cachePolicy = PolicyFactory.createCachePolicy(policyType
            );
            if(cachePolicy != null){
                mPolicyList.add(cachePolicy);
                LogUtils.d(TAG, "成功设置缓存模式:" + cachePolicy.policyType());
            }
        }
    }

    /**
     * 设置无视缓存直接入库的事件类型
     * @param eventTypes
     */
    public void setIgnoreCacheEventType(int... eventTypes){
        if(isArrayEmpty(eventTypes)){
            return;
        }
        for (int eventType : eventTypes){
            mEventTypeWhiteList.add(eventType);
        }
    }

    /**
     * 此数据类型是否需要无视缓存直接保存入库
     * @param event
     * @return
     */
    private boolean ignoreCacheByEventType(IEvent event){
        return mEventTypeWhiteList.contains(event.eventType());
    }

    /**
     * 判断是否满足条件把数据保存到数据库
     * @return
     */
    private boolean save2DBAble(List<ICachePolicy> policyList){
        PolicyInfo policyInfo = getPolicyInfo();
        List<ICachePolicy> list = new ArrayList<>(policyList);
        for (ICachePolicy cachePolicy : list){
            if(cachePolicy == null){
                continue;
            }
            if(!cachePolicy.save2DBAble(policyInfo)){
                //只要有一个缓存策略不满足保存条件，就不让保存
                //相当于所有策略是‘与’的条件组合
                return false;
            }
        }
        cachePolicySyncData(mPolicyList);
        //没有添加策略或者所有策略都满足，则允许保存到数据库
        return true;
    }

    /**
     * 统一校准所有策略的临时数据
     */
    private void cachePolicySyncData(List<ICachePolicy> policyList){
        List<ICachePolicy> list = new ArrayList<>(policyList);
        for (ICachePolicy cachePolicy : list){
            if(cachePolicy == null){
                continue;
            }
            cachePolicy.syncData();
        }
    }

    /**
     * 设置所有策略用到的判断条件
     * @return
     */
    private PolicyInfo getPolicyInfo(){
        PolicyInfo policyInfo = new PolicyInfo();
        policyInfo.cachePoolSize = CachePool.getInstance().size();
        policyInfo.timeStamp = TimeCachePolicy.getTimeStamp();
        return policyInfo;
    }

    private boolean isArrayEmpty(int[] arr){
        return arr == null || arr.length <= 0;
    }

    /**
     * 删除所有缓存策略
     */
    private void clearAllPolicy(List<ICachePolicy> policyList){
        policyList.clear();
        LogUtils.d(TAG, "清空所有缓存模式");
    }

    /**
     * 是否已经设置了此缓存策略
     * @param policyType
     * @return
     */
    private boolean containsPolicy(List<ICachePolicy> policyList, int policyType){
        List<ICachePolicy> list = new ArrayList<>(policyList);
        for (ICachePolicy hasCachePolicy : list) {
            if(hasCachePolicy == null){
                continue;
            }
            if (hasCachePolicy.policyType() == policyType) {
                //策略池中已经添加了此策略，无需再添加
                return true;
            }
        }
        return false;
    }

    /**
     * 直接入库
     * @param event 采集数据
     * @return
     */
    private boolean ignoreCache(IEvent event){
        if(!ConfigAgent.getBehaviorConfig().cacheConfig.usable || ignoreCacheByEventType(event)){
//            LogUtils.d(TAG, "忽视缓存，直接入库");
            //白名单数据，单条数据直接入库
            DBManager.getInstance().insert(event);
            return true;
        }
        return false;
    }

    /**
     * 调式模式处理
     * @param event
     * @return
     */
    private boolean debugMode(IEvent event){
        if(ConfigAgent.getBehaviorConfig().isDebugMode){
            LogUtils.d(TAG, "当前为调式模式");
            CachePool.getInstance().cache(event);
            ReportAgent.doReport(ReportMode.MODE_REAL_TIME);
            return true;
        }
        return false;
    }

    private CacheManager(){
        //prevent the instance
    }
}
