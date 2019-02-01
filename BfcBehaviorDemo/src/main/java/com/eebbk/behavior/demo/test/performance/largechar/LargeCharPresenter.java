package com.eebbk.behavior.demo.test.performance.largechar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.text.TextUtils;

import com.eebbk.behavior.demo.MyApplication;
import com.eebbk.bfc.sdk.behavior.utils.ExecutorsUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * @author hesn
 * @function
 * @date 16-10-10
 * @company 步步高教育电子有限公司
 */

public class LargeCharPresenter {

    private ClipboardManager mClipboard;
    private ClipData mClip;
    private OnLargeCharListener mListener;
    private boolean isDoing = false;

    public LargeCharPresenter(OnLargeCharListener l) {
        mListener = l;
        mClipboard = (ClipboardManager) MyApplication.getContext().getSystemService(CLIPBOARD_SERVICE);
    }

    /**
     * 循环复制字符
     *
     * @param content
     * @param times
     */
    public synchronized void loopCopy(final String content, final String times) {
        if (mListener == null) {
            return;
        }
        if (isDoing) {
            mListener.onTip("正在处理,请稍后...");
            return;
        }
        if (TextUtils.isEmpty(times)) {
            mListener.onTip("请输入正确的循环次数");
            return;
        }
        if (TextUtils.isEmpty(content)) {
            mListener.copyContent("");
            mListener.onTip("完成循环复制");
            return;
        }
        isDoing = true;
        mListener.onTip("doing...");
        ExecutorsUtils.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                long timesLong = str2Long(times);
                StringBuilder dealContent = new StringBuilder(100);
                for (int i = 0; i < timesLong; i++) {
                    dealContent.append(content);
                }
                mListener.copyContent(dealContent.toString());
                mListener.onTip("完成循环复制");
                isDoing = false;
            }
        });
    }

    /**
     * 复制到粘帖板
     *
     * @param content
     */
    public void copy2Clip(String content) {
        mClip = ClipData.newPlainText("text", content);
        mClipboard.setPrimaryClip(mClip);
    }

    /**
     * 计算字符长度
     *
     * @param s
     * @return
     */
    public String getWordCount(String s) {
        s = s.replaceAll("[^\\x00-\\xff]", "**");
        int length = s.length();
        return "当前字符长度:" + length;
    }

    private long str2Long(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    interface OnLargeCharListener {
        /**
         * 提示语
         *
         * @param tip
         */
        void onTip(String tip);

        /**
         * 返回复制后内容
         *
         * @param content
         */
        void copyContent(String content);
    }
}
