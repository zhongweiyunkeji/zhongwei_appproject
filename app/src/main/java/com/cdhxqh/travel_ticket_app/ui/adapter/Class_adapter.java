package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 景点分类adapter
 */
public class Class_adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;

    private ArrayList<String> types = new ArrayList<String>();

    public Class_adapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }


    public void update(ArrayList<String> data) {
        if (data.size() > 0) {
            types = data;
        }


        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_grid_item, null);
                viewHolder = new ViewHolder();

                viewHolder.textView = (TextView) convertView
                        .findViewById(R.id.class_grid_text_id);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(types.get(position));

            return convertView;

        }
    }

    static class ViewHolder {

        /**
         * 文字*
         */
        TextView textView;

    }
}
