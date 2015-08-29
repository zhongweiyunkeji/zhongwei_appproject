package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Contacters;
import com.cdhxqh.travel_ticket_app.ui.adapter.CommonContactAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/15.
 */
public class CommonContactActivity extends BaseActivity {
    /**
     *listView
     */
    RecyclerView list_product;

    /**
     * list
     */
    private RecyclerView alarm_contacts;

    /**
     * 全选
     */
    private CheckBox checkbox_all;

    /**
     * 适配器
     */
    private CommonContactAdapter commonContactAdapter;

    /**
     * 确认
     */
    private TextView edit;

    /**
     * 联系人信息
     */
    private static ArrayList<Contacters> contactsMessage = new ArrayList<Contacters>();
    static ArrayList<Contacters> contacts = new ArrayList<Contacters>();

    /**
     *添加联系人
     */
    private ImageView addContacts;

    private RelativeLayout select_p;

    private TextView putConnect;

    Contacters contacters = new Contacters();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_contact);
        findViewById();
        initView();
    }


    public void updateList(ArrayList<Contacters> contacterseList)  {
        this.contacts = contacterseList;
        commonContactAdapter.updata(contacts);
        commonContactAdapter.dataChanged();
    }

    public void setData(Contacters c) {
        contacts.add(0, c);
        commonContactAdapter.updata(contacts);
        commonContactAdapter.dataChanged();
    }
//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        contacts = (ArrayList<Contacters>) data.getSerializableExtra("contactList");
//        ArrayList<Contacters> contactersLists = new ArrayList<Contacters>();
//        for(int i = 0; i<contactersList.size(); i++) {
//            if(contactersList.get(i).isFlag() == true) {
//                Contacters context = new Contacters();
//                context = contactersList.get(i);
//                contactersLists.add(context);
//            }
//        }
//        super.onActivityResult(reqCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if(contactersLists.size() > 0) {
//                user_num.setText("已选择" + contactersLists.size() + "人");
//            }else {
//                user_num.setText("可选择多人");
//            }
//        }
//    }

    protected void findViewById() {
        /**
         * list相关id
         */
        alarm_contacts = (RecyclerView) findViewById(R.id.alarm_contacts);

    }

    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(CommonContactActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        alarm_contacts.addItemDecoration(new ItemDivider(CommonContactActivity.this, ItemDivider.VERTICAL_LIST));
        alarm_contacts.setLayoutManager(layoutManager);
        alarm_contacts.setItemAnimator(new DefaultItemAnimator());

        if (contacts.size() <= 0) {
            for (int i = 0; i < 5; i++) {
                Contacters c = new Contacters();
                c.setName("张思");
                c.setType("老师");
                c.setPhone("15467656784");
                c.setFlag(false);
                contacts.add(c);
            }
        }

        commonContactAdapter = new CommonContactAdapter(CommonContactActivity.this, contacts);

        alarm_contacts.setAdapter(commonContactAdapter);
    }
}
