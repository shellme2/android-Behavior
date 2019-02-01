package com.eebbk.behavior.demo.test.function.query;

import android.content.Context;
import android.text.TextUtils;

import com.eebbk.behavior.demo.test.function.query.adapter.ItemConfig;
import com.eebbk.behavior.demo.test.function.query.aidl.QueryRemoteManager;
import com.eebbk.behavior.demo.test.function.query.utils.ListUtils;
import com.eebbk.behavior.demo.utils.ExecutorsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-25
 * @company 步步高教育电子有限公司
 */

public class QueryPresenter {

    private QueryRemoteManager mQueryRemoteMgr;
    private IRefreshUI mRefreshUI;
    private boolean isQuery = false;

    public QueryPresenter() {
        mQueryRemoteMgr = new QueryRemoteManager();
    }

    public void setOnRefreshUIListener(IRefreshUI refreshUI) {
        synchronized (QueryPresenter.class){
            mRefreshUI = refreshUI;
        }
    }

    public void query(final Context context, final String appPackageName) {
        synchronized (QueryPresenter.class) {
            if (mRefreshUI == null) {
                return;
            }
            if (context == null || TextUtils.isEmpty(appPackageName)) {
                mRefreshUI.onError("找不到该包名应用，请确认包名是否正确");
                return;
            }
            if (isQuery) {
                mRefreshUI.onError("正在查询,请稍后...");
                return;
            }
            isQuery = true;
            ExecutorsUtils.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //因为aidl的action是可以动态设置，所以每次都绑定获取后就解绑
                        mQueryRemoteMgr.bindService(context, appPackageName);
                        //不延时获取不上数据
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mRefreshUI.onRefreshUI(ListUtils.jsonList2ValueList(mQueryRemoteMgr.queryCache())
                                , ListUtils.jsonList2ValueList(mQueryRemoteMgr.queryDB())
                                , null);
                        mQueryRemoteMgr.unbindService(context);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        mRefreshUI.onError("找不到该包名应用，请确认包名是否正确");
                    } catch (Exception e) {
                        e.printStackTrace();
                        mRefreshUI.onError("error");
                    }
                    isQuery = false;
                }
            });
        }
    }

    /**
     * 设置数据显示项
     *
     * @param checkedIndex
     */
    public void setItemConfig(List<Integer> checkedIndex) {
        int size = checkedIndex.size();
        ItemConfig.ITEM = new String[size];
        for (int i = 0; i < size; i++) {
            ItemConfig.ITEM[i] = getDialogItem()[checkedIndex.get(i)];
        }
    }

    public List<Integer> getDefaultChecked() {
        int size = ItemConfig.ITEM.length;
        int allSize = getDialogItem().length;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            String value = ItemConfig.ITEM[i];
            for (int j = 0; j < allSize; j++) {
                if (getDialogItem()[j].equals(value)) {
                    list.add(j);
                    break;
                }
            }
        }
        return list;
    }

    public String[] getDialogItem() {
        return ItemConfig.ALL_ITEM;
    }

}
