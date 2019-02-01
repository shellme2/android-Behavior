package com.eebbk.behavior.demo.test.function.query;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.common.AEventSpinnerActivity;
import com.eebbk.behavior.demo.test.function.query.adapter.ConfigAdapter;
import com.eebbk.bfc.common.app.AppUtils;
import com.eebbk.bfc.common.app.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据app包名查询行为采集库配置信息
 */
public class QueryConfigActivity extends AEventSpinnerActivity implements IConfigRefreshUI {

    private TextView mTipTv;
    private EditText mEditText;
    private ListView mListView;
    private LinearLayout mSingleQueryLayout;
    private ConfigAdapter mAdapter;
    private QueryConfigPresenter mQueryPresenter;
    private AtomicInteger mCurPosition = new AtomicInteger();
    private String mProgressMsg = "";
    private String mProgressModuleMsg = "";
    private String mProgressPercent = "";
    private List<String> mMenuList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_query_config_layout;
    }

    @Override
    protected void initView() {
        mTipTv = findView(R.id.tipTvId);
        mListView = findView(R.id.showLvId);
        mEditText = findView(R.id.editText);
        mSingleQueryLayout = findView(R.id.singleQueryLayout);

        mTipTv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        mQueryPresenter = new QueryConfigPresenter();
        mQueryPresenter.setOnRefreshUIListener(this);

        mAdapter = new ConfigAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 开始
     *
     * @param view
     */
    public void onClick(View view) {
        if(mQueryPresenter.isQuery()){
            ToastUtils.getInstance(getApplicationContext()).l("正在查询,请稍后...");
            return;
        }
        mProgressMsg = "";
        mProgressPercent = "";
        mProgressModuleMsg = "";
        mTipTv.setText("loading......");
        mAdapter.setList(null);
        mAdapter.notifyDataSetChanged();
        if(mCurPosition.get() == 0){
            mQueryPresenter.query(this, mQueryPresenter.getInstalledPackageList(getApplicationContext()));
        }else {
            List<String> list = new ArrayList<>();
            list.add(mEditText.getText().toString());
            mQueryPresenter.query(this, list);
        }
    }

    /**
     * 导出报告
     *
     * @param view
     */
    public void onExport(View view) {
        if(mQueryPresenter.isQuery()){
            ToastUtils.getInstance(getApplicationContext()).l("正在查询,请稍后...");
            return;
        }
        String filePath = mQueryPresenter.exportFile(getApplicationContext());
        mTipTv.setText("\n\n\n导出：" + filePath + "\n\n\n");
        ToastUtils.getInstance(getApplicationContext()).l("导出：" + filePath);
    }

    /**
     * 更新数据
     * @param configList
     */
    @Override
    public void onRefreshUI(final List<QueryConfigInfo> configList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTipTv.setText(mQueryPresenter.getReport(getApplicationContext(), configList, false));
                mAdapter.setList(configList);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 找不到
     */
    @Override
    public void onError(final String tip) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTipTv.setText(tip);
                mAdapter.setList(null);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onProgress(final int total, final int finished, final String msg, final boolean end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressModuleMsg += "\n" + msg;
                if(end){
                    mProgressMsg = mProgressModuleMsg + "\n" + mProgressMsg;
                    mProgressModuleMsg = "";
                }
                if(total != -1 && finished != -1){
                    mProgressPercent = finished + "/" + total;
                }
                mTipTv.setText(mProgressPercent + mProgressModuleMsg + "\n" + mProgressMsg);
            }
        });
    }

    @Override
    protected int setSpinnerId() {
        return R.id.spinner;
    }

    @Override
    protected List<String> setSpinnerList() {
        mMenuList.clear();
        mMenuList.add("查询所有已安装app配置");
        List<String> mMenuNameList = new ArrayList<>(mMenuList);
        List<String> packageNames = mQueryPresenter.getInstalledPackageList(getApplicationContext());
        for (String packageName : packageNames) {
            mMenuNameList.add(AppUtils.getAppName(getApplicationContext(), packageName));
        }
        mMenuList.addAll(packageNames);
        return mMenuNameList;
    }

    @Override
    protected void initSpinnerData() {

    }

    @Override
    protected void onSelectedEventType(int position, int eventType) {
        mCurPosition.set(position);
        if(position == 0){
            mSingleQueryLayout.setVisibility(View.GONE);
        }else {
            mSingleQueryLayout.setVisibility(View.VISIBLE);
            mEditText.setText(mMenuList.get(position));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueryPresenter.destroy();
    }
}
