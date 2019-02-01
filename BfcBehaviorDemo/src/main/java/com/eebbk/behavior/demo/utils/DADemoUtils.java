package com.eebbk.behavior.demo.utils;

import android.app.Application;
import android.content.Context;

import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.aidl.BFCColumns;
import com.eebbk.bfc.sdk.behavior.aidl.BfcBehaviorAidl;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;

/**
 * @function 行为采集工具类
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public class DADemoUtils {

    private static int dataId = 0;
    private static BfcBehaviorAidl bfcBehaviorAidl;
    private static final String AIDL_TAG = "-aidl";

    public static void init(Application application) {
        bfcBehaviorAidl = new BfcBehaviorAidl.Builder()
                .debugMode(true)
                .crashEnable(true)
                .setCrashToast(true)
                .crashAnrEnable(false)
                .crashStrictModeEnable(false)
                .crashNativeEnable(false)
                .build(application);
    }

    /**
     * 绑定aidl埋点服务
     *
     * @param context
     */
    public static void bindService(Context context) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.bindDefaultSystemService(context);
//        bfcBehaviorAidl.bindService(context);
        bfcBehaviorAidl.putAttr(BFCColumns.COLUMN_AA_MODULENAME, "测试替换名字");
    }

    /**
     * 解绑aidl埋点服务
     *
     * @param context
     */
    public static void unbindService(Context context) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.unbindService(context);
        bfcBehaviorAidl.destroy();
//        bfcBehaviorAidl = null;
    }

    /**
     * aild马上上传所有数据
     */
    public static void realTime2UploadAidl() {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.realTime2Upload();
    }

    /**
     * aild-计次事件
     */
    public static void clickEventAidl(String activity, String functionName
            , String moduleDetail, String extend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.clickEvent(activity, functionName + AIDL_TAG, moduleDetail, extend);
    }

    /**
     * aild-计次事件
     */
    public static void clickEventAidl(String activity, String functionName, String moduleDetail, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.clickEvent(activity, functionName + AIDL_TAG, moduleDetail, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * aild-计数事件
     */
    public static void countEventAidl(String activity, String functionName
            , String moduleDetail, String trigValue, String extend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.countEvent(activity, functionName + AIDL_TAG, moduleDetail, trigValue, extend);
    }

    /**
     * aild-计数事件
     */
    public static void countEventAidl(String activity, String functionName
            , String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.countEvent(activity, functionName + AIDL_TAG, moduleDetail, trigValue, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * aild-自定义事件
     */
    public static void customEventAidl(String activity, String functionName
            , String moduleDetail, String trigValue, String extend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.customEvent(activity, functionName + AIDL_TAG, moduleDetail, trigValue, extend);
    }

    /**
     * aild-自定义事件
     */
    public static void customEventAidl(String activity, String functionName
            , String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.customEvent(activity, functionName + AIDL_TAG, moduleDetail, trigValue, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * aild-搜索事件
     */
    public static void searchEventAidl(String activity, String functionName
            , String moduleDetail, String keyWord, String resultCount) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.searchEvent(activity, functionName + AIDL_TAG, moduleDetail, keyWord, resultCount);
    }

    /**
     * aild-搜索事件
     */
    public static void searchEventAidl(String activity, String functionName
            , String moduleDetail, String keyWord, String resultCount
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.searchEvent(activity, functionName + AIDL_TAG, moduleDetail, keyWord, resultCount
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * aild-界面退出事件
     *
     * @param page 为 activity或fragment
     */
    public static void pageEndAidl(String page, String functionName, String moduleDetail, String extend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.pageEnd(page, functionName + AIDL_TAG, moduleDetail, extend);
    }

    /**
     * aild-界面退出事件
     *
     * @param page 为 activity或fragment
     */
    public static void pageEndAidl(String page, String functionName, String moduleDetail, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.pageEnd(page, functionName + AIDL_TAG, moduleDetail, extend
                , dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    /**
     * aild-界面进入事件
     *
     * @param page 为 activity或fragment
     */
    public static void pageBeginAidl(String page) {
        if (bfcBehaviorAidl == null) {
            return;
        }
        bfcBehaviorAidl.pageBegin(page);
    }

    /**
     * aild-埋点
     */
    public static void eventAidl(int eventType, String activity, String functionName
            , String moduleDetail, String trigValue, String extend
            , String dataId, String dataTitle, String dataEdition, String dataType, String dataGrade
            , String dataSubject, String dataPublisher, String dataExtend) {
        switch (eventType) {
            case EType.TYPE_CLICK:
                clickEventAidl(activity, functionName, moduleDetail, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
                break;
            case EType.TYPE_COUNT:
                countEventAidl(activity, functionName, moduleDetail, trigValue, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
                break;
            case EType.TYPE_CUSTOM:
                customEventAidl(activity, functionName, moduleDetail, trigValue, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
                break;
            case EType.TYPE_SEARCH:
                searchEventAidl(activity, functionName, moduleDetail, trigValue, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
                break;
            case EType.TYPE_ACTIVITY_OUT:
                pageBeginAidl(activity);
                pageEndAidl(activity, functionName, moduleDetail, extend
                        , dataId, dataTitle, dataEdition, dataType, dataGrade
                        , dataSubject, dataPublisher, dataExtend);
                break;
            default:
                break;
        }
    }

    /**
     * 计次事件
     *
     * @param activity
     * @param functionName
     * @param moduleDetail
     * @param extend
     */
    public static void clickEvent(String activity, String functionName
            , String moduleDetail, String extend) {
//        ClickEvent clickEvent = new ClickEvent();
//        clickEvent.activity = activity;
//        clickEvent.functionName = functionName;
//        clickEvent.moduleDetail = moduleDetail;
//        // 扩展信息
//        // 请提交json格式
//        // 或者使用 event.setExtend(map)，提交map
//        clickEvent.extend = extend;
//        clickEvent.dataAttr = getDataAttr();
//
//        //允许使用移动网络上传(默认不允许)
//        clickEvent.useMobileTraffic(true);
//
//        BehaviorCollector.getInstance().clickEvent(clickEvent);
        new ClickEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();
    }

    /**
     * 计数事件
     *
     * @param activity
     * @param functionName
     * @param moduleDetail
     * @param trigValue
     * @param extend
     */
    public static void countEvent(String activity, String functionName
            , String moduleDetail, String trigValue, String extend) {
//        CountEvent countEvent = new CountEvent();
//        countEvent.activity = activity;
//        countEvent.functionName = functionName;
//        countEvent.moduleDetail = moduleDetail;
//        countEvent.trigValue = trigValue;
//        // 扩展信息
//        // 请提交json格式
//        // 或者使用 event.setExtend(map)，提交map
//        countEvent.extend = extend;
//        countEvent.dataAttr = getDataAttr();
//
//        BehaviorCollector.getInstance().countEvent(countEvent);
        new CountEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setTrigValue(trigValue)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();
    }

    /**
     * 自定义事件
     *
     * @param activity
     * @param functionName
     * @param moduleDetail
     * @param trigValue
     * @param extend
     */
    public static void customEvent(String activity, String functionName
            , String moduleDetail, String trigValue, String extend) {
//        CustomEvent customEvent = new CustomEvent();
//        customEvent.activity = activity;
//        customEvent.functionName = functionName;
//        customEvent.moduleDetail = moduleDetail;
//        customEvent.trigValue = trigValue;
//        // 扩展信息
//        // 请提交json格式
//        // 或者使用 event.setExtend(map)，提交map
//        customEvent.extend = extend;
//        customEvent.dataAttr = getDataAttr();
//
//        BehaviorCollector.getInstance().customEvent(customEvent);
        new CustomEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setTrigValue(trigValue)
                .setExtend(extend)
                .setDataAttr(getDataAttr())
                .insert();
    }

    /**
     * 搜索事件
     *
     * @param activity
     * @param functionName
     * @param moduleDetail
     * @param keyWord
     * @param resultCount
     */
    public static void searchEvent(String activity, String functionName
            , String moduleDetail, String keyWord, String resultCount) {
//        SearchEvent searchEvent = new SearchEvent();
//        searchEvent.activity = activity;
//        searchEvent.functionName = functionName;
//        searchEvent.moduleDetail = moduleDetail;
//        searchEvent.keyWrod = keyWord;
//        searchEvent.resultCount = resultCount;
//        searchEvent.dataAttr = getDataAttr();
//
//        BehaviorCollector.getInstance().searchEvent(searchEvent);
        new SearchEvent().setActivity(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setKeyWrod(keyWord)
                .setResultCount(resultCount)
                .setDataAttr(getDataAttr())
                .insert();
    }

    /**
     * 界面退出事件
     *
     * @param page 为 activity或fragment
     */
    public static void pageEnd(String page) {
        BehaviorCollector.getInstance().pageEnd(page);
    }

    /**
     * 界面进入事件
     *
     * @param page 为 activity或fragment
     */
    public static void pageBegin(String page) {
        BehaviorCollector.getInstance().pageBegin(page);
    }

    public static void resetDataId() {
        dataId = 0;
    }

    private static DataAttr getDataAttr() {
        dataId++;
        return new DataAttr().setDataId(String.valueOf(dataId));
    }

    private DADemoUtils() {

    }
}
