package com.eebbk.behavior.demo.test.function.query.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eebbk.behavior.demo.R;
import com.eebbk.behavior.demo.test.function.query.QueryConfigInfo;
import com.eebbk.behavior.demo.test.function.query.report.Error;
import com.eebbk.bfc.sdk.behavior.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hesn
 * @function
 * @date 16-8-29
 * @company 步步高教育电子有限公司
 */

public class ConfigAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<QueryConfigInfo> mList = new ArrayList<>();

    public ConfigAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    public void setList(List<QueryConfigInfo> configList) {
        mList.clear();
        if(configList == null || configList.size() == 0){
            return;
        }
        mList.addAll(configList);
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
        QueryConfigInfo info = mList.get(position);
        holder.tv.setText(getText(info));
        return convertView;
    }

    private String getText(QueryConfigInfo info){
        StringBuilder sb = new StringBuilder();
        sb.append("应用名：").append(info.getAppName()).append("\n");
        sb.append("包名：").append(info.getPackageName()).append("\n");
        sb.append("版本号：").append(String.valueOf(info.getVersionCode())).append("\n");
        if(info.getErrorCode() != Error.Code.SUCCESS) {
            sb.append("error：").append(Error.getErrorMsg(info.getErrorCode())).append("\n");
        }else {
            sb.append("配置信息：").append(formatString(info.getJson())).append("\n");
        }
        return sb.toString();
    }

    private String formatString(String text){

        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }
        return json.toString();
    }

    private static class ViewHolder{
        TextView tv;
    }
}
