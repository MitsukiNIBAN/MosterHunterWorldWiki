package com.satou.wiki.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.satou.wiki.R;
import com.satou.wiki.data.entity.Unit;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class ModuleListAdapter extends BaseAdapter {

    public static final int CONTENT = 0;
    public static final int FIRST_LEVEL = 1;
    public static final int SECOND_LEVEL = 2;
    public static final int THIRD_LEVEL = 3;
    private final int TYPE_COUNT = 4;

    private List<Unit> data;
    private Context context;

    public ModuleListAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
    }

    public void refreshData(List<Unit> d) {
        data.clear();
        data.addAll(d);
        notifyDataSetChanged();
    }

    public void refreshData(Unit u) {
        data.clear();
        data.add(u);
        notifyDataSetChanged();
    }

    public void addData(List<Unit> data) {
        data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(Unit u) {
        data.add(u);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getItemType() == FIRST_LEVEL)
            return FIRST_LEVEL;
        else if (data.get(position).getItemType() == SECOND_LEVEL)
            return SECOND_LEVEL;
        else
            return CONTENT;
//        return data.get(position).getItemType();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_string, null);
            viewHolder = new ViewHolder();
            viewHolder.content = convertView.findViewById(R.id.tv_content);
            viewHolder.time = convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.content.setText(data.get(i).getContent() + "");

        //item的样式
        switch (getItemViewType(i)) {
            case FIRST_LEVEL:
                break;
            case SECOND_LEVEL:
                break;
            case CONTENT:
                if (!data.get(i).getTime().equals("null")){
                    viewHolder.time.setText(data.get(i).getTime() + "");
                }else {
                    viewHolder.time.setText("");
                }
                break;
            default:
                break;
        }

        return convertView;
    }

    class ViewHolder {
        TextView content;
        TextView time;
    }
}
