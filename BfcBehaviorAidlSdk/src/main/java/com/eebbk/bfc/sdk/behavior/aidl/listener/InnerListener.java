package com.eebbk.bfc.sdk.behavior.aidl.listener;

import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 17-5-8
 * @company 步步高教育电子有限公司
 */

public interface InnerListener {

    void onAppLaunch();

    void onPageBegin(String activityName);

    void onPageEnd(String activityName);

    void onRealTime2Upload();

    void onServiceConnected();

    void onServiceDisconnected();

    void onExitApp();

    void onEvent(int type, Map<String, String> map);
}
