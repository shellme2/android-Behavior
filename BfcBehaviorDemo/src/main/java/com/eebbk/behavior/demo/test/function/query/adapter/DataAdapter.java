package com.eebbk.behavior.demo.test.function.query.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.function.query.utils.FormatUtils;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-29
 * @company 步步高教育电子有限公司
 */

public class DataAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<ContentValues> mList = new ArrayList<>();
    private int cacheTitltIndex = -1;
    private int dbTitltIndex = -1;
    private int fileTitltIndex = -1;

    public DataAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<ContentValues> cacheList
            , List<ContentValues> dbList, List<ContentValues> fileList) {
        mList.clear();
        clearTitleIndex();
        if(!ListUtils.isEmpty(cacheList)){
            cacheTitltIndex = getCount();
            mList.addAll(cacheList);
        }
        if(!ListUtils.isEmpty(dbList)){
            dbTitltIndex = getCount();
            mList.addAll(dbList);
        }
        if(!ListUtils.isEmpty(fileList)){
            fileTitltIndex = getCount();
            mList.addAll(fileList);
        }
    }

    @Override
    public int getCount() {
        return ListUtils.isEmpty(mList) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_query_layout, null);
            holder.tv = (TextView) convertView.findViewById(R.id.tvId);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        String title = getListTitle(position);
        ContentValues values = mList.get(position);
        holder.tv.setText(title + FormatUtils.getAsString(values, ItemConfig.ITEM));
        return convertView;
    }

    private String getListTitle(int position){
        String title = "";
        if(position == cacheTitltIndex){
            title = FormatUtils.formatListTitle("缓存池");
        }else if(position == dbTitltIndex){
            title = FormatUtils.formatListTitle("数据库");
        }else if(position == fileTitltIndex){
            title = FormatUtils.formatListTitle("文件");
        }
        return title;
    }

    private void clearTitleIndex(){
        cacheTitltIndex = -1;
        dbTitltIndex = -1;
        fileTitltIndex = -1;
    }

    private static class ViewHolder{
        TextView tv;
    }
}
