package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import android.os.SystemClock;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.error.ErrorCode;
import com.eebbk.bfc.sdk.behavior.utils.log.LogUtils;
import com.eebbk.bfc.sdk.behavior.utils.SharedPrefUtils;

/**
 * @author hesn
 * @function
 * @date 16-9-19
 * @company 步步高教育电子有限公司
 */

public class FunctionEndEvent extends AEvent<FunctionEndEvent>{
    private static final String TAG = "FunctionEndEvent";
    /**
     * 进入的activity
     */
    public String activity;
    /**
     * 功能名称
     */
    public String functionName;
    /**
     * 模块详细
     */
    public String moduleDetail;
    /**
     * 使用时长
     */
    public String trigValue;
    /**
     * 扩展字段
     */
    public String extend;

    /**
     * 设置事件类型
     * @return 参考EType
     */
    @Override
    public int eventType() {
        return EType.TYPE_FUNC_END;
    }
    /**
     * 设置事件类型名称
     * @return 参考EType
     */
    @Override
    public String eventName() {
        return EType.NAME_FUNC_END;
    }
    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息 <P/>
     * @return
     */
    @Override
    protected EventAttr packagEventAttr() {
        return new EventAttr()
                .setPage(activity)
                .setFunctionName(functionName)
                .setModuleDetail(moduleDetail);
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
     * @return
     */
    @Override
    protected String getJsonExtend() {
        return extend;
    }

    public boolean calcuDuring(){
        long startTime = SharedPrefUtils.getKeyLongValue(functionName, -1);
        if(TextUtils.isEmpty(functionName) || startTime == -1){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_RECORD_FUNCTION_ORDER);
            return false;
        }
        long during = SystemClock.elapsedRealtime() - startTime;
        SharedPrefUtils.remove(functionName);
        if(during <= 0){
            LogUtils.bfcExceptionLog(TAG, ErrorCode.CONTROL_ILLEGAL_ARGUMENT_RECORD_FUNCTION);
            return false;
        }
        trigValue = String.valueOf(during);
        return true;
    }

    public FunctionEndEvent setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public FunctionEndEvent setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }

    public FunctionEndEvent setModuleDetail(String moduleDetail) {
        this.moduleDetail = moduleDetail;
        return this;
    }

    public FunctionEndEvent setTrigValue(String trigValue) {
        this.trigValue = trigValue;
        return this;
    }

    public FunctionEndEvent setExtend(String extend) {
        this.extend = extend;
        return this;
    }
}
