package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import android.text.TextUtils;

import com.eebbk.bfc.common.devices.DeviceUtils;
import com.eebbk.bfc.common.devices.DisplayUtils;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.db.constant.BFCColumns;
import com.eebbk.bfc.sdk.behavior.utils.ContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public class AppLaunchEvent extends AEvent<AppLaunchEvent> {
    /**
     * 进入的activity
     */
    public String activityName;
    /**
     * 进入的时间
     */
    public String trigValue;
    /**
     * 扩展字段
     */
    public String extend;

    /**
     * 设置事件类型
     *
     * @return 参考EType
     */
    @Override
    public int eventType() {
        return EType.TYPE_APP_IN;
    }

    /**
     * 设置事件类型名称
     *
     * @return 参考EType
     */
    @Override
    public String eventName() {
        return EType.NAME_APP_LAUNCH;
    }

    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息 <P/>
     *
     * @return
     */
    @Override
    protected EventAttr packagEventAttr() {
        return new EventAttr()
                .setPage(activityName)
                .setTrigValue(trigValue);
    }

    /**
     * 扩展 IAttr 信息
     * <p> 注：各个实现IAttr的类如：DataAttr是直接对应数据库表格字段的 </p>
     * <p> 此函数作用为：添加额外的基础采集信息，如：DataAttr等 </p>
     * <p> 可以覆盖默认的系统采集数据，如：MachineAttr,OtherAttr等 </p>
     */
    @Override
    protected void packagExtendAttr() {
    }

    /**
     * 扩展字段,必须json格式
     * <p> 如果有额外信息，请在此返回 </p>
     *
     * @return
     */
    @Override
    protected String getJsonExtend() {
        if (TextUtils.isEmpty(extend)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(BFCColumns.LAUNCH_EVENT_EXTEND_SCREEN_WIDTH,
                    String.valueOf(DisplayUtils.getScreenWidth(ContextUtils.getContext())));
            map.put(BFCColumns.LAUNCH_EVENT_EXTEND_SCREEN_HEIGHT,
                    String.valueOf(DisplayUtils.getScreenHeight(ContextUtils.getContext())));
            map.put(BFCColumns.LAUNCH_EVENT_EXTEND_ANDROID_SDK,
                    String.valueOf(DeviceUtils.getSDK()));
            map.put(BFCColumns.LAUNCH_EVENT_EXTEND_MANUFACTURER,
                    String.valueOf(DeviceUtils.getManufacturer()));
            extend = hashMap2Json(map);
        }
        return extend;
    }

    public AppLaunchEvent setActivity(String activity) {
        this.activityName = activity;
        return this;
    }

    public AppLaunchEvent setTrigValue(String trigValue) {
        this.trigValue = trigValue;
        return this;
    }

    public AppLaunchEvent setExtend(String extend) {
        this.extend = extend;
        return this;
    }
}
