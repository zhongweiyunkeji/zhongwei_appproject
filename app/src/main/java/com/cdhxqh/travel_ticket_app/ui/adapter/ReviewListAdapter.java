package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;

import java.util.ArrayList;

/**
 * Created by think on 2015/8/6.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private static final String TAG = "ReviewListAdapter";
    Context mContext;
    ArrayList<View> viewList = new ArrayList<View>();


    ArrayList<String> list=new ArrayList<String>();

    public ReviewListAdapter(Context context) {

        this.mContext = context;
    }

    public void setView(View v) {
        viewList.add(v);
    }

    public ArrayList<View> getView() {
        return viewList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_adapter, parent, false);
        setView(v);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String s =list.get(position);
        holder.textView.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        /**头像**/
        public ImageView iconView;
        /**名字**/
        public TextView nameView;
        /**时间**/
        public TextView dateView;
        /**内容**/
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.review_text);
        }
    }


    public void update(ArrayList<String> data, boolean merge) {
        Log.i(TAG, "mItems=" + list.size());
        if (merge && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String obj = list.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j) == obj) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        list = data;

        notifyDataSetChanged();
    }



}
