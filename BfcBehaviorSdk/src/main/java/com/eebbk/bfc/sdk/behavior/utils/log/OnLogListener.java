package com.eebbk.bfc.sdk.behavior.utils.log;

/**
 * @author hesn
 * @function
 * @date 16-10-24
 * @company 步步高教育电子有限公司
 */

public interface OnLogListener {

    int LOG_TYPE_ALL = 0x01;

    void onLogRefresh(String log, int type);
}
