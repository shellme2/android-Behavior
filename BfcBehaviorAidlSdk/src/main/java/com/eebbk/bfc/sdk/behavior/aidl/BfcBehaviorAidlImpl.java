package com.eebbk.bfc.sdk.behavior.aidl;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.eebbk.bfc.sdk.behavior.aidl.crash.CrashHandler;
import com.eebbk.bfc.sdk.behavior.aidl.crash.check.AidlAnrCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.aidl.crash.check.AidlNativeCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.aidl.crash.check.AidlStrictModeCheckExceptionImpl;
import com.eebbk.bfc.sdk.behavior.aidl.crash.check.CheckExceptionAgent;
import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;
import com.eebbk.bfc.sdk.behavior.aidl.listener.OnServiceConnectionListener;
import com.eebbk.bfc.sdk.behavior.aidl.utils.AppUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.aidl.utils.ExecutorsUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.LogUtils;
import com.eebbk.bfc.sdk.behavior.aidl.utils.StorageUtils;
import com.eebbk.bfc.sdk.behavior.aidl.version.SDKVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 17-4-10
 * @company 步步高教育电子有限公司
 */

public class BfcBehaviorAidlImpl implements BfcBehaviorAidl {

    private static final String TAG = "BfcBehaviorAidlImpl";
    private final AidlManager mAidlManager;
    private final ListenerManager mListenerManager;
    private BCLifeCycleCallback mBCLifeCycleCallback;
    private Application mApplication;

    // registerActivityLifecycleCallbacks 注册会比绑定服务要早,会出现服务没有绑定好就需要埋点操作,
    // 所以添加个缓存.可能会导致绑定服务的activity的页面及其之前的界面统计时间有一点点偏差,但是应该能忍
    private List<CommandInfo> cacheCommand = new ArrayList<>();

    /**
     * @param application
     * @param settings
     */
    public BfcBehaviorAidlImpl(Application application, Settings settings, ListenerManager listenerManager) {
        Log.i(TAG, "init " + getVersion());
        mApplication = application;
        setSettings(settings);
        mAidlManager = new AidlManager(mInnerListener, settings);
        mListenerManager = listenerManager;
        initAppConfig();
    }

    @Override
    public void appLaunch() {
        event(EType.TYPE_APP_IN, null, null, null, null, null, null
                , null, null, null, null, null, null, null);
    }

    /**
     * 计次事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param extend          扩展信息
     */
    @Override
    public void clickEvent(String curActivityName, String functionName, String moduleDetail, String extend) {
        clickEvent(curActivityName, functionName, moduleDetail, extend
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 计次事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param extend          扩展信息
     * @param dataId          数据ID
     * @param dataTitle       数据标题
     * @param dataEdition     数据版本
     * @param dataType        数据类型
     * @param dataGrade       数据年级
     * @param dataSubject     数据科目
     * @param dataPublisher   数据出版者
     * @param dataExtend      数据扩展
     */
    @Override
    public void clickEvent(String curActivityName, String functionName, String moduleDetail, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        event(EType.TYPE_CLICK, curActivityName, functionName, moduleDetail, null, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * 计数事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
     * @param extend          扩展信息
     */
    @Override
    public void countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend) {
        countEvent(curActivityName, functionName, moduleDetail, trigValue, extend
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 计数事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
     * @param extend          扩展信息
     * @param dataId          数据ID
     * @param dataTitle       数据标题
     * @param dataEdition     数据版本
     * @param dataType        数据类型
     * @param dataGrade       数据年级
     * @param dataSubject     数据科目
     * @param dataPublisher   数据出版者
     * @param dataExtend      数据扩展
     */
    @Override
    public void countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        event(EType.TYPE_COUNT, curActivityName, functionName, moduleDetail, trigValue, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * 自定义事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发值
     * @param extend          扩展信息
     */
    @Override
    public void customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend) {
        customEvent(curActivityName, functionName, moduleDetail, trigValue, extend
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 自定义事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发值
     * @param extend          扩展信息
     * @param dataId          数据ID
     * @param dataTitle       数据标题
     * @param dataEdition     数据版本
     * @param dataType        数据类型
     * @param dataGrade       数据年级
     * @param dataSubject     数据科目
     * @param dataPublisher   数据出版者
     * @param dataExtend      数据扩展
     */
    @Override
    public void customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        event(EType.TYPE_CUSTOM, curActivityName, functionName, moduleDetail, trigValue, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * 搜索事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param keyWord         搜索的关键字
     * @param resultCount     搜索结果
     */
    @Override
    public void searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount) {
        searchEvent(curActivityName, functionName, moduleDetail, keyWord, resultCount
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 搜索事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param keyWord         搜索的关键字
     * @param resultCount     搜索结果
     * @param dataId          数据ID
     * @param dataTitle       数据标题
     * @param dataEdition     数据版本
     * @param dataType        数据类型
     * @param dataGrade       数据年级
     * @param dataSubject     数据科目
     * @param dataPublisher   数据出版者
     * @param dataExtend      数据扩展
     */
    @Override
    public void searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        event(EType.TYPE_SEARCH, curActivityName, functionName, moduleDetail, keyWord, resultCount
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * 界面进入事件
     *
     * @param page 进入的activity
     */
    @Override
    public void pageBegin(@NonNull String page) {
        event(EType.TYPE_ACTIVITY_IN, page, null, null, null, null
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 页面退出事件
     *
     * @param page         进入的activity
     * @param functionName 功能名称
     * @param moduleDetail 模块详细
     * @param extend       扩展信息
     */
    @Override
    public void pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend) {
        pageEnd(page, functionName, moduleDetail, extend
                , null, null, null, null, null, null, null, null);
    }

    /**
     * 页面退出事件
     *
     * @param page          进入的activity
     * @param functionName  功能名称
     * @param moduleDetail  模块详细
     * @param extend        扩展信息
     * @param dataId        数据ID
     * @param dataTitle     数据标题
     * @param dataEdition   数据版本
     * @param dataType      数据类型
     * @param dataGrade     数据年级
     * @param dataSubject   数据科目
     * @param dataPublisher 数据出版者
     * @param dataExtend    数据扩展
     */
    @Override
    public void pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend, String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        event(EType.TYPE_ACTIVITY_OUT, page, functionName, moduleDetail, null, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    @Override
    public void event(int type, Map<String, String> map) {
        if (!ConfigAgent.getBehaviorConfig().enable) {
            return;
        }

        if (map == null || map.isEmpty()) {
            return;
        }

        mAidlManager.event(type, map);
    }

    /**
     * (以后扩展用)
     *
     * @param type
     * @param json
     * @return
     */
    @Override
    public String eventJson(int type, @NonNull String json) {
        if (!ConfigAgent.getBehaviorConfig().enable) {
            return null;
        }
        return mAidlManager.eventJson(type, json);
    }

    /**
     * 马上上传所有数据
     */
    @Override
    public void realTime2Upload() {
        mAidlManager.realTime2Upload();
    }

    /**
     * 获取行为采集库版本信息
     *
     * @return
     */
    @Override
    public String getBehaviorVersion() {
        return mAidlManager.getBehaviorVersion();
    }

    /**
     * 获取行为采集aidl库版本信息
     *
     * @return
     */
    @Override
    public String getVersion() {
        return TextUtils.concat(SDKVersion.getLibraryName(),
                "\nversion: ", SDKVersion.getVersionName(),
                "\ncode: ", String.valueOf(SDKVersion.getSDKInt()),
                "\nbuild: ", SDKVersion.getBuildName()
        ).toString();
    }

    /**
     * 绑定服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     */
    @Override
    public boolean bindService(@NonNull Context context) {
        return bindService(context, context.getPackageName());
    }

    /**
     * 绑定服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     * @param appPackageName
     */
    @Override
    public boolean bindService(@NonNull Context context, @NonNull String appPackageName) {
        return mAidlManager.bindService(context, appPackageName);
    }

    /**
     * 绑定默认系统服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     */
    @Override
    public boolean bindDefaultSystemService(@NonNull Context context) {
        return bindService(context, "com.bbk.studyos.launcher");
    }

    /**
     * 解绑
     *
     * @param context
     */
    @Override
    public void unbindService(@NonNull Context context) {
        mAidlManager.unbindService(context);
    }

    @Override
    public void setOnServiceConnectionListener(OnServiceConnectionListener l) {
        mListenerManager.setOnServiceConnectionListener(l);
    }

    /**
     * 服务是否已经绑定好
     *
     * @return
     */
    @Override
    public boolean isConnectionService() {
        return mAidlManager.isConnection();
    }

    /**
     * 获取配置设置
     *
     * @return
     */
    @Override
    public Settings getSettings() {
        try {
            return ConfigAgent.getBehaviorConfig().clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ConfigAgent.getBehaviorConfig();
    }

    @Override
    public void setSettings(@NonNull Settings settings) {
        ConfigAgent.setBehaviorConfig(settings == null ? new Settings() : settings);
    }

    /**
     * 添加默认采集信息
     * <p>
     * 添加后所有埋点都会添加此信息,可以覆盖默认采集信息
     * <p/>
     *
     * @param key   key值必须是在{@link BFCColumns}
     * @param value
     */
    @Override
    public BfcBehaviorAidl putAttr(String key, String value) {
        mAidlManager.getAttrManager().put(key, value);
        return this;
    }

    /**
     * 添加默认采集信息
     * <p>
     * 添加后所有埋点都会添加此信息,可以覆盖默认采集信息
     * <p/>
     *
     * @param map key值必须是在{@link BFCColumns}
     * @return
     */
    @Override
    public BfcBehaviorAidl putAttr(Map<String, String> map) {
        mAidlManager.getAttrManager().put(map);
        return this;
    }

    /**
     * 删除自定义采集信息
     * <p>
     * 删除后,所有埋点对应信息都显示默认的
     * <p/>
     *
     * @param key key值必须是在{@link BFCColumns}
     */
    @Override
    public BfcBehaviorAidl removeAttr(String key) {
        mAidlManager.getAttrManager().remove(key);
        return this;
    }

    /**
     * 删除自定义采集信息,则显示默认的
     * <p>
     * 删除后,所有埋点对应信息都显示默认的
     * <p/>
     *
     * @param map key值必须是在{@link BFCColumns}
     * @return
     */
    @Override
    public BfcBehaviorAidl removeAttr(Map<String, String> map) {
        mAidlManager.getAttrManager().remove(map);
        return this;
    }

    /**
     * 获取自定义attr信息
     *
     * @return
     */
    @Override
    public Map<String, String> getAttr() {
        return mAidlManager.getAttrManager().getAttr();
    }

    /**
     * 行为采集aidl库开关
     * <p>
     * 注:此开关不影响 com.eebbk.bfc.sdk.behavior.BehaviorCollector 中的开关
     * </p>
     *
     * @param enable
     */
    @Override
    public void enable(boolean enable) {
        ConfigAgent.getBehaviorConfig().enable = enable;
    }

    @Override
    public void destroy() {
//        unregisterActivityLifecycleCallbacks();
        mListenerManager.destroy();
//        mAidlManager.destroy();
    }

    private void event(final int type, final String curActivityName, final String functionName, final String moduleDetail, final String trigValue, final String extend
            , final String dataId, final String dataTitle, final String dataEdition, final String dataType, final String dataGrade
            , final String dataSubject, final String dataPublisher, final String dataExtend) {
        if (!ConfigAgent.getBehaviorConfig().enable) {
            return;
        }
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                mAidlManager.event(type, curActivityName, functionName, moduleDetail, trigValue, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
            }
        });
    }

    private void initAppConfig() {
        if (mApplication == null) {
            // 目前以是否有 mApplication 来区分是模块使用还是app使用
            return;
        }
        registerActivityLifecycleCallbacks();
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                initCrashHandler();
                checkExceptionFile();
            }
        });
    }

    private void checkExceptionFile(){
        CheckExceptionAgent.clear();
        initANRCheck();
        initStrictModeCheck();
        initNativeCheck();
        CheckExceptionAgent.check(mApplication, mInnerListener);
    }

    private void initANRCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashAnrEnable) {
            LogUtils.w(TAG, "当前没有打开ANR异常采集功能!");
            return;
        }
        CheckExceptionAgent.add(new AidlAnrCheckExceptionImpl()
                .setAutoFilter(ConfigAgent.getBehaviorConfig().autoFilterAnr)
                .setReport(true)
                .ignoreBefore(mApplication, ConfigAgent.getBehaviorConfig().ignoreBeforeAnr));
    }

    private void initStrictModeCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashStrictModeEnable) {
            LogUtils.w(TAG, "当前没有打开严苛模式异常采集功能!");
            return;
        }
        CheckExceptionAgent.add(new AidlStrictModeCheckExceptionImpl()
                .setReport(true)
                .ignoreBefore(mApplication, ConfigAgent.getBehaviorConfig().ignoreBeforeStrictMode));
    }

    private void initNativeCheck(){
        if (!ConfigAgent.getBehaviorConfig().crashNativeEnable) {
            LogUtils.w(TAG, "当前没有打开Native异常采集功能!");
            return;
        }
        CheckExceptionAgent.add(new AidlNativeCheckExceptionImpl()
                .setReport(true)
                .ignoreBefore(mApplication, ConfigAgent.getBehaviorConfig().ignoreBeforeNative));
    }

    /**
     * 初始化异常捕获
     */
    private void initCrashHandler() {
        if (mApplication == null) {
            // 目前以是否有 mApplication 来区分是模块使用还是app使用
            return;
        }
        if (!ConfigAgent.getBehaviorConfig().crashEnable) {
            LogUtils.w(TAG, "当前没有打开异常捕获行为采集功能!");
            return;
        }

        Context context = mApplication.getApplicationContext();
        if (!AppUtils.isSystemApp(context, context.getPackageName())
                && (StorageUtils.getExternalStorageAvailableSize() / (1024 * 1024) < 10
                || StorageUtils.getDataAvailableSize() / (1024 * 1024) < 10)) {
            LogUtils.w(TAG, "当前内部或者外部存储控件少于10M,无法开启异常捕获功能!");
            return;
        }

        // set the crash handler.
        CrashHandler.getInstance().init(context, mInnerListener);
        if (CrashHandler.getInstance().isReStartTooMany()) {
            // 如果大于10秒,可以重新尝试
            if (compare2Sec(CrashHandler.getInstance().readRestartTime(),
                    System.currentTimeMillis(), 10) && ConfigAgent.getBehaviorConfig().crashEnable) {
                CrashHandler.getInstance().registerCrashHandler();
            }
            CrashHandler.getInstance().cleanReStartCount();
        } else {
            if (ConfigAgent.getBehaviorConfig().crashEnable) {
                CrashHandler.getInstance().registerCrashHandler();
            }
        }
    }

    private boolean compare2Sec(long time1, long time2, int dur) {
        Log.v(TAG, (Math.abs(time1 - time2) / 1000) + "");
        return (Math.abs(time1 - time2) / 1000) > dur;
    }

    private void registerActivityLifecycleCallbacks() {
        if (mApplication == null) {
            LogUtils.w(TAG, "application == null, 不初始化自动采集页面切换.");
            return;
        }
        unregisterActivityLifecycleCallbacks();
        int sdkVersion = Integer.parseInt(android.os.Build.VERSION.SDK);
        if (sdkVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mBCLifeCycleCallback = new BCLifeCycleCallback(mInnerListener);
            mApplication.registerActivityLifecycleCallbacks(mBCLifeCycleCallback);
        }
    }

    private void unregisterActivityLifecycleCallbacks() {
        try {
            if (mApplication != null && mBCLifeCycleCallback != null) {
                mApplication.unregisterActivityLifecycleCallbacks(mBCLifeCycleCallback);
                mBCLifeCycleCallback.setInnerListener(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理缓存的埋点数据
     */
    private synchronized void pushCacheCommand2Remote() {
        if (cacheCommand.isEmpty()) {
            return;
        }
        List<CommandInfo> list = new ArrayList<>(cacheCommand);
        for (CommandInfo command : list) {
            switch (command.getType()) {
                case EType.TYPE_APP_IN:
                    appLaunch();
                    break;
                case EType.TYPE_ACTIVITY_IN:
                    pageBegin(command.getPage());
                    break;
                case EType.TYPE_ACTIVITY_OUT:
                    pageEnd(command.getPage(), null, null, null);
                    break;
                case EType.TYPE_REALTIME_TO_UPLOAD:
                    realTime2Upload();
                    break;
                case EType.TYPE_EXCEPTION:
                    event(command.getType(), command.getMap());
                    break;
                default:
                    break;
            }
        }
        cacheCommand.clear();
    }

    private InnerListener mInnerListener = new InnerListener() {

        @Override
        public void onAppLaunch() {
            if (isConnectionService()) {
                appLaunch();
            } else {
                cacheCommand.add(new CommandInfo(EType.TYPE_APP_IN));
            }
        }

        @Override
        public void onPageBegin(String activityName) {
            if (isConnectionService()) {
                pageBegin(activityName);
            } else {
                cacheCommand.add(new CommandInfo(EType.TYPE_ACTIVITY_IN, activityName));
            }
        }

        @Override
        public void onPageEnd(String activityName) {
            if (isConnectionService()) {
                pageEnd(activityName, null, null, null);
            } else {
                cacheCommand.add(new CommandInfo(EType.TYPE_ACTIVITY_OUT, activityName));
            }
        }

        @Override
        public void onRealTime2Upload() {
            if (isConnectionService()) {
                realTime2Upload();
            } else {
                cacheCommand.add(new CommandInfo(EType.TYPE_REALTIME_TO_UPLOAD));
            }
        }

        @Override
        public void onServiceConnected() {
            pushCacheCommand2Remote();
            if (mListenerManager.getOnServiceConnectionListener() != null) {
                mListenerManager.getOnServiceConnectionListener().onConnected();
            }
        }

        @Override
        public void onServiceDisconnected() {
            if (mListenerManager.getOnServiceConnectionListener() != null) {
                mListenerManager.getOnServiceConnectionListener().onDisconnected();
            }
        }

        @Override
        public void onExitApp() {
            if (mBCLifeCycleCallback != null) {
                mBCLifeCycleCallback.exit();
            }
        }

        @Override
        public void onEvent(int type, Map<String, String> map) {
            if (isConnectionService()) {
                event(type, map);
            } else {
                cacheCommand.add(new CommandInfo(type).setMap(map));
            }
        }
    };
}
