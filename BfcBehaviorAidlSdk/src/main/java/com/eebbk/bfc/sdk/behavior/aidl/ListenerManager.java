package com.eebbk.bfc.sdk.behavior.aidl;

import com.eebbk.bfc.sdk.behavior.aidl.listener.OnServiceConnectionListener;

/**
 * @author hesn
 * @function
 * @date 17-5-8
 * @company 步步高教育电子有限公司
 */

class ListenerManager {
    private OnServiceConnectionListener onServiceConnectionListener;

    public OnServiceConnectionListener getOnServiceConnectionListener() {
        return onServiceConnectionListener;
    }

    public void setOnServiceConnectionListener(OnServiceConnectionListener l) {
        this.onServiceConnectionListener = l;
    }

    public void destroy(){
        onServiceConnectionListener = null;
    }
}
