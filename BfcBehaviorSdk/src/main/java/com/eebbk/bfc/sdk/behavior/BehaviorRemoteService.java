package com.eebbk.bfc.sdk.behavior;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.IBehaviorAidlInterface;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.AidlCustomAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.DataAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.AppLaunchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.ClickEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CountEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomAttrEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.CustomEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event.SearchEvent;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;
import com.eebbk.bfc.sdk.behavior.utils.JsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public class BehaviorRemoteService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return mbider;
    }

    private final IBehaviorAidlInterface.Stub mbider = new IBehaviorAidlInterface.Stub() {
        @Override
        public void event(int type, String curActivityName, String functionName, String moduleDetail
                , String trigValue, String extend, String dataId, String dataTitle, String dataEdition
                , String dataType, String dataGrade, String dataSubject, String dataPublisher
                , String dataExtend, String innerExtend) throws RemoteException {
            setEvent(type, curActivityName, functionName, moduleDetail, trigValue, extend
                    , getDataAttr(dataId, dataTitle, dataEdition, dataType, dataGrade
                            , dataSubject, dataPublisher, dataExtend), innerExtend);
        }

        @Override
        public String eventJson(int type, String json) throws RemoteException {
            switch (type){
                case Constant.Remote.Command.CONFIG:
                    return getConfig();
                default:
                    break;
            }
            return null;
        }

        @Override
        public void realTime2Upload() throws RemoteException {
            BehaviorCollector.getInstance().realTime2Upload();
        }

        @Override
        public String getBehaviorVersion() throws RemoteException {
            return BehaviorCollector.getInstance().getBehaviorVersion();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    private String getConfig(){
        try {
            String jsonStr = JsonUtils.toJson(ConfigAgent.getBehaviorConfig());
            JSONObject jsonObject = new JSONObject(jsonStr);
            jsonObject.put(Constant.Remote.Key.VERSION_CODE, BuildConfig.VERSION_CODE);
            jsonObject.put(Constant.Remote.Key.INIT, ContextUtils.getContext() != null);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setEvent(int type, String curActivityName, String functionName, String moduleDetail
            , String trigValue, String extend, DataAttr dataAttr, String innerExtend) {
        switch (type) {
            case EType.TYPE_CLICK:
                clickEvent(curActivityName, functionName, moduleDetail, extend, dataAttr, getAttr(innerExtend));
                break;
            case EType.TYPE_COUNT:
                countEvent(curActivityName, functionName, moduleDetail, trigValue, extend, dataAttr, getAttr(innerExtend));
                break;
            case EType.TYPE_CUSTOM:
                customEvent(curActivityName, functionName, moduleDetail, trigValue, extend, dataAttr, getAttr(innerExtend));
                break;
            case EType.TYPE_SEARCH:
                searchEvent(curActivityName, functionName, moduleDetail, trigValue, extend, dataAttr, getAttr(innerExtend));
                break;
            case EType.TYPE_ACTIVITY_IN:
                BehaviorCollector.getInstance().pageBegin(curActivityName);
                break;
            case EType.TYPE_ACTIVITY_OUT:
                BehaviorCollector.getInstance().pageEnd(curActivityName, functionName, moduleDetail, dataAttr, extend, getAttr(innerExtend));
                break;
            case EType.TYPE_APP_IN:
                appLaunch(getAttr(innerExtend));
                break;
            case EType.TYPE_EXCEPTION:
                otherEvent(type, curActivityName, functionName, moduleDetail, trigValue, extend, dataAttr, innerExtend);
                break;
            default:
                otherEvent(type, curActivityName, functionName, moduleDetail, trigValue, extend, dataAttr, innerExtend);
                break;
        }
    }

    private void appLaunch(IAttr attr) {
        AppLaunchEvent appLaunchEvent = new AppLaunchEvent();
        appLaunchEvent.addAttr(attr);
        BehaviorCollector.getInstance().event(appLaunchEvent);
    }

    private void clickEvent(String curActivityName, String functionName
            , String moduleDetail, String extend, DataAttr dataAttr, IAttr attr) {
        ClickEvent clickEvent = new ClickEvent();
        clickEvent.activity = curActivityName;
        clickEvent.functionName = functionName;
        clickEvent.moduleDetail = moduleDetail;
        clickEvent.extend = extend;
        clickEvent.dataAttr = dataAttr;
        clickEvent.addAttr(attr);
        BehaviorCollector.getInstance().clickEvent(clickEvent);
    }

    private void countEvent(String curActivityName, String functionName, String moduleDetail
            , String trigValue, String extend, DataAttr dataAttr, IAttr attr) {
        CountEvent countEvent = new CountEvent();
        countEvent.activity = curActivityName;
        countEvent.functionName = functionName;
        countEvent.moduleDetail = moduleDetail;
        countEvent.extend = extend;
        countEvent.trigValue = trigValue;
        countEvent.dataAttr = dataAttr;
        countEvent.addAttr(attr);
        BehaviorCollector.getInstance().countEvent(countEvent);
    }

    private void customEvent(String curActivityName, String functionName, String moduleDetail
            , String trigValue, String extend, DataAttr dataAttr, IAttr attr) {
        CustomEvent customEvent = new CustomEvent();
        customEvent.activity = curActivityName;
        customEvent.functionName = functionName;
        customEvent.moduleDetail = moduleDetail;
        customEvent.extend = extend;
        customEvent.trigValue = trigValue;
        customEvent.dataAttr = dataAttr;
        customEvent.addAttr(attr);
        BehaviorCollector.getInstance().customEvent(customEvent);
    }

    private void searchEvent(String curActivityName, String functionName, String moduleDetail
            , String trigValue, String extend, DataAttr dataAttr, IAttr attr) {
        SearchEvent searchEvent = new SearchEvent();
        searchEvent.activity = curActivityName;
        searchEvent.functionName = functionName;
        searchEvent.moduleDetail = moduleDetail;
        searchEvent.resultCount = extend;
        searchEvent.keyWrod = trigValue;
        searchEvent.dataAttr = dataAttr;
        searchEvent.addAttr(attr);
        BehaviorCollector.getInstance().searchEvent(searchEvent);
    }

    private void otherEvent(int type, String curActivityName, String functionName, String moduleDetail
            , String trigValue, String extend, DataAttr dataAttr, String innerExtend) {
        CustomAttrEvent customAttrEvent = new CustomAttrEvent();
        customAttrEvent.eventAttr = new EventAttr()
                .setEventType(type)
                .setEventName(getEventName(type))
                .setPage(curActivityName)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail)
                .setTrigValue(trigValue);
        customAttrEvent.addAttr(dataAttr);
        Map<String, String> extMap = null;
        if (!TextUtils.isEmpty(extend)) {
            // 没有扩展字段接口,需要手动加入
            extMap = new HashMap<>();
            extMap.put(BFCColumns.COLUMN_OA_EXTEND, extend);
        }
        customAttrEvent.addAttr(getAttr(innerExtend, extMap));
        BehaviorCollector.getInstance().event(customAttrEvent);
    }

    private DataAttr getDataAttr(String dataId, String dataTitle, String dataEdition, String dataType
            , String dataGrade, String dataSubject, String dataPublisher, String dataExtend) {
        return new DataAttr(dataId, dataTitle, dataEdition, dataType, dataGrade
                , dataSubject, dataPublisher, dataExtend);
    }

    private IAttr getAttr(String json) {
        return getAttr(json, null);
    }

    private IAttr getAttr(String json, Map map) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        AidlCustomAttr aidlCustomAttr = new AidlCustomAttr();
        Map mapType = JsonUtils.fromJson(json, Map.class);
        if (map != null && !map.isEmpty()) {
            mapType.putAll(map);
        }
        aidlCustomAttr.setMap(mapType);
        return aidlCustomAttr;
    }

    private String getEventName(int eventType) {
        switch (eventType) {
            case EType.TYPE_ACTIVITY_OUT:
                return EType.NAME_PAGE;
            case EType.TYPE_APP_IN:
                return EType.NAME_APP_LAUNCH;
            case EType.TYPE_CLICK:
                return EType.NAME_CLICK;
            case EType.TYPE_SEARCH:
                return EType.NAME_SEARCH;
            case EType.TYPE_CUSTOM:
                return EType.NAME_CUSTOM;
            case EType.TYPE_COUNT:
                return EType.NAME_COUNT;
            case EType.TYPE_EXCEPTION:
                return EType.NAME_EXCEPTION;
            case EType.TYPE_FUNC_BEGIN:
                return EType.NAME_FUNC_BEGIN;
            case EType.TYPE_FUNC_END:
                return EType.NAME_FUNC_END;
            case EType.TYPE_MONITOR_URL:
                return EType.FUNCTION_MONITOR_URL;
            case EType.TYPE_DURATION:
                return EType.NAME_DURATION;
            default:
                return null;
        }
    }

}
