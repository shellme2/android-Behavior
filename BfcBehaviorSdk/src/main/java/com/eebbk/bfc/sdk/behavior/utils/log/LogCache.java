package com.eebbk.bfc.sdk.behavior.utils.log;

import com.eebbk.bfc.sdk.behavior.utils.ConfigAgent;

/**
 * @author hesn
 * @function 日志缓存
 * @date 16-10-14
 * @company 步步高教育电子有限公司
 */

class LogCache {

    //压力测试时发现还是需要用 StringBuffer
    private StringBuffer mAllLogSb = new StringBuffer();
    private OnLogListener mListener;

    void setListener(OnLogListener l) {
        mListener = l;
    }

    LogCache() {
        cleanAllLog();
    }

    void addAll(String log) {
        if (!ConfigAgent.getBehaviorConfig().isCacheLog) {
            return;
        }

        mAllLogSb.insert(0, log + "\n");
        if (mAllLogSb.length() >= 3000) {
            mAllLogSb.delete(1000, mAllLogSb.length());
        }

        callBack(OnLogListener.LOG_TYPE_ALL);
    }

    /**
     * 读取当前日志
     *
     * @return
     */
    String readCurrentAllLog() {
        return mAllLogSb.toString();
    }

    void cleanAllLog() {
        mAllLogSb.delete(0, mAllLogSb.length());
    }

    private void callBack(int type) {
        if (mListener == null) {
            return;
        }
        switch (type) {
            case OnLogListener.LOG_TYPE_ALL:
                mListener.onLogRefresh(mAllLogSb.toString(), OnLogListener.LOG_TYPE_ALL);
                break;
            default:
                break;
        }
    }
}
