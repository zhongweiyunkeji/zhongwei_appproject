package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Contacters;
import com.cdhxqh.travel_ticket_app.ui.activity.AddContacterActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/15.
 */
public class CommonContactAdapter extends RecyclerView.Adapter<CommonContactAdapter.ViewHolder> {
    Context mContext;
    ArrayList<Contacters> contacts;

    private static final int EDIT_CONTACT = 3;

    public CommonContactAdapter(Context paramContext, ArrayList<Contacters> contacts) {
        this.mContext = paramContext;
        this.contacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.avtivity_common_contact_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.contacts_name.setText(contacts.get(i).getName() + "\u3000\u3000\u3000");
        holder.contacts_group.setText(contacts.get(i).getType());
        holder.contacts_phone.setText("手机号:" + contacts.get(i).getPhone());
        final int item = i;
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, AddContacterActivity.class);
                intent.putExtra("contactList", (Serializable) contacts.get(item));
                intent.putExtra("edit", "edit");
                contacts.remove(contacts.get(item));
                ((Activity) mContext).startActivityForResult(intent, EDIT_CONTACT);

            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 联系人姓名
         */
        private TextView contacts_name;
        /**
         * 类别
         */
        private TextView contacts_group;
        /**
         * 联系人电话
         */
        private TextView contacts_phone;

        /**
         *edit
         */
        private ImageView edit;

        public ViewHolder(View view) {
            super(view);
            contacts_name = (TextView) view.findViewById(R.id.contacts_name);
            contacts_phone = (TextView) view.findViewById(R.id.contacts_phone);
            contacts_group = (TextView) view.findViewById(R.id.contacts_group);
            edit = (ImageView) view.findViewById(R.id.edit);
        }
    }

    public void updata(ArrayList<Contacters> contacters) {
         this.contacts = contacters;
    }

    // 刷新list
    public void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    public ArrayList<Contacters> getContacts() {
        return contacts;
    }
}
