package com.eebbk.behavior.demo.test.function.errorcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hesn
 * @function
 * @date 16-9-30
 * @company 步步高教育电子有限公司
 */

public class ErrorCodeAdapter extends BaseAdapter {

    private List<Map<String, String>> mList;
    private LayoutInflater mInflater;

    public ErrorCodeAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<Map<String, String>> list){
        mList = list;
    }

    @Override
    public int getCount() {
        return ListUtils.isEmpty(mList) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_error_code_layout, null);
            holder.code = (TextView) convertView.findViewById(R.id.codeTv);
            holder.detial = (TextView) convertView.findViewById(R.id.detialTv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Map<String, String> map = mList.get(position);
        Iterator<Map.Entry<String, String>> it2 = map.entrySet().iterator();
        while(it2.hasNext()){
            Map.Entry<String, String> me = it2.next();
            holder.code.setText("[ " + me.getKey() + " ] : ");
            holder.detial.setText(me.getValue());
            break;
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView code;
        TextView detial;
    }
}
