package com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.event;

import android.content.ContentValues;

import com.eebbk.bfc.sdk.behavior.BehaviorCollector;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.BaseAttrManager;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.entity.attr.EventAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IAttr;
import com.eebbk.bfc.sdk.behavior.control.collect.encapsulation.interfaces.IEvent;
import com.eebbk.bfc.sdk.behavior.control.report.Judgment;
import com.eebbk.bfc.sdk.behavior.utils.FormatUtils;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;
import com.eebbk.bfc.sdk.behavior.utils.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hesn
 * @function 采集事件基础处理类
 * @date 16-8-9
 * @company 步步高教育电子有限公司
 */

public abstract class AEvent<T extends AEvent<T>> implements IEvent {
    /**
     * 采集信息集合
     */
    private List<IAttr> mAttrs;
    /**
     * 额外扩展上传判断条件
     */
    private String extendJudgment = "";
    /**
     * 用户可输入的额外信息集合
     */
    private final List<IAttr> mExtendAttrs = new ArrayList<IAttr>();

    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息，添加默认类型和触发时间 <P/>
     * @return
     */
    protected abstract EventAttr packagEventAttr();
    /**
     * 封装扩展 IAttr 信息
     * <p> 注：各个实现IAttr的类如：DataAttr是直接对应数据库表格字段的 </p>
     * <p> 此函数作用为：添加额外的基础采集信息，如：DataAttr等 </p>
     * <p> 可以覆盖默认的系统采集数据，如：MachineAttr,OtherAttr等 </p>
     */
    protected abstract  void packagExtendAttr();
    /**
     * 设置扩展字段,必须json格式
     * <p> 如果有额外信息，请在此返回 </p>
     * @return
     */
    protected abstract String getJsonExtend();

    /**
     * 生成数据
     * <p> 因为有缓存，所以要考虑生成的数据的实时性 </p>
     */
    @Override
    final public void makeData() {
        //很多默认采集数据如：创建时间，网络状态等实时性数据，
        // 在触发getAttrs()函数时才会采集，所以如果直接缓存，
        //就会存在实时性问题，所以在缓存的时候需要调用此函数，生成数据
        getAttrs();
    }
    /**
     * 获取采集数据的 ContentValues 对象
     * <p> 用于数据库保存 </p>
     * @return
     */
    @Override
    final public ContentValues getContentValues(){
        List<IAttr> iAttrs = getAttrs();
        ContentValues values = new ContentValues();
        for (IAttr iAttr : iAttrs){
            if(iAttr == null){
                continue;
            }
            iAttr.insert(values);
        }
        return values;
    }
    /**
     * 添加基础采集信息
     * @param attrs 实现IAttr的采集信息，如 DataAttr,OtherAttr,UserAttr 等
     */
    public T addAttr(IAttr...attrs){
        for(IAttr iAttr : attrs){
            if(iAttr != null){
                mExtendAttrs.add(iAttr);
            }
        }
        return (T) this;
    }
    /**
     * 使用移动网络上传
     * <p>本条采集数据是否允许使用移动网络上传</p>
     * @param useable
     */
    public T useMobileTraffic(boolean useable){
        extendJudgment = Judgment.useMobileTraffic(extendJudgment,useable);
        return (T)this;
    }

    public void insert(){
        BehaviorCollector.getInstance().event(this);
    }
    /**
     * hashmap转json
     * @param map
     * @return
     */
    String hashMap2Json(Map map) {
        return MapUtils.map2Json(map);
    }
    /**
     * 默认采集数据
     * @return
     */
    private List<IAttr> getAttrs() {
        if(ListUtils.isEmpty(mAttrs)){
            mAttrs = new ArrayList<IAttr>();
            //用户采集信息
            mAttrs.add(getEventAttr());
            //用户信息
            mAttrs.add(BaseAttrManager.getInstance().getUserAttr());
            //app信息
            mAttrs.add(BaseAttrManager.getInstance().getApplicationAttr());
            //机器信息
            mAttrs.add(BaseAttrManager.getInstance().getMachinceAttr());
            //其他信息
            mAttrs.add(BaseAttrManager.getInstance().getOtherAttr(getJsonExtend(), extendJudgment));
            //添加自定义扩展 Attr
            packagExtendAttr();
            mAttrs.addAll(mExtendAttrs);
        }
        return mAttrs;
    }
    /**
     * 封装app采集信息
     * <P> 此函数作用为：封装app采集信息，添加默认类型和触发时间 <P/>
     * @return
     */
    private EventAttr getEventAttr() {
        EventAttr eventAttr = packagEventAttr();
        if(eventAttr == null){
            eventAttr = new EventAttr();
        }
        eventAttr.setEventType(eventType());
        eventAttr.setEventName(eventName());
        eventAttr.setTrigTime(FormatUtils.getDate());
        return eventAttr;
    }

}
