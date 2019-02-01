package com.eebbk.bfc.sdk.behavior.cache;

import com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType;

/**
 * @author hesn
 * @function app配置缓存信息
 * @date 16-8-19
 * @company 步步高教育电子有限公司
 */

public class CacheConfig {

    /**
     * 是否开启缓存模式
     */
    public boolean usable = false;
    /**
     * 缓存策略
     * <p> 如果为null,则这是默认策略 PolicyType.TIME + PolicyType.CAPACITY </p>
     */
    public int[] policyTypes = new int[]{PolicyType.TIME, PolicyType.CAPACITY};
    /**
     * 无视缓存策略的事件类型
     * <p> 需要直接入库或者上传的事件类型 </p>
     * <p> 默认类型有EType.TYPE_APP_IN,EType.TYPE_EXCEPTION </p>
     */
    public int[] ignoreCacheEventType = null;

    //------CapacityCachePolicy member------
    /**
     * 缓存池最大值
     * <p>因为存在杀死进程等异常退出的可能，所以缓存池不能设置太大</p>
     * <p>降低异常时的数据损失</p>
     */
    private static final int CAPACITY_MAX = 10;
    /** 缓存池默认值 */
    private static final int CAPACITY_DEFAULT = 5;
    /** 缓存池大小 */
    private int size = CAPACITY_DEFAULT;

    //------TimeCachePolicy member------
    //缓冲时间默认值
    private static final int TIME_DEFAULT = 5000;
    /** 在这个时间段内(毫秒)，只保存一次到数据库 */
    private long cacheTime = TIME_DEFAULT;


    //------CapacityCachePolicy method------
    public int getCacheSize() {
        return size;
    }

    /**
     * 容量缓存策略的缓存池大小
     * @param size
     */
    public CacheConfig setCacheSize(int size) {
        this.size = size > CAPACITY_MAX ? CAPACITY_MAX : size;
        return this;
    }

    //------TimeCachePolicy method------
    public long getCacheTime() {
        return cacheTime;
    }

    /**
     * 时间间隔缓冲策略的时间间隔
     * @param cacheTime 毫秒
     */
    public CacheConfig setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
        return this;
    }

}
