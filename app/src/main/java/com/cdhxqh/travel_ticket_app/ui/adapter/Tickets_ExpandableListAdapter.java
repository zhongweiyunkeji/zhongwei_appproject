package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

import java.util.List;

/**
 * 自定义可扩展的ExpandableListAdapter控件
 */
public class Tickets_ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> group;
    private List<List<String>> child;

    public Tickets_ExpandableListAdapter(Context context, List<String> group,
                                         List<List<String>> child) {
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
        return child.size();
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        holder.textView.setText(child.get(groupPosition).get(childPosition));
        return convertView;
    }


    /**标题**/
    class ViewHolderGroup {
        /**标题**/
        TextView textView;
        /**图标**/
        ImageView imageView;

    }

    /**内容**/
    class ViewHolderChild {
        TextView textView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
