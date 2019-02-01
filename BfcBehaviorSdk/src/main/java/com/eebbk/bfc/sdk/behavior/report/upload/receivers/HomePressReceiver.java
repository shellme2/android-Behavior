package com.eebbk.bfc.sdk.behavior.report.upload.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class HomePressReceiver extends BroadcastReceiver {
    private final static String SYSTEM_REASON = "reason";
    private final static String SYSTEM_HOME_KEY = "homekey";

    private HomePressListener mListener = null;

    /**
     * Home 键接听器。
     *
     * @author Administrator
     */
    public interface HomePressListener {

        /**
         * 按下 home 键
         */
        void onHomePressed();
    }

    public HomePressReceiver(HomePressListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || null == mListener) {
            return;
        }
        String action = intent.getAction();
        if (!TextUtils.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS, action)) {
            return;
        }
        if (TextUtils.equals(intent.getStringExtra(SYSTEM_REASON), SYSTEM_HOME_KEY)) {
            mListener.onHomePressed();
        }
    }
}