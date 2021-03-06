package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.GoodsList;
import com.cdhxqh.travel_ticket_app.ui.activity.LoginActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.Order_Tracking_Activity;
import com.cdhxqh.travel_ticket_app.ui.activity.ReservationActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.Tickets_Detail_Activity;

import java.text.DecimalFormat;
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
                holder.imagetitle.setImageResource(R.drawable.ic_menu_adult);
                break;
            case "组合票":
                holder.imagetitle.setImageResource(R.drawable.ic_menu_group);
                break;
            case "儿童票":
                holder.imagetitle.setImageResource(R.drawable.ic_menu_child);
                break;
            case "学生票":
                holder.imagetitle.setImageResource(R.drawable.ic_menu_pupil);
                break;
            case "优待票":
                holder.imagetitle.setImageResource(R.drawable.ic_menu_vip);
                break;
            case "其它":
                holder.imagetitle.setImageResource(R.drawable.ic_menu_other);
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
            holder = new ViewHolderChild(convertView);
            holder.textView = (TextView) convertView
                    .findViewById(R.id.ticket_detail_name_id);
            holder.priceView = (TextView) convertView
                    .findViewById(R.id.ticket_detail_price_id);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }
        final int groupPositions = groupPosition;
        final int childPositions = childPosition;
        convertView.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tittle_reservation", child.get(groupPositions).get(childPositions).getGoods_name());
                bundle.putString("end_date_id_a", "2015-12-31 17:00:00");
                bundle.putString("bookingNum", child.get(groupPositions).get(childPositions).getShop_price().toString());
                bundle.putString("unit_fare", child.get(groupPositions).get(childPositions).getShop_price().toString());
                bundle.putString("goodsId", child.get(groupPositions).get(childPositions).getGoods_id());
                intent.putExtras(bundle);

                intent.setClass(context, ReservationActivity.class);
                context.startActivity(intent);
            }
        });
        holder.textView.setText(child.get(groupPosition).get(childPosition).getGoods_name());
        holder.priceView.setText("¥"+child.get(groupPosition).get(childPosition).getShop_price());
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
        RelativeLayout expand_able_list_id;

        public ViewHolderChild() {

        }

        public ViewHolderChild(View paramView) {
            // expand_able_list_id = (RelativeLayout) paramView.findViewById(R.id.expand_able_list_id);
        }
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
