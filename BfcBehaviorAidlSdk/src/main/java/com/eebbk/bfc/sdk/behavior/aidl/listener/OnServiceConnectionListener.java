package com.eebbk.bfc.sdk.behavior.aidl.listener;

/**
 * @author hesn
 * @function aidl服务绑定回调监听接口
 * @date 17-5-8
 * @company 步步高教育电子有限公司
 */

public interface OnServiceConnectionListener {

    /**
     * aidl链接成功后回调
     */
    void onConnected();

    /**
     * aidl意外断开回调
     * <p>
     *    正常调用 unbindService() 不会回调
     * <p/>
     */
    void onDisconnected();
}
