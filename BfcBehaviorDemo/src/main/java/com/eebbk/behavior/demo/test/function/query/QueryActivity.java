package com.eebbk.behavior.demo.test.function.query;

import android.content.ContentValues;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eebbk.behavior.demo.common.ABaseActivity;
import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.function.query.adapter.DataAdapter;
import com.eebbk.behavior.demo.test.function.query.utils.FormatUtils;
import com.eebbk.behavior.demo.test.function.query.utils.ListUtils;
import com.eebbk.behavior.demo.test.function.query.view.ItemDialog;

import java.util.List;

/**
 * 根据app包名查询其本地行为采集数据
 */
public class QueryActivity extends ABaseActivity implements IRefreshUI {

    private TextView mTipTv;
    private EditText mEditText;
    private ListView mListView;
    private DataAdapter mAdapter;
    private QueryPresenter mQueryPresenter;
    private ItemDialog mDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_query_layout;
    }

    @Override
    protected void initView() {
        mTipTv = findView(R.id.tipTvId);
        mListView = findView(R.id.showLvId);
        mEditText = findView(R.id.editText);
    }

    @Override
    protected void initData() {
        mQueryPresenter = new QueryPresenter();
        mDialog = new ItemDialog();
        mDialog.setItemList(mQueryPresenter.getDialogItem(), mQueryPresenter.getDefaultChecked());
        mDialog.setOnRefreshUI(this);
        mQueryPresenter.setOnRefreshUIListener(this);

        mAdapter = new DataAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected boolean isShowLog() {
        return false;
    }

    /**
     * 刷新/显示
     *
     * @param view
     */
    public void onClick(View view) {
        mTipTv.setText("loading......");
        mQueryPresenter.query(this, mEditText.getText().toString());
    }

    /**
     * 选择显示的数据
     *
     * @param view
     */
    public void onItemDialog(View view) {
        mDialog.show(this);
    }

    /**
     * 更新数据
     *
     * @param cacheList 缓存池中数据
     * @param dbList    数据库中数据
     * @param fileList  打包但还没上传的文件中数据
     */
    @Override
    public void onRefreshUI(final List<ContentValues> cacheList
            , final List<ContentValues> dbList, final List<ContentValues> fileList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTipTv.setText(FormatUtils.formatTitle(ListUtils.size(cacheList)
                        , ListUtils.size(dbList), ListUtils.size(fileList)));
                mAdapter.setList(cacheList, dbList, fileList);
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
                mAdapter.setList(null, null, null);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemChange(List<Integer> checkedIndex) {
        mQueryPresenter.setItemConfig(checkedIndex);
        mAdapter.notifyDataSetChanged();
    }
}
