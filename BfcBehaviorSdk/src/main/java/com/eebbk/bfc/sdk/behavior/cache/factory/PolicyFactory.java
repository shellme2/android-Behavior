package com.eebbk.bfc.sdk.behavior.cache.factory;

import com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType;
import com.eebbk.bfc.sdk.behavior.cache.interfaces.ICachePolicy;
import com.eebbk.bfc.sdk.behavior.cache.policy.CapacityCachePolicy;
import com.eebbk.bfc.sdk.behavior.cache.policy.TimeCachePolicy;

/**
 * @author hesn
 * @function
 * @date 16-8-24
 * @company 步步高教育电子有限公司
 */

public class PolicyFactory {

    /**
     * 创建缓存策略
     * @param policyType 策略类型
     * @return
     */
    public static ICachePolicy createCachePolicy(int policyType){
        switch (policyType){
            case PolicyType.TIME:
                //时间缓冲策略
                return new TimeCachePolicy();
            case PolicyType.CAPACITY:
                //容量缓存策略
                return new CapacityCachePolicy();
        }
        return null;
    }

    private PolicyFactory(){
        //prevent the instance
    }
}
