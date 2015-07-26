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
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        findViewById();
        initView();

        img_member = (CircleImageView) findViewById(R.id.img_member);
        img_member.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void findViewById() {
        recyclerView = (RecyclerView) findViewById(R.id.list_recyclerView);
    }

    @Override
    protected void initView() {
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
//        Log.i(TAG,"names="+names);
//        userSetingAdapter.update(getResources().getStringArray(R.array.user_name_arrays), true);

    }


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
}
