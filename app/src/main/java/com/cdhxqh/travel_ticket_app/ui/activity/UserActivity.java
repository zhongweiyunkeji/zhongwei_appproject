package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.ui.adapter.UserSetingAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的用户界面*
 */

public class UserActivity extends BaseActivity {

    private static final String TAG = "UserActivity";
    private CircleImageView img_member;

    /**
     * 显示following的recyclerView*
     */
    RecyclerView recyclerView;
    /**
     * UserSetingAdapter*
     */
    UserSetingAdapter userSetingAdapter;

    String[] names;

    /**
     * 待出游、待点评
     */
    private TextView user_not_pay_id,user_not_travel_id,user_not_review_id;


    /**
     * 请登录*
     */
    TextView txt_member;

    /**切换账号**/
    TextView switch_account_text;

    /**
     *用户
     */
    LinearLayout normalUser;

    /**
     *验票员
     */
    LinearLayout ticketor;

    /**
     * 验票
     */
    ImageView ticket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.list_recyclerView);

        img_member = (CircleImageView) findViewById(R.id.img_member);
        txt_member = (TextView) findViewById(R.id.txt_member);
        switch_account_text = (TextView) findViewById(R.id.switch_account_id);

        user_not_pay_id = (TextView)findViewById(R.id.user_not_pay_id);
        user_not_travel_id = (TextView) findViewById(R.id.user_not_travel_id);
        user_not_review_id = (TextView) findViewById(R.id.user_not_review_id);

        normalUser = (LinearLayout) findViewById(R.id.normalUser);
        ticketor = (LinearLayout) findViewById(R.id.ticketor);

        ticket = (ImageView) findViewById(R.id.ticket);
    }

    @Override
    protected void initView() {
        if(userType = true) {
            normalUser.setVisibility(View.VISIBLE);
            ticketor.setVisibility(View.GONE);
        } else {
            normalUser.setVisibility(View.GONE);
            ticketor.setVisibility(View.VISIBLE);
        }
        user_not_pay_id.setOnClickListener(user_not_payOnClickListener);
        user_not_travel_id.setOnClickListener(user_not_travelOnClickListener);
        user_not_review_id.setOnClickListener(user_not_reviewOnClickListener);

        ticket.setOnClickListener(ticketOnClickListener);

        Log.i(TAG, "mIsLogin=" + mIsLogin);
        if (mIsLogin) {
            txt_member.setText(ec_user.userName);
            switch_account_text.setVisibility(View.VISIBLE);
            switch_account_text.setOnClickListener(img_memberOnClickListener);

        } else {
            switch_account_text.setVisibility(View.GONE);
            txt_member.setOnClickListener(img_memberOnClickListener);
            img_member.setOnClickListener(img_memberOnClickListener);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(UserActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new ItemDivider(this,
                ItemDivider.VERTICAL_LIST));

        names = getResources().getStringArray(R.array.user_name_arrays);

        userSetingAdapter = new UserSetingAdapter(UserActivity.this, addStrings(names));
        recyclerView.setAdapter(userSetingAdapter);

    }
    private View.OnClickListener user_not_payOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(UserActivity.this,OrderActivity.class);
            intent.putExtra("unPayMent", true);
            UserActivity.this.startActivity(intent);
        }
    };

    private View.OnClickListener user_not_travelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(UserActivity.this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("activity", "ORDER_ACTIVITY");

            intent.putExtras(bundle);
            UserActivity.this.startActivity(intent);
            UserActivity.this.finish();
        }
    };

    private View.OnClickListener user_not_reviewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(UserActivity.this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("activity", "ORDER_ACTIVITY");

            intent.putExtras(bundle);
            UserActivity.this.startActivity(intent);
            UserActivity.this.finish();
        }
    };

    private View.OnClickListener img_memberOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(UserActivity.this, LoginActivity.class);
            startActivityForResult(intent, 0);
        }
    };


    private ArrayList<String> addStrings(String[] names) {
        ArrayList<String> strings = new ArrayList<String>();
        if (names != null || names.length != 0) {
            for (int i = 0; i < names.length; i++) {
                strings.add(names[i]);
            }
        }
        return strings;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    private View.OnClickListener ticketOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserActivity.this, TicketCheckActivity.class);
            UserActivity.this.startActivity(intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            exit(UserActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case Constants.STATUS_CODE_1000:
                if(mIsLogin) {
                    txt_member.setText(ec_user.userName);
                }
                break;
        }
    }
}
