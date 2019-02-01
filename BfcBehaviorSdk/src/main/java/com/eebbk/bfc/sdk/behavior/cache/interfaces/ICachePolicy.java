package com.eebbk.bfc.sdk.behavior.cache.interfaces;

import com.eebbk.bfc.sdk.behavior.cache.entity.PolicyInfo;

/**
 * @author hesn
 * @function 缓存策略接口
 * @date 16-8-13
 * @company 步步高教育电子有限公司
 */

public interface ICachePolicy {
    /**
     * 策略类型
     * @return
     */
    int policyType();
    /**
     * 判断是否满足条件存库
     * @param info
     * @return
     */
    boolean save2DBAble(PolicyInfo info);
    /**
     * 同步校准条件判断数据
     * <p>缓存的数据入库时，所有策略同时校准临时数据，
     * 避免各个策略判断结果不同步，导致入库不准</p>
     */
    void syncData();
}
