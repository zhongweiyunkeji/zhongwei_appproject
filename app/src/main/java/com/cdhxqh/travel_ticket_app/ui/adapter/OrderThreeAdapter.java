package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.OrderGoods;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexian on 2015/8/4.
 */
public class OrderThreeAdapter extends BaseExpandableListAdapter {

    private List<OrderModel> groupList = new ArrayList<OrderModel>(0);
    private Map<String, List<OrderGoods>> itemList = new HashMap<String, List<OrderGoods>>(0);
    private Context context;
    private LayoutInflater inflater;

    public OrderThreeAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public OrderThreeAdapter(Context context, List<OrderModel> groupList, Map<String, List<OrderGoods>> itemList){
        this(context);
        this.groupList = groupList;
        this.itemList = itemList;
    }

    public void update(List<OrderModel> glist, Map<String, List<OrderGoods>> ilist){
        for(OrderModel order : glist){
            boolean flag = true;
            String ordernum = order.getOrderSn(); // 订单号
            for(OrderModel module : groupList){
                String sn = module.getOrderSn();
                if(ordernum.equals(sn)){
                    flag = false;
                    break;
                }
            }
            if(flag){
                groupList.add(order);
                itemList.put(ordernum, ilist.get(ordernum));
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupList.get(groupPosition).getOrderSn()).get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String orderSn = groupList.get(groupPosition).getOrderSn();
        return itemList.get(orderSn).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
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
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_order_three_in_group, null);
            viewHolder = new GroupViewHolder(convertView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder)convertView.getTag();
        }

        ImageView icon = viewHolder.order_three_in_group_img;
        if(isExpanded){
            icon.setImageResource(R.drawable.ic_up);  // 展开时时设置图标  ic_down
        } else {
            icon.setImageResource(R.drawable.ic_down);  // 折叠时时设置图标  ic_up
        }

        OrderModel text = (OrderModel)getGroup(groupPosition);
        viewHolder.order_three_in_group_title.setText(text.getOrderSn());
        viewHolder.order_three_in_group_status.setText(text.getStatus());

        FrameLayout fl = (FrameLayout)convertView;
        fl.setPadding(0,-10,0,0);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        OrderModel model = (OrderModel)this.getGroup(groupPosition);
        OrderGoods msg = (itemList.get(model.getOrderSn())).get(childPosition);
        if(childPosition < (itemList.get(model.getOrderSn())).size()-1){
            ItemViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_order_three_in_item, null);
                viewHolder = new ItemViewHolder(convertView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemViewHolder)convertView.getTag();
            }

            ImageLoader.getInstance().displayImage(msg.getImgurl(), viewHolder.order_three_in_item_img);  // 设置图片
            viewHolder.order_three_in_item_title.setText(msg.getGoodsName());
            viewHolder.order_three_in_item_ordertime.setText(model.getAddTime());
            viewHolder.order_three_in_item_orderqty.setText(msg.getGoodsNumber()+"");
            viewHolder.order_three_in_item_ordertotal.setText("￥"+(msg.getGoodsNumber()*msg.getGoodsPrice())+"");
            // viewHolder.expand_text_04.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

            return convertView;
        } else {
            FooterViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_order_three_in_item_footer, null);
                viewHolder = new FooterViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FooterViewHolder)convertView.getTag();
            }

           String status =  model.getStatus();
            if("订单未确认".equals(status)){
                viewHolder.canButton.setVisibility(View.VISIBLE);  // 显示取消按钮
                viewHolder.payButton.setVisibility(View.VISIBLE);  // 显示支付按钮
            } else
            if("已取消".equals(status)){
                viewHolder.delButton.setVisibility(View.VISIBLE);  // 显示删除按钮
            } else
            if("支付成功".equals(status)){
                viewHolder.rtnButton.setVisibility(View.VISIBLE);  // 显示退票按钮
            }

            return convertView;
        }
    }

    // 组的ViewHolder
    static class GroupViewHolder {
        //文字*
        TextView order_three_in_group_title;     // 订单编号
        TextView order_three_in_group_status;   // 订单状态
        ImageView order_three_in_group_img;     //  展开和关闭图片

        public GroupViewHolder(View convertView){
            order_three_in_group_title  = (TextView)convertView.findViewById(R.id.order_three_in_group_title );
            order_three_in_group_status = (TextView)convertView.findViewById(R.id.order_three_in_group_status );
            order_three_in_group_img    = (ImageView)convertView.findViewById(R.id.order_three_in_group_img );
        }
    }

    // 子项的ViewHolder
    static class ItemViewHolder {
        //文字*
        ImageView order_three_in_item_img;          // 景点图片
        TextView order_three_in_item_title;        // 景点名称
        TextView order_three_in_item_ordertime;   // 够票时间
        TextView order_three_in_item_orderqty;    // 购票数量
        TextView order_three_in_item_ordertotal;  // 购票总额

        public ItemViewHolder(View convertView){
            order_three_in_item_img          = (ImageView)convertView.findViewById(R.id.order_three_in_item_img );
            order_three_in_item_title        = (TextView)convertView.findViewById(R.id.order_three_in_item_title );
            order_three_in_item_ordertime   = (TextView)convertView.findViewById(R.id.order_three_in_item_ordertime );
            order_three_in_item_orderqty    = (TextView)convertView.findViewById(R.id.order_three_in_item_orderqty );
            order_three_in_item_ordertotal  = (TextView)convertView.findViewById(R.id.order_three_in_item_ordertotal );
        }

    }

    static class FooterViewHolder {

        Button rtnButton;  // 退票按钮
        Button canButton;  // 取消按钮
        Button payButton;  // 继续支付按钮
        Button delButton;  // 删除按钮

        public FooterViewHolder(View view){
            rtnButton = (Button)view.findViewById(R.id.order_three_item_footer_ret_btn);
            canButton = (Button)view.findViewById(R.id.order_three_item_footer_can_btn);
            payButton = (Button)view.findViewById(R.id.order_three_item_footer_pay_btn);
            delButton = (Button)view.findViewById(R.id.order_three_item_footer_del_btn);

            /*
            RelativeLayout relativeLayout = new RelativeLayout(context);

            LinearLayout layout = new LinearLayout(context);
            // LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器的参数
            AbsListView.LayoutParams parentLaout = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器的参数
            layout.setOrientation(LinearLayout.HORIZONTAL);//所有组件垂直摆放
            layout.setGravity(Gravity.RIGHT);  // 右对齐
            layout.setLayoutParams(parentLaout);

            LinearLayout.LayoutParams rtnBtnLaout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器参数
            rtnButton = new Button(context);
            rtnButton.setText("退票");
            rtnButton.setBackgroundColor(Color.parseColor("#067CE3"));  // 设置背景颜色
            rtnButton.setLayoutParams(rtnBtnLaout);

            LinearLayout.LayoutParams canBtnLaout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器参数
            canButton = new Button(context);
            canButton.setText("取消订单");
            canButton.setBackgroundColor(Color.parseColor("#80848D"));  // 设置背景颜色
            canButton.setLayoutParams(canBtnLaout);

            LinearLayout.LayoutParams payBtnLaout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器参数
            payButton = new Button(context);
            payButton.setText("继续支付");
            payButton.setBackgroundColor(Color.parseColor("#FC9A01"));  // 设置背景颜色
            payButton.setLayoutParams(payBtnLaout);

            LinearLayout.LayoutParams delBtnLaout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//定义布局管理器参数
            delButton = new Button(context);
            delButton.setText("删除订单");
            delButton.setBackgroundColor(Color.parseColor("#FC6B6F"));  // 设置背景颜色
            delButton.setLayoutParams(delBtnLaout);

//            layout.addView(rtnButton, rtnBtnLaout);
//            layout.addView(canButton, canBtnLaout);
//            layout.addView(payButton, payBtnLaout);
//            layout.addView(delButton, delBtnLaout);

             RelativeLayout.LayoutParams relLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
             relLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  // 相对父组件靠右
             relLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);  // 水平靠右对齐
             relativeLayout.setLayoutParams(relLayoutParams);
             relativeLayout.addView(delButton, relLayoutParams);

             relLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
             relLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  // 相对父组件靠右
             relLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);  // 水平靠右对齐
             relativeLayout.setLayoutParams(relLayoutParams);
             relativeLayout.addView(payButton, relLayoutParams);


             layout.addView(relativeLayout, parentLaout);
             */
        }

    }

    public void clearData(){
        groupList.clear();
        itemList.clear();
    }

}
