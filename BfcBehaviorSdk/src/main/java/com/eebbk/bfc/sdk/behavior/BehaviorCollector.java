package com.eebbk.bfc.sdk.behavior;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType;
import com.eebbk.bfc.sdk.behavior.control.collect.CollectionManager;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.BaseAttrManager;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.OtherAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.UserAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ExceptionEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.MonitorURLEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.interfaces.ICollector;
import com.eebbk.bfc.sdk.behavior.control.init.InitManager;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.report.analysis.interfaces.ISort;
import com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode;
import com.eebbk.bfc.sdk.behavior.report.control.ReportAgent;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.MapUtils;
import com.eebbk.bfc.sdk.behavior.utils.UriUtils;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.uploadsdk.upload.net.NetworkType;

import java.util.Arrays;
import java.util.Map;

/**
 * 对外调用接口类
 */
public final class BehaviorCollector implements ICollector {

    private static final String TAG = "BehaviorCollector";
    private Uri CONTENT_URI = null;

    private static class InstanceHolder {
        private static final BehaviorCollector mInstance = new BehaviorCollector();
    }

    public static final class Builder {
        private final BehaviorConfig mBuildConfig;

        public Builder(Application app) {
            ContextUtils.setContext(app);
            mBuildConfig = ConfigAgent.getBehaviorConfig();
        }

        /**
         * 开启/关闭 大数据采集
         *
         * @param enable 是否允许采集
         */
        public Builder enable(boolean enable) {
            mBuildConfig.usable = enable;
            return this;
        }

        /**
         * app在后台sessionTimeout后，再进入前台则调用app启动事件
         *
         * @param timeout 秒
         */
        public Builder sessionTimeout(long timeout) {
            mBuildConfig.sessionTimeout = timeout;
            return this;
        }

        /**
         * 调式模式
         * <p>调式模式下，数据直接上传<p/>
         *
         * @param enable 是否开启(默认关闭)
         * @return
         */
        public Builder setDebugMode(boolean enable) {
            mBuildConfig.isDebugMode = enable;
            return this;
        }

        /**
         * 自动采集app启动事件
         *
         * @param enable 是否自动采集，默认true
         */
        public Builder enableCollectLaunch(boolean enable) {
            mBuildConfig.enableCollectLaunch = enable;
            return this;
        }

        /**
         * 自动采集Activity界面的进入与退出
         *
         * @param enable 是否自动采集
         */
        public Builder openActivityDurationTrack(boolean enable) {
            mBuildConfig.openActivityDurationTrack = enable;
            return this;
        }

        /**
         * 开启/关闭 日志缓存
         *
         * @param enable
         * @return
         */
        public Builder enableLogCache(boolean enable) {
            mBuildConfig.isCacheLog = enable;
            return this;
        }

        /**
         * 开启/关闭 异常捕获
         *
         * @param enable
         * @return
         */
        public Builder enableCrash(boolean enable) {
            mBuildConfig.crashConfig.usable = enable;
            return this;
        }

        /**
         * 决定着要不要弹崩溃界面
         *
         * @param enable
         * @return
         */
        public Builder setCrashUIReport(boolean enable) {
            mBuildConfig.crashConfig.isUIReport = enable;
            return this;
        }

        /**
         * 异常时，弹Toast提示
         *
         * @param enable
         * @return
         */
        public Builder setCrashToast(boolean enable) {
            mBuildConfig.crashConfig.isToast = enable;
            return this;
        }

        /**
         * anr异常采集开关
         *
         * @param enable 默认true
         * @return
         */
        public Builder crashAnrEnable(boolean enable) {
            mBuildConfig.crashConfig.crashAnrEnable = enable;
            return this;
        }

        /**
         * 自动过滤信息不全的anr异常
         * <p>
         *   1.无trace的anr,不上报
         *   2.trace中无app包名的,不上报
         * <p/>
         * @param enable 默认true 过滤
         * @return
         */
        public Builder autoFilterAnr(boolean enable) {
            mBuildConfig.crashConfig.autoFilterAnr = enable;
            return this;
        }

        /**
         * 严苛模式捕获开关
         *
         * @param enable 默认false
         * @return
         */
        public Builder crashStrictModeEnable(boolean enable) {
            mBuildConfig.crashConfig.crashStrictModeEnable = enable;
            return this;
        }

        /**
         * native异常捕获开关
         *
         * @param enable 默认true
         * @return
         */
        public Builder crashNativeEnable(boolean enable) {
            mBuildConfig.crashConfig.crashNativeEnable = enable;
            return this;
        }

        /**
         * 忽略设置的时间之前的异常信息
         * <p>
         *     anr异常采集,严苛模式异常采集,native异常采集 基本都是通过获取dropbox中保存的信息.
         *     dropbox中是基本永久保持,所以如果初次集成这些采集,或者app数据被清除,会出现把以前旧数据从新上报的现象,可能会影响当前版本异常数据的统计.
         *     预留此接口,app根据自己需求选择需不需要忽略某个时间之前的异常信息
         * <p/>
         *
         * @param type  {@link com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType#TYPE_ALL} 所有异常采集都设置,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType#TYPE_ANR} ANR异常采集,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType#TYPE_STRICT_MODE} 严苛模式异常采集,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType#TYPE_NATIVE} native异常采集
         * @param timeMillis    无视此时间戳之前的所以dropbox中的异常
         * @return
         */
        public Builder ignoreBeforeCrash(String type, long timeMillis) {
            if(TextUtils.equals(type, CrashType.TYPE_ALL)){
                mBuildConfig.crashConfig.ignoreBeforeAnr = timeMillis;
                mBuildConfig.crashConfig.ignoreBeforeStrictMode = timeMillis;
                mBuildConfig.crashConfig.ignoreBeforeNative = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_ANR)){
                mBuildConfig.crashConfig.ignoreBeforeAnr = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_STRICT_MODE)){
                mBuildConfig.crashConfig.ignoreBeforeStrictMode = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_NATIVE)){
                mBuildConfig.crashConfig.ignoreBeforeNative = timeMillis;
                return this;
            }
            return this;
        }

        /**
         * 开启/关闭 入库缓存
         * <p>为了性能优化，减少频繁数据库操作</p>
         *
         * @param enable 开启关闭入库缓存功能(默认关闭)
         */
        public Builder enableCache(boolean enable) {
            mBuildConfig.cacheConfig.usable = enable;
            return this;
        }

        /**
         * 设置缓存策略
         *
         * @param policyTypes 策略类型: <br/>
         *              {@link com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType#TIME} 时间缓存策略,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType#CAPACITY} 容量缓存策略
         */
        public Builder setCachePolicy(int... policyTypes) {
            mBuildConfig.cacheConfig.policyTypes = policyTypes;
            return this;
        }

        /**
         * 设置不需要缓存，直接入库的数据类型
         *
         * @param eventTypes 无视缓存的数据类型 eg:EType.TYPE_APP_IN,EType.TYPE_EXCEPTION
         */
        public Builder setCacheIgnore(int... eventTypes) {
            mBuildConfig.cacheConfig.ignoreCacheEventType = eventTypes;
            return this;
        }

        /**
         * 开启/关闭 上传功能
         *
         * @param enable 开启关闭上传功能(默认开启)
         * @return
         */
        public Builder enableReport(boolean enable) {
            mBuildConfig.reportConfig.usable = enable;
            return this;
        }

        /**
         * 上报模式
         *
         * @param mode 上报类型:<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_LAUNCH} 启动上报模式,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_EXIT} 退出上报模式,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_QUANTITY} 定量上传模式,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_FIXTIME} 定时上传模式,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_PERIOD} 周期性上报模式,<br/>
         *              {@link com.eebbk.bfc.sdk.behavior.report.common.constants.ReportMode#MODE_REAL_TIME} 即时上报模式
         * @return
         */
        public Builder setReportMode(int...mode) {
            if(mode == null){
                return this;
            }
            Arrays.sort(mode);
            mBuildConfig.reportConfig.reportModeType = mode;
            return this;
        }

        /**
         * 数据过滤策略
         * <p> 上报时的数据过滤策略即数据的上报优先级 若null则不过滤 </p>
         *
         * @param sort 过滤策略
         *             <p> {@link com.eebbk.bfc.sdk.behavior.report.analysis.sort.SortByTime} 通过数据时间过滤 </p>
         *             <p> {@link com.eebbk.bfc.sdk.behavior.report.analysis.sort.SortByType} 通过数据类型过滤 </p>
         *             <p> {@link com.eebbk.bfc.sdk.behavior.report.analysis.sort.SortByPriority} 通过数据优先级过滤 </p>
         * @return
         */
        public Builder setReportSort(ISort sort) {
            mBuildConfig.reportConfig.sort = sort;
            return this;
        }

        /**
         * 只在wifie环境下上报
         *
         * @param isOnlyWifi
         * @return
         * @deprecated 建议改用 setNetworkTypes()
         */
        @Deprecated
        public Builder setReportOnlyWifi(boolean isOnlyWifi) {
            return setNetworkTypes(isOnlyWifi ? NetworkType.NETWORK_WIFI : ( NetworkType.NETWORK_WIFI | NetworkType.NETWORK_MOBILE));
        }

        /**
         * 设置是否无视用户体验改进计划
         * <br> 此接口为步步高机器特有接口,非步步高机器(下海版本)建议设置为true
         * <br> 默认为true,无视用户体验改进计划
         *
         * @param isIgnoreUserPlan false:不无视,如果在 设置-安全与隐私 中未加入用户体验改进计划,则采集的数据不会上报
         * @return
         */
        public Builder setIgnoreUserPlan(boolean isIgnoreUserPlan) {
            mBuildConfig.isIgnoreUserPlan = isIgnoreUserPlan;
            return this;
        }

        /**
         * 重试上传大文件
         * <p>
         * 重试上传OOM文件
         * <p/>
         *
         * @param enable
         * @return
         */
        public Builder enableRetryLargeFile(boolean enable) {
            mBuildConfig.isRetryLargeFile = enable;
            return this;
        }

        /**
         * <pre>上报埋点数据可用的网络类型：
         *  {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI}
         * , {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_MOBILE}
         * <br/>
         * 默认只有 wifi {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI}
         * <br/>
         * 如需使用多种类型的网络请用“|”连接，如 int networkTypes = {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI} | {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_MOBILE};
         * </pre>
         *
         * @param networkType 网络类型
         * @return 任务构造器
         */
        public Builder setNetworkTypes(int networkType) {
            mBuildConfig.reportConfig.mNetworkTypes = networkType;
            return this;
        }

        /**
         * 设置所有埋点的模块名
         * @param moduleName
         * @return
         */
        public Builder setModuleName(String moduleName) {
            BaseAttrManager.getInstance().setModuleName(moduleName);
            return this;
        }

        public BehaviorConfig build() {
            return mBuildConfig;
        }
    }

    public static BehaviorCollector getInstance() {
        return InstanceHolder.mInstance;
    }

    /**
     * 大数据采集初始化
     * <p> 必须调用此函数，才能正常使用大数据采集，在自定义application中需要用户主动调用 <p/>
     *
     * @param behaviorConfig 配置信息
     */
    public void init(@NonNull BehaviorConfig behaviorConfig) {
        ConfigAgent.setBehaviorConfig(behaviorConfig);
        init(ContextUtils.getContext());
    }

    /**
     * 大数据采集初始化
     * <p> 必须调用此函数，才能正常使用大数据采集，在自定义application中需要用户主动调用 <p/>
     *
     * @param app application
     */
    public void init(@NonNull Application app) {
        if (app == null) {
            LogUtils.e(TAG, "BFC DA init fail！Application can not be null!!!");
            return;
        }
        ContextUtils.setContext(app);
        LogUtils.v("init() mContext");
        CONTENT_URI = UriUtils.getContentUri(app);
        InitManager.init();
    }

    /**
     * 大数据采集开关
     *
     * @param enable 是否允许采集
     */
    public void enableUpload(boolean enable) {
        ConfigAgent.getBehaviorConfig().usable = enable;
    }

    /**
     * 自动采集Activity界面的进入与退出
     *
     * @param isOpen 是否自动采集
     */
    public void openActivityDurationTrack(boolean isOpen) {
        ConfigAgent.getBehaviorConfig().openActivityDurationTrack = isOpen;
    }

    /**
     * 是否开启了自动采集activity页面切换信息
     *
     * @return
     */
    public boolean getActivityDurationTrack() {
        return ConfigAgent.getBehaviorConfig().openActivityDurationTrack;
    }

    /**
     * app启动事件
     */
    @Override
    public void appLaunch() {
        CollectionManager.getInstance().appLaunch();
    }

    /**
     * app启动事件
     *
     * @param tValue          进入的时间
     * @param curActivityName 进入的activity
     * @deprecated 建议改用 appLaunch()
     */
    @Deprecated
    @Override
    public void appLaunch(String tValue, String curActivityName) {
        CollectionManager.getInstance().appLaunch(tValue, curActivityName);
    }

    /**
     * 计次事件(点击事件)
     *
     * @param functionName 功能名称
     * @param context      Context
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, Context context) {
        CollectionManager.getInstance().clickEvent(functionName, context);
    }

    /**
     * 计次事件(点击事件)
     *
     * @param functionName    功能名称
     * @param curActivityName 进入的activity
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName) {
        CollectionManager.getInstance().clickEvent(functionName, curActivityName);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 进入的activity
     * @param extend          扩展信息
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend) {
        CollectionManager.getInstance().clickEvent(functionName, curActivityName, extend);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 进入的activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend, String moduleDetail) {
        CollectionManager.getInstance().clickEvent(functionName, curActivityName, extend, moduleDetail);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 进入的activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @param dataAttr        数据属性
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CollectionManager.getInstance().clickEvent(functionName, curActivityName, extend, moduleDetail, dataAttr);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().clickEvent(eventAttr, dataAttr);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @param otherAttr 其它属性
     * @deprecated 建议改用 clickEvent(ClickEvent clickEvent)
     */
    @Deprecated
    @Override
    public void clickEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        CollectionManager.getInstance().clickEvent(eventAttr, dataAttr, otherAttr);
    }

    /**
     * 计次事件(点击事件)
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param clickEvent 事件属性
     */
    @Override
    public void clickEvent(@NonNull ClickEvent clickEvent) {
        CollectionManager.getInstance().clickEvent(clickEvent);
    }

    /**
     * 自定义事件
     *
     * @param eventName       事件名称
     * @param functionName    功能名称
     * @param curActivityName 当前activity
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName) {
        CollectionManager.getInstance().customEvent(eventName, functionName, curActivityName);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventName       事件名称
     * @param functionName    功能名称
     * @param curActivityName 当前activity
     * @param extend          扩展信息
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend) {
        CollectionManager.getInstance().customEvent(eventName, functionName, curActivityName, extend);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventName       事件名称
     * @param functionName    功能名称
     * @param curActivityName 当前activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail) {
        CollectionManager.getInstance().customEvent(eventName, functionName, curActivityName, extend, moduleDetail);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventName       事件名称
     * @param functionName    功能名称
     * @param curActivityName 当前activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @param dataAttr        数据属性
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(String eventName, String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CollectionManager.getInstance().customEvent(eventName, functionName, curActivityName, extend, moduleDetail, dataAttr);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().customEvent(eventAttr, dataAttr);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @param otherAttr 其它属性
     * @deprecated 建议改用 customEvent(CustomEvent customEvent)
     */
    @Deprecated
    @Override
    public void customEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        CollectionManager.getInstance().customEvent(eventAttr, dataAttr, otherAttr);
    }

    /**
     * 自定义事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param customEvent 事件信息
     */
    @Override
    public void customEvent(@NonNull CustomEvent customEvent) {
        CollectionManager.getInstance().customEvent(customEvent);
    }

    /**
     * 计数事件
     * <p> 带扩展信息（次数 + 扩展信息） </p>
     *
     * @param countEvent 事件信息
     */
    @Override
    public void countEvent(@NonNull CountEvent countEvent) {
        CollectionManager.getInstance().countEvent(countEvent);
    }

    /**
     * 记录功能点开始
     * <p>（时长 + 次数）</p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName) {
        CollectionManager.getInstance().recordFunctionBegin(functionName, curActivityName);
    }

    /**
     * 记录功能点开始
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName, String extend, String moduleDetail) {
        CollectionManager.getInstance().recordFunctionBegin(functionName, curActivityName, extend, moduleDetail);
    }

    /**
     * 记录功能点开始
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @param dataAttr        数据属性
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CollectionManager.getInstance().recordFunctionBegin(functionName, curActivityName, extend, moduleDetail, dataAttr);
    }

    /**
     * 记录功能点开始
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().recordFunctionBegin(eventAttr, dataAttr);
    }

    /**
     * 记录功能点开始
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @param otherAttr 其它属性
     * @deprecated 建议改用 pageBegin(String activityName) 非跨进程
     */
    @Deprecated
    @Override
    public void recordFunctionBegin(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        CollectionManager.getInstance().recordFunctionBegin(eventAttr, dataAttr, otherAttr);
    }

    /**
     * 记录功能点结束
     * <p>（时长 + 次数）</p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName) {
        CollectionManager.getInstance().recordFunctionEnd(functionName, curActivityName);
    }

    /**
     * 记录功能点结束
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName, String extend, String moduleDetail) {
        CollectionManager.getInstance().recordFunctionEnd(functionName, curActivityName, extend, moduleDetail);
    }

    /**
     * 记录功能点结束
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param functionName    功能名称
     * @param curActivityName 当前界面名称
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @param dataAttr        数据属性
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(String functionName, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CollectionManager.getInstance().recordFunctionEnd(functionName, curActivityName, extend, moduleDetail, dataAttr);
    }

    /**
     * 记录功能点结束
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用
     * <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     * <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().recordFunctionEnd(eventAttr, dataAttr);
    }

    /**
     * 记录功能点结束
     * <p> 带扩展信息（时长 + 次数 + 扩展信息） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @param otherAttr 其它属性
     *                  <p> pageEnd(String activityName, String functionName, String moduleDetail, DataAttr dataAttr, String extend) </p>
     *                  <p> 非跨进程 </p>
     */
    @Deprecated
    @Override
    public void recordFunctionEnd(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        CollectionManager.getInstance().recordFunctionEnd(eventAttr, dataAttr, otherAttr);
    }

    /**
     * 搜索事件
     * <p> （次数 + 内容） </p>
     *
     * @param functionName 功能名称
     * @param content      搜索的内容
     * @param context      Context
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, Context context) {
        CollectionManager.getInstance().searchEvent(functionName, content, context);
    }

    /**
     * 搜索事件
     * <p> （次数 + 内容） </p>
     *
     * @param functionName    功能名称
     * @param content         搜索的内容
     * @param curActivityName 当前activity
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName) {
        CollectionManager.getInstance().searchEvent(functionName, content, curActivityName);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息） </p>
     *
     * @param functionName    功能名称
     * @param content         搜索的内容
     * @param curActivityName 当前activity
     * @param extend          扩展信息
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend) {
        CollectionManager.getInstance().searchEvent(functionName, content, curActivityName, extend);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息） </p>
     *
     * @param functionName    功能名称
     * @param content         搜索的内容
     * @param curActivityName 进入的activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend, String moduleDetail) {
        CollectionManager.getInstance().searchEvent(functionName, content, curActivityName, extend, moduleDetail);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息 + 数据属性） </p>
     *
     * @param functionName    功能名称
     * @param content         搜索的内容
     * @param curActivityName 进入的activity
     * @param extend          扩展信息
     * @param moduleDetail    模块详细
     * @param dataAttr        数据属性
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(String functionName, String content, String curActivityName, String extend, String moduleDetail, DataAttr dataAttr) {
        CollectionManager.getInstance().searchEvent(functionName, content, curActivityName, extend, moduleDetail, dataAttr);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息 + 数据属性） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().searchEvent(eventAttr, dataAttr);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息 + 数据属性） </p>
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @param otherAttr 其它属性
     * @deprecated 建议改用 searchEvent(SearchEvent searchEvent)
     */
    @Deprecated
    @Override
    public void searchEvent(EventAttr eventAttr, DataAttr dataAttr, OtherAttr otherAttr) {
        CollectionManager.getInstance().searchEvent(eventAttr, dataAttr, otherAttr);
    }

    /**
     * 搜索事件
     * <p> 带扩展信息（次数 + 内容 + 附加信息 + 数据属性） </p>
     *
     * @param searchEvent 事件属性
     */
    @Override
    public void searchEvent(@NonNull SearchEvent searchEvent) {
        CollectionManager.getInstance().searchEvent(searchEvent);
    }

    /**
     * 界面进入事件
     *
     * @param page 为 activity或fragment
     */
    @Override
    public void pageBegin(String page) {
        CollectionManager.getInstance().pageBegin(page);
    }

    /**
     * 界面进入事件
     *
     * @param page         为 activity或fragment
     * @param functionName 为 功能名称
     * @param moduleDetail 为 模块详细
     * @deprecated 此函数的 functionName和 moduleDetail 参数传递无效
     * <p> 建议改用 pageBegin(String activityName) 配合 </p>
     * <p> pageEnd(String activityName, String functionName, String moduleDetail) </p>
     * <p> 完成 functionName和 moduleDetail 参数传递 </p>
     */
    @Deprecated
    @Override
    public void pageBegin(String page, String functionName, String moduleDetail) {
        CollectionManager.getInstance().pageBegin(page, functionName, moduleDetail);
    }

    /**
     * 界面退出事件
     *
     * @param page 为 activity或fragment
     * @return
     */
    @Override
    public boolean pageEnd(String page) {
        return CollectionManager.getInstance().pageEnd(page, null, null, null, null);
    }

    /**
     * 界面退出事件
     *
     * @param page         为 activity或fragment
     * @param functionName 为 功能名称
     * @param moduleDetail 为 模块详细
     * @return
     */
    @Override
    public boolean pageEnd(String page, String functionName, String moduleDetail) {
        return CollectionManager.getInstance().pageEnd(page, functionName, moduleDetail, null, null);
    }

    /**
     * 界面退出事件
     *
     * @param page         为 activity或fragment
     * @param functionName 为 功能名称
     * @param moduleDetail 为 模块详细
     * @param dataAttr     数据属性
     * @param extend       扩展属性
     * @return
     */
    public boolean pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, Map extend) {
        return CollectionManager.getInstance().pageEnd(page, functionName, moduleDetail, dataAttr, MapUtils.map2Json(extend));
    }

    /**
     * 界面退出事件
     *
     * @param page         为 activity或fragment
     * @param functionName 为 功能名称
     * @param moduleDetail 为 模块详细
     * @param dataAttr     数据属性
     * @param extend       扩展属性
     * @return
     */
    @Override
    public boolean pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, String extend) {
        return CollectionManager.getInstance().pageEnd(page, functionName, moduleDetail, dataAttr, extend);
    }

    /**
     * 界面退出事件
     *
     * @param page         为 activity或fragment
     * @param functionName 为 功能名称
     * @param moduleDetail 为 模块详细
     * @param dataAttr     数据属性
     * @param extend       扩展属性
     * @return
     */
    public boolean pageEnd(String page, String functionName, String moduleDetail, DataAttr dataAttr, String extend , IAttr attr) {
        return CollectionManager.getInstance().pageEnd(page, functionName, moduleDetail, dataAttr, extend, attr);
    }

    /**
     * 界面时长统计
     *
     * @param curActivityName 当前的activity
     * @param duaring         本次在该 activity 的停留时间
     * @param pidName
     * @param label
     */
    @Deprecated
    @Override
    public void activityPaused(String curActivityName, long duaring, String pidName, String label) {
        CollectionManager.getInstance().activityPaused(curActivityName, duaring, pidName, label);
    }

    /**
     * 异常事件
     *
     * @param eventAttr 事件属性
     * @param dataAttr  数据属性
     * @deprecated 建议改用 exceptionEvent(ExceptionEvent exceptionInfo)
     */
    @Deprecated
    @Override
    public void exceptionEvent(EventAttr eventAttr, DataAttr dataAttr) {
        CollectionManager.getInstance().exceptionEvent(eventAttr, dataAttr);
    }

    /**
     * 异常事件
     *
     * @param exceptionInfo 事件属性
     */
    @Override
    public void exceptionEvent(@NonNull ExceptionEvent exceptionInfo) {
        CollectionManager.getInstance().exceptionEvent(exceptionInfo);
    }

    /**
     * URL监控事件
     *
     * @param monitorurlevent 事件属性
     */
    @Override
    public void monitorURLEvent(@NonNull MonitorURLEvent monitorurlevent) {
        CollectionManager.getInstance().monitorURLEvent(monitorurlevent);
    }

    /**
     * 初始化用户信息
     *
     * @param userInfo 用户属性
     */
    @Override
    public void initUserInfo(@NonNull UserAttr userInfo) {
        CollectionManager.getInstance().initUserInfo(userInfo);
    }

    /**
     * 行为采集jar包版本
     *
     * @return
     */
    @Override
    public String getBehaviorVersion() {
        return TextUtils.concat(
                SDKVersion.getLibraryName(), ", version: ", SDKVersion.getVersionName(),
                " code: ", String.valueOf(SDKVersion.getSDKInt()),
                " build: ", SDKVersion.getBuildName()
        ).toString();
    }

    /**
     * 马上上传所有数据
     */
    @Override
    public void realTime2Upload() {
        ReportAgent.doReport(ReportMode.MODE_REAL_TIME);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserAttr getUserInfo() {
        return CollectionManager.getInstance().getUserInfo();
    }

    /**
     * 自定义重写事件
     * <p> 预留扩展类型事件 </p>
     * <p> 应用可以自己继承AEvent,实现定制化事件数据 </p>
     *
     * @param event
     */
    public void event(@NonNull IEvent event) {
        CollectionManager.getInstance().event(event);
    }

    /**
     * 主动杀死线程前调用
     */
    public void onKillProcess() {
        CollectionManager.getInstance().exit();
    }

    /**
     * 设置大数据采集配置信息
     *
     * @param behaviorConfig 配置信息
     */
    public void setConfig(@NonNull BehaviorConfig behaviorConfig) {
        ConfigAgent.setBehaviorConfig(behaviorConfig);
        ReportAgent.initReport();
    }

    public Map<String, String> getErrorCodes() {
        return ErrorCode.getErrorCodes();
    }

    /**
     * 获取大数据采集配置信息
     *
     * @return
     */
    public BehaviorConfig getConfig() {
        return ConfigAgent.getBehaviorConfig();
    }

    public Uri getContentUri() {
        return CONTENT_URI;
    }

    /**
     * <pre>上报埋点数据可用的网络类型：
     *  {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI}
     * , {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_MOBILE}
     * <br/>
     * 默认只有 wifi {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI}
     * <br/>
     * 如需使用多种类型的网络请用“|”连接，如 int networkTypes = {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_WIFI} | {@link com.eebbk.bfc.uploadsdk.upload.net.NetworkType#NETWORK_MOBILE};
     * </pre>
     *
     * @param networkType 网络类型
     */
    public void setNetworkTypes(int networkType) {
        ConfigAgent.getBehaviorConfig().reportConfig.mNetworkTypes = networkType;
    }

    private BehaviorCollector() {
    }
}
