package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

/**
 * @author hesn
 * @function
 * @date 17-6-19
 * @company 步步高教育电子有限公司
 */

class TracesInfo {

    public boolean isMyAnr;

    /**
     * 能找到 "----- pid 15878 at 2017-06-15 16:47:24 -----"用这个时间,找不到就用文件最后最改时间
     */
    public String anrTime;

    public String stack;
}
