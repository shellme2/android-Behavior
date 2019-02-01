package com.eebbk.bfc.sdk.behavior.control.collect;

import android.content.Context;
import android.os.SystemClock;

import com.eebbk.bfc.sdk.behavior.cache.CacheManager;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.BaseAttrManager;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.OtherAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.UserAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.AppLaunchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomAttrEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.FunctionBeginEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.FunctionEndEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.MonitorURLEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.PageEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.interfaces.ICollector;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.sdk.behavior.version.Build;

import java.util.HashMap;

/**
 * @author hesn
 * @function 采集模块的整体对外封装类
 * @date 16-8-16
 * @company 步步高教育电子有限公司
 */

public class CollectionManager implements ICollector {

    private static final String TAG = "CollectionManager";
    private final HashMap<String, Long> mPagbeginTimehashMap;// 记录进入页面的开始时间
    private static CollectionManager mInstance;

    public static CollectionManager getInstance() {
        if(mInstance != null){
            return mInstance;
        }
        synchronized (CollectionManager.class) {
            if (null == mInstance) {
                mInstance = new CollectionManager();
            }
        }
        return mInstance;
    }

    /**
     * app启动事件
     */
    public void appLaunch() {
        this.insert(new AppLaunchEvent());
    }

    @Deprecated
    @Override
    public void appLaunch(String tValue, String curActivityName) {
        AppLaunchEvent appLaunchEvent = new AppLaunchEvent();
        appLaunchEvent.activityName = curActivityName;
        appLaunchEvent.trigValue = tValue;
        this.insert(appLaunchEvent);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, Context context) {
        clickEvent(functionName, context == null ? "" : context.getPackageName(), null, null, null);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName) {
        clickEvent(functionName, curActivityName, null, null, null);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend) {
        clickEvent(functionName, curActivityName, extend, null, null);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend, String moduleDetail) {
        clickEvent(functionName, curActivityName, extend, moduleDetail, null);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        ClickEvent event = new ClickEvent();
        event.activity = curActivityName;
        event.functionName = functionName;
        event.moduleDetail = moduleDetail;
        event.extend = extend;
        event.dataAttr = dataAttr;
        this.insert(event);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(EventAttr eventAttr, DataAttr dataAttr) {
        clickEvent(eventAttr, dataAttr, null);
    }

    /**
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        this.insert(createCustomAttrEvent(
                EType.TYPE_CLICK,
                EType.NAME_CLICK,
                eventAttr,
                dataAttr,
                otherAttr));
    }

    /**
     * 计次事件
     * @param clickEvent
     */
    @Override
    public void clickEvent(ClickEvent clickEvent) {
        this.insert(clickEvent);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName) {
        customEvent(eventName, functionName, curActivityName, null, null, null);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend) {
        customEvent(eventName, functionName, curActivityName, extend, null, null);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail) {
        customEvent(eventName, functionName, curActivityName, extend, moduleDetail, null);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CustomEvent event = new CustomEvent();
        event.activity = curActivityName;
        //此处设置的eventName无效，会统一命名为‘自定义事件’
//        event.eventName = eventName;
        event.functionName = functionName;
        event.extend = extend;
        event.moduleDetail = moduleDetail;
        event.dataAttr = dataAttr;
        this.insert(event);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(EventAttr eventAttr, DataAttr dataAttr) {
        customEvent(eventAttr, dataAttr, null);
    }

    /**
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        this.insert(createCustomAttrEvent(
                EType.TYPE_CUSTOM,
                EType.NAME_CUSTOM,
                eventAttr,
                dataAttr,
                otherAttr));
    }

    /**
     * 自定义事件
     *
     * @param customEvent
     */
    @Override
    public void customEvent(CustomEvent customEvent) {
        this.insert(customEvent);
    }

    /**
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName) {
        recordFunctionBegin(functionName, curActivityName, null, null, null);
    }

    /**
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName, String extend, String moduleDetail) {
        recordFunctionBegin(functionName, curActivityName, extend, moduleDetail, null);
    }

    /**
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        FunctionBeginEvent event = new FunctionBeginEvent();
        event.activity = curActivityName;
        event.functionName = functionName;
        event.extend = extend;
        event.moduleDetail = moduleDetail;
        event.addAttr(dataAttr);
        if(event.recordTime()){
            this.insert(event);
        }
    }

    /**
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(EventAttr eventAttr, DataAttr dataAttr) {
        recordFunctionBegin(eventAttr, dataAttr, null);
    }

    /**
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        if(isAttrEmpty(eventAttr)){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_EVENT_ATTR);
            return;
        }
        FunctionBeginEvent event = new FunctionBeginEvent();
        event.activity = eventAttr.getPage();
        event.functionName = eventAttr.getFunctionName();
        event.moduleDetail = eventAttr.getModuleDetail();
        event.addAttr(dataAttr, otherAttr);
        if(event.recordTime()){
            this.insert(event);
        }
    }

    /**
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName) {
        recordFunctionEnd(functionName, curActivityName, null, null, null);
    }

    /**
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName, String extend, String moduleDetail) {
        recordFunctionEnd(functionName, curActivityName, extend, moduleDetail, null);
    }

    /**
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        FunctionEndEvent event = new FunctionEndEvent();
        event.activity = curActivityName;
        event.functionName = functionName;
        event.moduleDetail = moduleDetail;
        event.extend = extend;
        event.addAttr(dataAttr);
        if(event.calcuDuring()){
            this.insert(event);
        }
    }

    /**
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(EventAttr eventAttr, DataAttr dataAttr) {
        recordFunctionEnd(eventAttr, dataAttr, null);
    }

    /**
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        if(isAttrEmpty(eventAttr)){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_EVENT_ATTR);
            return;
        }
        FunctionEndEvent event = new FunctionEndEvent();
        event.activity = eventAttr.getPage();
        event.functionName = eventAttr.getFunctionName();
        event.moduleDetail = eventAttr.getModuleDetail();
        event.addAttr(dataAttr, otherAttr);
        if(event.calcuDuring()){
            this.insert(event);
        }
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, Context context) {
        searchEvent(functionName, content, context == null ? null : context.getClass().getName(),
                null, null, null);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName) {
        searchEvent(functionName, content, curActivityName, null, null, null);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend) {
        searchEvent(functionName, content, curActivityName, extend, null, null);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend, String moduleDetail) {
        searchEvent(functionName, content, curActivityName, extend, moduleDetail, null);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        SearchEvent event = new SearchEvent();
        event.functionName = functionName;
        event.keyWrod = content;
        event.activity = curActivityName;
        event.resultCount = extend;
        event.moduleDetail = moduleDetail;
        event.dataAttr = dataAttr;
        this.insert(event);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(EventAttr eventAttr, DataAttr dataAttr) {
        searchEvent(eventAttr, dataAttr, null);
    }

    /**
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        this.insert(createCustomAttrEvent(
                EType.TYPE_SEARCH,
                EType.NAME_SEARCH,
                eventAttr,
                dataAttr,
                otherAttr));
    }

    /**
     * 搜索事件
     *
     * @param searchEvent
     */
    @Override
    public void searchEvent(SearchEvent searchEvent) {
        this.insert(searchEvent);
    }

    /**
     * 页面切出事件
     */
    public void pageBegin(String activityName) {
        mPagbeginTimehashMap.put(activityName, SystemClock.elapsedRealtime());
    }

    /**
     * @deprecated 此函数的 functionName和 moduleDetail 参数传递无效
     * <p> 建议改用 pageBegin(String activityName) 配合 </p>
     * <p> pageEnd(String activityName, String functionName, String moduleDetail) </p>
     * <p> 完成 functionName和 moduleDetail 参数传递 </p>
     */
    @Deprecated
    @Override
    public void pageBegin(String activityName, String functionName, String moduleDetail) {
        mPagbeginTimehashMap.put(activityName, SystemClock.elapsedRealtime());
    }

    @Override
    public boolean pageEnd(String activityName) {
        return pageEnd(activityName, null, null, null, null);
    }

    @Override
    public boolean pageEnd(String activityName, String functionName, String moduleDetail) {
        return pageEnd(activityName, functionName, moduleDetail, null, null);
    }

    public boolean pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) {
        return pageEnd(activityName, functionName, moduleDetail, dataAttr, extend, null);
    }

    public boolean pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend, IAttr attr) {
        Long l = mPagbeginTimehashMap.remove(activityName);
        if(l == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_PAGE_ORDER, ", activityName:" + activityName);
            return false;
        }
        long duaring = SystemClock.elapsedRealtime() - l;
        PageEvent pageEvent = new PageEvent();
        pageEvent.activityName = activityName;
        pageEvent.functionName = functionName;
        pageEvent.moduleDetail = moduleDetail;
        pageEvent.dataAttr = dataAttr;
        pageEvent.extend = extend;
        pageEvent.duaring = String.valueOf(duaring);
        if(attr != null){
            pageEvent.addAttr(attr);
        }
        this.insert(pageEvent);
        return true;
    }

    @Deprecated
    @Override
    public void activityPaused(String curActivityName, long duaring, String pidName, String label) {
        PageEvent pageEvent = new PageEvent();
        pageEvent.activityName = curActivityName;
        pageEvent.functionName = pidName;
        pageEvent.moduleDetail = label;
        pageEvent.duaring = String.valueOf(duaring);
        this.insert(pageEvent);
    }

    /**
     * 计数事件
     *
     * @param countEvent
     */
    @Override
    public void countEvent(CountEvent countEvent) {
        this.insert(countEvent);
    }

    /**
     * @deprecated 建议改用 exceptionEvent(ExceptionEvent exceptionInfo)
     */
    @Deprecated
    @Override
    public void exceptionEvent(EventAttr eventAttr, DataAttr dataAttr) {
        this.insert(createCustomAttrEvent(
                EType.TYPE_EXCEPTION,
                EType.NAME_EXCEPTION,
                eventAttr,
                dataAttr));
    }

    /**
     * 异常事件
     *
     * @param exceptionInfo
     */
    @Override
    public void exceptionEvent(ExceptionEvent exceptionInfo) {
        this.insert(exceptionInfo);
    }

    @Override
    public void monitorURLEvent(MonitorURLEvent monitorurlevent) {
        this.insert(monitorurlevent);
    }

    /**初始化用户信息*/
    @Override
    public void initUserInfo(UserAttr userInfo) {
        BaseAttrManager.getInstance().setUserAttr(userInfo);
    }

    @Override
    public String getBehaviorVersion() {
        return Build.VERSION.VERSION_NAME;
    }

    /**
     * 返回用户信息
     * @return
     */
    public UserAttr getUserInfo() {
        return BaseAttrManager.getInstance().getUserAttr();
    }

    @Override
    public void realTime2Upload() {

    }

    /**
     * 可以插入自定义 IEvent 数据
     * @param event
     */
    public void event(IEvent event){
        this.insert(event);
    }

    /**
     * 设置是否需要缓存
     * @param cacheable
     */
    public void setCacheable(boolean cacheable){
        CacheManager.getInstance().setCacheable(cacheable);
    }

    /**
     * 设置缓存策略
     * @param policyTypes eg:PolicyType.TIME,PolicyType.CAPACITY
     */
    public void setCachePolicy(int...policyTypes){
        CacheManager.getInstance().setPolicy(policyTypes);
    }

    /**
     * 设置不需要缓存，直接入库的数据类型
     * @param eventTypes eg:EType.TYPE_APP_IN,EType.TYPE_EXCEPTION
     */
    public void setIgnoreCache(int...eventTypes){
        CacheManager.getInstance().setIgnoreCacheEventType(eventTypes);
    }

    /**
     * 退出应用处理
     */
    public void exit(){
        saveData2DB();
    }

    public void saveData2DB(){
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                CacheManager.getInstance().push2DB();
            }
        });
    }

    private CollectionManager() {
        mPagbeginTimehashMap = new HashMap<String, Long>();
    }

    /**
     * 入库
     * @param event 数据
     */
    private void insert(final IEvent event) {
        if(!ConfigAgent.getBehaviorConfig().usable){
            LogUtils.i(TAG, "EnableUpload=false,不需要上报");
            return;
        }
        if(ContextUtils.isEmpty()){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_INIT_INSERT);
            return;
        }
        if(event == null){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_IEVENT);
            return;
        }
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //触发生成数据
                event.makeData();
                CacheManager.getInstance().cache(event);
            }
        });
    }

    //创建自定义Attr的事件类型
    private CustomAttrEvent createCustomAttrEvent(int eventType, String eventName
        , EventAttr eventAttr, IAttr...attrs){
        if(isAttrEmpty(eventAttr)){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_NULL_POINTER_EVENT_ATTR);
            return null;
        }
        eventAttr.setEventType(eventType);
        eventAttr.setEventName(eventName);
        CustomAttrEvent event = new CustomAttrEvent();
        event.eventAttr = eventAttr;
        event.addAttr(attrs);
        return event;
    }

    private boolean isAttrEmpty(IAttr iAttr){
        return iAttr == null;
    }

}
