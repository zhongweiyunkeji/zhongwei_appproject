package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.GoodsList;

import java.util.List;

/**
 * 自定义可扩展的ExpandableListAdapter控件
 */
public class Tickets_ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> group;
    private List<List<GoodsList>> child;

    public Tickets_ExpandableListAdapter(Context context, List<String> group,
                                         List<List<GoodsList>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(childPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolderGroup holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.expandablelistadapter_group, null);
            holder = new ViewHolderGroup();
            holder.textView = (TextView) convertView
                    .findViewById(R.id.group_text_id);

            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.e_stus_id);
            holder.imagetitle = (ImageView) convertView
                    .findViewById(R.id.group_image_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderGroup) convertView.getTag();
        }
        holder.textView.setText(group.get(groupPosition));
        if(isExpanded){
            holder.imageView.setBackgroundResource(R.drawable.ic_up);
        }else{
            holder.imageView.setBackgroundResource(R.drawable.ic_down);
        }
        switch (group.get(groupPosition)){
            case "成人票":
                holder.imagetitle.setImageResource(R.drawable.ic_adult_ticket);
                break;
            case "组合票":
                holder.imagetitle.setImageResource(R.drawable.ic_group);
                break;
            case "儿童票":
                holder.imagetitle.setImageResource(R.drawable.ic_child);
                break;
            case "学生票":
                holder.imagetitle.setImageResource(R.drawable.ic_pupil);
                break;
            case "优待票":
                holder.imagetitle.setImageResource(R.drawable.ic_vip);
                break;
            case "其它":
                holder.imagetitle.setImageResource(R.drawable.ic_adult_ticket);
                break;
        }

        return convertView;

    }


    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.expandablelistadapter_child, null);
            holder = new ViewHolderChild();
            holder.textView = (TextView) convertView
                    .findViewById(R.id.ticket_detail_name_id);
            holder.priceView = (TextView) convertView
                    .findViewById(R.id.ticket_detail_price_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        holder.textView.setText(child.get(groupPosition).get(childPosition).getGoods_name());
        holder.priceView.setText("￥"+child.get(groupPosition).get(childPosition).getShop_price());
        return convertView;
    }


    /**标题**/
    class ViewHolderGroup {
        /**标题**/
        TextView textView;
        /**标题图标**/
        ImageView imagetitle;
        /**图标**/
        ImageView imageView;

    }

    /**内容**/
    class ViewHolderChild {
        TextView textView;
        TextView priceView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
