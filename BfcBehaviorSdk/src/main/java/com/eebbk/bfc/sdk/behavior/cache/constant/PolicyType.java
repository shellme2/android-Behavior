package com.eebbk.bfc.sdk.behavior.cache.constant;

/**
 * @author hesn
 * @function 策略唯一id,用于区分不同策略。不同策略不要用同一个id，否则后添加者会添加不上。
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public class PolicyType {
    /** 时间缓存策略 */
    public static final int TIME = 0x01;
    /** 容量缓存策略 */
    public static final int CAPACITY = 0x02;

    private PolicyType(){
        //prevent the instance
    }
}
