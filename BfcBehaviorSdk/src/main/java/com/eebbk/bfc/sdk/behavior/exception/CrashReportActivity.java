package com.eebbk.bfc.sdk.behavior.exception;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Crash report activity. Due this project is a jar library,
 * you need declare it in your application AndroidMainfest.xml.
 *
 * @author humingming <hmm@dw.gdbbk.com>
 */
public class CrashReportActivity extends Activity implements OnClickListener {

    private String mCrashInfo;

    private TextView mTvCrashInfo;
    private TextView mTvTitle;
    private Button mBtnConfirm;
    private Button mBtnMagnify;//放大
    private Button mBtnReduce;//缩小
    private static final int PADDING = 5;
    private static final String CONFIRM = "关闭";
    private static final String TITLE = "异常信息";
    private static final String FONT_MAGNIFY = "字体放大";
    private static final String FONT_REDUCE = "字体缩小";
    private static final float FONT_DEFAULT_SIZE = 20f;
    private float mCurrentFontSize = FONT_DEFAULT_SIZE;
    private static final int LOG_UNIT = TypedValue.COMPLEX_UNIT_SP;
    private static final float CHANGE_SIZE = 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // we clean the re-start count here.
        // if the activity is ready to launch(onCreate),
        // in this time the application is crash, we count is clean by un expected.
        CrashHandler.getInstance().cleanReStartCount();
    }

    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }
        if (view.equals(mBtnConfirm)) {
            onBtnConfirmClick();//确定
        } else if (view.equals(mBtnMagnify)) {
            changeTextSize(CHANGE_SIZE);//放大
        } else if (view.equals(mBtnReduce)) {
            changeTextSize(-CHANGE_SIZE);//缩小
        }
    }

    private void init() {
        setContentView(createView());
        initConfig();
        initView();
    }

    // can't use the layout xml in jar, suck !!.
    // maybe we can think about anther way to use the layout xml.
    private View createView() {
        LinearLayout content = initRootLayout();
        initTvTitle();
        initTvCrashInfo();
        initBtnConfirm();

        content.setBackgroundColor(Color.BLACK);
        content.addView(mTvTitle);
        content.addView(mTvCrashInfo);
        content.addView(initFontZoom());
        content.addView(mBtnConfirm);
        return content;
    }

    private LinearLayout initRootLayout() {
        LinearLayout content = new LinearLayout(this);
        ViewGroup.LayoutParams lpVG = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(lpVG);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setWeightSum(1f);
        return content;
    }

    private void initTvTitle() {
        mTvTitle = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTvTitle.setLayoutParams(lp);
        mTvTitle.setText(TITLE);
        mTvTitle.setGravity(Gravity.CENTER);
        mTvTitle.setTextSize(LOG_UNIT, FONT_DEFAULT_SIZE);
        mTvTitle.setPadding(PADDING, PADDING, PADDING, PADDING);
        mTvTitle.setTextColor(Color.BLACK);
        mTvTitle.setBackgroundColor(Color.WHITE);
        mTvTitle.setSingleLine(false);
    }

    private void initTvCrashInfo() {
        mTvCrashInfo = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        mTvCrashInfo.setLayoutParams(lp);
        mTvCrashInfo.setTextSize(LOG_UNIT, mCurrentFontSize);

        mTvCrashInfo.setTextColor(Color.WHITE);
        mTvCrashInfo.setPadding(PADDING, PADDING, PADDING, PADDING);
        //添加滚动效果
        mTvCrashInfo.setMovementMethod(new ScrollingMovementMethod());

        mTvCrashInfo.setSingleLine(false);
    }

    private LinearLayout initFontZoom() {
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(llp);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        mBtnMagnify = getBottomBtn(FONT_MAGNIFY);
        mBtnReduce = getBottomBtn(FONT_REDUCE);
        ll.addView(mBtnMagnify);
        ll.addView(mBtnReduce);

        return ll;
    }

    private Button getBottomBtn(String text) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        Button btn = new Button(this);
        btn.setLayoutParams(lp);
        btn.setText(text);
        btn.setGravity(Gravity.CENTER);
        btn.setTextSize(FONT_DEFAULT_SIZE);
        btn.setOnClickListener(this);
        return btn;
    }

    private void initBtnConfirm() {
        mBtnConfirm = new Button(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mBtnConfirm.setLayoutParams(lp);
        mBtnConfirm.setText(CONFIRM);
        mBtnConfirm.setGravity(Gravity.CENTER);
        mBtnConfirm.setTextSize(FONT_DEFAULT_SIZE);
    }

    private void initConfig() {
        mCrashInfo = CrashHandler.getInstance().loadLastCrashReport();
    }

    private void initView() {
        if (null != mTvCrashInfo && !TextUtils.isEmpty(mCrashInfo)) {
            mTvCrashInfo.setText(mCrashInfo);
        }

        mBtnConfirm.setOnClickListener(this);
    }

    private void onBtnConfirmClick() {
        finish();
    }

    /**
     * 修改字体大小
     *
     * @param changeSize
     */
    private void changeTextSize(float changeSize) {
        mCurrentFontSize += changeSize;
        mTvCrashInfo.setTextSize(LOG_UNIT, mCurrentFontSize);
    }

}
