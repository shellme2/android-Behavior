package com.eebbk.bfc.sdk.behavior.cache.policy;

import com.eebbk.bfc.sdk.behavior.cache.constant.PolicyType;
import com.eebbk.bfc.sdk.behavior.cache.entity.PolicyInfo;

/**
 * @author hesn
 * @function 容量缓存策略
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public class CapacityCachePolicy extends APolicy {

    @Override
    public int policyType() {
        return PolicyType.CAPACITY;
    }

    @Override
    public boolean save2DBAble(PolicyInfo info) {
        return info != null && info.cachePoolSize >= getCacheConfig().getCacheSize();
    }

    @Override
    public void syncData() {

    }

}
