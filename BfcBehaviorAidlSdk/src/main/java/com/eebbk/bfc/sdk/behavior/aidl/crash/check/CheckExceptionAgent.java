package com.eebbk.bfc.sdk.behavior.aidl.crash.check;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.bfc.sdk.behavior.aidl.listener.InnerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function 主动检查异常信息
 * @date 17-7-3
 * @company 步步高教育电子有限公司
 */

public class CheckExceptionAgent {

    private static final List<ICheckException> mCheckList = new ArrayList<>();
    public static final String TAG = "CheckExceptionAgent";

    public static synchronized void clear() {
        mCheckList.clear();
    }

    public static synchronized void add(ICheckException checkException) {
        if (checkException == null) {
            return;
        }
        List<ICheckException> checkList = new ArrayList<>(mCheckList);
        for (ICheckException c : checkList) {
            if (TextUtils.equals(c.getExceptionType(), checkException.getExceptionType())) {
                // 无需重复添加
                return;
            }
        }
        mCheckList.add(checkException);
    }

    public static void check(Context context) {
        check(context, null);
    }

    /**
     * aidl调用检查必须要传innerListener,否则无法回调
     *
     * @param context
     * @param innerListener
     */
    public static synchronized void check(Context context, InnerListener innerListener) {
        if (mCheckList.size() <= 0 || context == null) {
            return;
        }
        List<ICheckException> checkList = new ArrayList<>(mCheckList);
        try {
            for (ICheckException checkException : checkList) {
                if (checkException == null) {
                    continue;
                }
                checkException.setListener(innerListener).check(context);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
