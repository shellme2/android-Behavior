package com.eebbk.bfc.sdk.behavior.cache.policy;

import com.eebbk.bfc.sdk.behavior.cache.CacheConfig;
import com.eebbk.bfc.sdk.behavior.cache.interfaces.ICachePolicy;
import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;

/**
 * @author hesn
 * @function
 * @date 16-8-19
 * @company 步步高教育电子有限公司
 */

abstract class APolicy implements ICachePolicy {
    /**
     * 获取缓存策略的配置信息
     * @return
     */
    CacheConfig getCacheConfig(){
        return ConfigAgent.getBehaviorConfig().cacheConfig;
    }
}
