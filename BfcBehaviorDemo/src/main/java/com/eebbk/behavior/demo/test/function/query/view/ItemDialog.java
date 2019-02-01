package com.eebbk.behavior.demo.test.function.query.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ListView;

import com.eebbk.behavior.demo.test.function.query.IRefreshUI;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-9-12
 * @company 步步高教育电子有限公司
 */

public class ItemDialog {
    private ListView mListView;
    private String[] mItemList;
    private boolean[] mCheckedItems;
    private IRefreshUI mRefreshUI;

    public void setItemList(String[] itemList,List<Integer> checkeds){
        mItemList = itemList;
        mCheckedItems = new boolean[size()];
        int size = checkeds.size();
        for (int i = 0 ; i < size ; i++){
            mCheckedItems[checkeds.get(i)] = true;
        }
    }

    public void setOnRefreshUI(IRefreshUI iRefreshUI){
        mRefreshUI = iRefreshUI;
    }

    public void show(final Context context){
        AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle("请选择你要显示的项：")
                .setMultiChoiceItems(mItemList, mCheckedItems,
                        new DialogInterface.OnMultiChoiceClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked){
                                // TODO Auto-generated method stub

                            }
                        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        setCheckedItems();
                    }
                }).setNegativeButton("取消", null).create();
        mListView = builder.getListView();
        builder.show();
    }

    /**
     * 设置对话框选择项
     */
    private void setCheckedItems(){
        int size = size();
        List<Integer> checkedIndex = new ArrayList<>();
        for (int i = 0 ; i < size ; i++){
            boolean checked = mListView.getCheckedItemPositions().get(i);
            mCheckedItems[i] = checked;
            if(checked){
                checkedIndex.add(i);
            }
        }
        callbackChange(checkedIndex);
    }

    /**
     * 设置数据显示项
     * @param checkedIndex
     */
    private void callbackChange(List<Integer> checkedIndex){
        if(mRefreshUI != null){
            mRefreshUI.onItemChange(checkedIndex);
        }
    }

    private int size(){
        return mItemList == null ? 0 : mItemList.length;
    }

}
