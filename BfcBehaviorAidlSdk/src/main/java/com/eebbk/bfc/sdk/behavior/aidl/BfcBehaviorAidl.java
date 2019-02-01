package com.eebbk.bfc.sdk.behavior.aidl;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.crash.check.CrashType;
import com.eebbk.bfc.sdk.behavior.aidl.listener.OnServiceConnectionListener;

import java.util.Map;

/**
 * @author hesn
 * @function aidl埋点解决bfc模块间埋点调用, 但是不想相互依赖的问题
 * <p>
 * 如:bfc-Http库想要埋点,但是不想相互依赖(直接依赖行为采集库)耦合,就可以使用aidl埋点.
 * <p/>
 * <p>
 * 注意:(1)aidl包提供的接口最终还是使用bfc-behavior提供的正式接口,所以可以保持所有埋点逻辑处理一致.
 * <br>(2)所有的设置和初始化等最高权限由上层app决定,避免设置冲突.
 * <br>如:app在正式库bfc-behavior中无初始化,aidl埋点模块也无法使用;app关闭埋点开关,aidl也无法埋点.
 * <p/>
 * @date 17-4-10
 * @company 步步高教育电子有限公司
 */

public interface BfcBehaviorAidl {

    /**
     * APP调出前台事件
     */
    void appLaunch();

    /**
     * 计次事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param extend          扩展信息
     */
    void clickEvent(String curActivityName, String functionName, String moduleDetail, String extend);

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
    void clickEvent(String curActivityName, String functionName, String moduleDetail, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend);

    /**
     * 计数事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发本次动作的触发值，如视频播放的时长等，注：时长统一以毫秒(ms)为单位
     * @param extend          扩展信息
     */
    void countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend);

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
    void countEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend);

    /**
     * 自定义事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param trigValue       触发值
     * @param extend          扩展信息
     */
    void customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend);

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
    void customEvent(String curActivityName, String functionName, String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend);

    /**
     * 搜索事件
     *
     * @param curActivityName 进入的activity
     * @param functionName    功能名称
     * @param moduleDetail    模块详细
     * @param keyWord         搜索的关键字
     * @param resultCount     搜索结果
     */
    void searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount);

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
    void searchEvent(String curActivityName, String functionName, String moduleDetail, String keyWord, String resultCount
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend);

    /**
     * 界面进入事件
     *
     * @param page 进入的activity
     */
    void pageBegin(@NonNull String page);

    /**
     * 页面退出事件
     *
     * @param page         进入的activity
     * @param functionName 功能名称
     * @param moduleDetail 模块详细
     * @param extend       扩展信息
     */
    void pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend);

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
    void pageEnd(@NonNull String page, String functionName, String moduleDetail, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend);

    /**
     * map形式埋点
     *
     * @param type
     * @param map
     */
    void event(int type, Map<String, String> map);

    /**
     * (以后扩展用)
     *
     * @param type
     * @param json
     * @return
     */
    String eventJson(int type, @NonNull String json);

    /**
     * 马上上传所有数据
     */
    void realTime2Upload();

    /**
     * 获取行为采集库版本信息
     *
     * @return
     */
    String getBehaviorVersion();

    /**
     * 获取行为采集aidl库版本信息
     *
     * @return
     */
    String getVersion();

    /**
     * 绑定服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     */
    boolean bindService(@NonNull Context context);

    /**
     * 绑定服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     * @param appPackageName
     */
    boolean bindService(@NonNull Context context, @NonNull String appPackageName);

    /**
     * 绑定默认系统服务
     * <p>
     * 注:使用此aidl库埋点前,必须先绑定
     * <p/>
     *
     * @param context
     */
    boolean bindDefaultSystemService(@NonNull Context context);

    /**
     * 解绑
     *
     * @param context
     */
    void unbindService(@NonNull Context context);

    /**
     * aidl服务绑定回调监听接口
     *
     * @param l
     */
    void setOnServiceConnectionListener(OnServiceConnectionListener l);

    /**
     * 服务是否已经绑定好
     *
     * @return
     */
    boolean isConnectionService();

    /**
     * 获取配置设置
     *
     * @return
     */
    Settings getSettings();

    void setSettings(@NonNull Settings settings);

    /**
     * 添加默认采集信息
     * <p>
     * 添加后所有埋点都会添加此信息,可以覆盖默认采集信息
     * <p/>
     *
     * @param key   key值必须是在{@link BFCColumns}
     * @param value
     */
    BfcBehaviorAidl putAttr(String key, String value);

    /**
     * 添加默认采集信息
     * <p>
     * 添加后所有埋点都会添加此信息,可以覆盖默认采集信息
     * <p/>
     *
     * @param map key值必须是在{@link BFCColumns}
     * @return
     */
    BfcBehaviorAidl putAttr(Map<String, String> map);

    /**
     * 删除自定义采集信息
     * <p>
     * 删除后,所有埋点对应信息都显示默认的
     * <p/>
     *
     * @param key key值必须是在{@link BFCColumns}
     */
    BfcBehaviorAidl removeAttr(String key);

    /**
     * 删除自定义采集信息,则显示默认的
     * <p>
     * 删除后,所有埋点对应信息都显示默认的
     * <p/>
     *
     * @param map key值必须是在{@link BFCColumns}
     * @return
     */
    BfcBehaviorAidl removeAttr(Map<String, String> map);

    /**
     * 获取自定义attr信息
     *
     * @return
     */
    Map<String, String> getAttr();

    /**
     * 行为采集aidl库开关
     * <p>
     * 注:此开关不影响 com.eebbk.bfc.sdk.behavior.BehaviorCollector 中的开关
     * </p>
     *
     * @param enable
     */
    void enable(boolean enable);

    /**
     * 销毁
     */
    void destroy();

    class Builder {

        private final Settings settings = new Settings();
        private final ListenerManager listenerManager = new ListenerManager();

        /**
         * aidl采集开关
         * <p>
         * 注:此开关不影响 com.eebbk.bfc.sdk.behavior.BehaviorCollector 中的开关
         * </p>
         *
         * @param enable 默认true
         * @return
         */
        public Builder enable(boolean enable) {
            settings.enable = enable;
            return this;
        }

        /**
         * app异常捕获开关
         *
         * @param enable 默认true
         * @return
         */
        public Builder crashEnable(boolean enable) {
            settings.crashEnable = enable;
            return this;
        }

        /**
         * 异常时，弹Toast提示
         *
         * @param enable
         * @return
         */
        public Builder setCrashToast(boolean enable) {
            settings.isToastCrash = enable;
            return this;
        }

        /**
         * anr捕获开关
         *
         * @param enable 默认true
         * @return
         */
        public Builder crashAnrEnable(boolean enable) {
            settings.crashAnrEnable = enable;
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
            settings.autoFilterAnr = enable;
            return this;
        }

        /**
         * 严苛模式捕获开关
         *
         * @param enable 默认false
         * @return
         */
        public Builder crashStrictModeEnable(boolean enable) {
            settings.crashStrictModeEnable = enable;
            return this;
        }

        /**
         * native异常捕获开关
         *
         * @param enable 默认true
         * @return
         */
        public Builder crashNativeEnable(boolean enable) {
            settings.crashNativeEnable = enable;
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
                settings.ignoreBeforeAnr = timeMillis;
                settings.ignoreBeforeStrictMode = timeMillis;
                settings.ignoreBeforeNative = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_ANR)){
                settings.ignoreBeforeAnr = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_STRICT_MODE)){
                settings.ignoreBeforeStrictMode = timeMillis;
                return this;
            }
            if(TextUtils.equals(type, CrashType.TYPE_NATIVE)){
                settings.ignoreBeforeNative = timeMillis;
                return this;
            }
            return this;
        }

        /**
         * 调试模式
         *
         * @param isDebugMode 默认false
         * @return
         */
        public Builder debugMode(boolean isDebugMode) {
            settings.isDebugMode = isDebugMode;
            return this;
        }

        /**
         * app在后台sessionTimeout后，再进入前台则调用app启动事件
         *
         * @param sessionTimeout 默认30秒 (单位毫秒)
         * @return
         */
        public Builder sessionTimeout(long sessionTimeout) {
            settings.sessionTimeout = sessionTimeout;
            return this;
        }

        /**
         * 自动采集activity使用时长
         *
         * @param isOpen 默认 true
         * @return
         */
        public Builder openActivityDurationTrack(boolean isOpen) {
            settings.openActivityDurationTrack = isOpen;
            return this;
        }

        /**
         * 自动采集app启动事件
         *
         * @param enable 是否自动采集，默认true
         */
        public Builder enableCollectLaunch(boolean enable) {
            settings.enableCollectLaunch = enable;
            return this;
        }

        /**
         * aidl服务绑定回调监听接口
         *
         * @param l
         * @return
         */
        public Builder setOnServiceConnectionListener(OnServiceConnectionListener l) {
            listenerManager.setOnServiceConnectionListener(l);
            return this;
        }

        /**
         * 设置所有埋点的模块名
         * @param moduleName
         * @return
         */
        public Builder setModuleName(String moduleName) {
            settings.moduleName = moduleName;
            return this;
        }

        public BfcBehaviorAidl build(Application application) {
            return new BfcBehaviorAidlImpl(application, settings, listenerManager);
        }
    }
}
