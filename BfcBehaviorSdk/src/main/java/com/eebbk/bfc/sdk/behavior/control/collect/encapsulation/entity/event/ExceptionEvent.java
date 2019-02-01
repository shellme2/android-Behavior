package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.constant.EType;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;

import org.json.JSONException;
import org.json.JSONObject;

public class ExceptionEvent extends AEvent<ExceptionEvent> {
	private static final long serialVersionUID = 1L;
    /**
     * 异常栈信息
     */
    public  String stack;
    /**
     * 系统日志
     */
    public String sysLog;
    /**
     * 磁盘总大小(GB)
     */
    public String diskTotal;
    /**
     * 磁盘使用百分比
     */
    public String diskUsage;
    /**
     * SD卡总大小(GB)
     */
    public String sdTotal;
    /**
     * SD卡使用百分比
     */
    public String sdUsage;
    /**
     * 内存总大小(MB)
     */
    public String memTotal;
    /**
     * 内存使用百分比
     */
    public String memUsage;
    /**
     * 异常类型
     */
    public String type;

    /**
     * 设置事件类型
     * @return 参考EType
     */
    @Override
    public int eventType() {
        return EType.TYPE_EXCEPTION;
    }
    /**
     * 设置事件类型名称
     * @return 参考EType
     */
    @Override
    public String eventName() {
        return EType.NAME_EXCEPTION;
    }
    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息 <P/>
     * @return
     */
    @Override
    protected EventAttr packagEventAttr() {
        return new EventAttr().setTrigValue(data2Json());
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
        return null;
    }

    private String data2Json(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(TrigValueKey.STACK, stack);
            jsonObject.put(TrigValueKey.SYS_LOG, sysLog);
            jsonObject.put(TrigValueKey.DISK_TOTAL, diskTotal);
            jsonObject.put(TrigValueKey.DISK_USAGE, diskUsage);
            jsonObject.put(TrigValueKey.SD_TOTAL, sdTotal);
            jsonObject.put(TrigValueKey.SD_USAGE, sdUsage);
            jsonObject.put(TrigValueKey.MEM_TOTAL, memTotal);
            jsonObject.put(TrigValueKey.MEM_USAGE, memUsage);
            jsonObject.put(TrigValueKey.TYPE, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public ExceptionEvent setStack(String stack) {
        this.stack = stack;
        return this;
    }

    public ExceptionEvent setSysLog(String sysLog) {
        this.sysLog = sysLog;
        return this;
    }

    public ExceptionEvent setDiskTotal(String diskTotal) {
        this.diskTotal = diskTotal;
        return this;
    }

    public ExceptionEvent setDiskUsage(String diskUsage) {
        this.diskUsage = diskUsage;
        return this;
    }

    public ExceptionEvent setSdTotal(String sdTotal) {
        this.sdTotal = sdTotal;
        return this;
    }

    public ExceptionEvent setSdUsage(String sdUsage) {
        this.sdUsage = sdUsage;
        return this;
    }

    public ExceptionEvent setMemTotal(String memTotal) {
        this.memTotal = memTotal;
        return this;
    }

    public ExceptionEvent setMemUsage(String memUsage) {
        this.memUsage = memUsage;
        return this;
    }

    public ExceptionEvent setType(String type) {
        this.type = type;
        return this;
    }

    public interface TrigValueKey{
        String STACK = "stack";
        String SYS_LOG = "sysLog";
        String DISK_TOTAL = "diskTotal";
        String DISK_USAGE = "diskUsage";
        String SD_TOTAL = "sdTotal";
        String SD_USAGE = "sdUsage";
        String MEM_TOTAL = "memTotal";
        String MEM_USAGE = "memUsage";
        String TYPE = "type";
        String URL = "url";
    }

}
