package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Created by hexian on 2015/8/4.
 */
public class SearchScenicAdapter extends RecyclerView.Adapter<SearchScenicAdapter.ViewHolder>  {
    public static final String TAG = "SearchScenicAdapter";
    private List<Ecs_brand> list = new ArrayList(0);
    Context mContext;

    public SearchScenicAdapter(Context paramContext) {
        this.mContext = paramContext;
    }

    public int getItemCount() {
        return this.list.size();
    }

    public List<Ecs_brand> getList() {
        return this.list;
    }

    public void onBindViewHolder(SearchScenicAdapter.ViewHolder paramViewHolder, int paramInt) {
        Ecs_brand localEcs_brand = (Ecs_brand) this.list.get(paramInt);
        ImageLoader.getInstance().displayImage(localEcs_brand.brand_logo, paramViewHolder.leftImageView);
        paramViewHolder.titleTextView.setText(localEcs_brand.brand_name);
        paramViewHolder.detailTextView.setText(localEcs_brand.brand_desc);
    }

    public SearchScenicAdapter.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        return new SearchScenicAdapter.ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.activity_scenic_search_item, paramViewGroup, false));
    }

    public void update(List<Ecs_brand> ecs_brands) {
        /*for (int i = 0; i < paramList.size(); i++) {
            Ecs_brand obj = paramList.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).brand_id == obj.brand_id) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(obj);
        }
        this.list = paramList;*/

        for (int i = 0; i < ecs_brands.size(); i++) {
            Ecs_brand obj = ecs_brands.get(i);
            boolean exist = false;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).brand_id == obj.brand_id) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(obj);
        }
        ecs_brands = list;

        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView detailTextView;
        ImageView leftImageView;
        ImageView rightImageView;
        TextView titleTextView;

        public ViewHolder(View paramView) {
            super(paramView);
            this.leftImageView = ((ImageView) paramView.findViewById(R.id.scenic_item_left));
            this.rightImageView = ((ImageView) paramView.findViewById(R.id.scenic_item_right));
            this.titleTextView = ((TextView) paramView.findViewById(R.id.scenic_search_title));
            this.detailTextView = ((TextView) paramView.findViewById(R.id.scenic_search_detail));
        }
    }

}
