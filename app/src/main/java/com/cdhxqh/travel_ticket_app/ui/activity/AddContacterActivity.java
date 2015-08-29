package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Contacters;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/15.
 */
public class AddContacterActivity extends BaseActivity {
    /**
     * 填写姓名
     */
    TextView name;

    /**
     * 填写手机号
     */
    TextView phone;

    /**
     * 提交
     */
    Button commit_id;

    /**
     * 对象
     */
    Contacters contacts;

    TextView role_group;

    /**
     * 编辑
     */
    String judje;

    CommonContactActivity commonContactFragment;

    private static final int PICK_CONTACT = 1;

    public static final int ACTIVITY_REGISTRY_RESPONSE1 = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacter);
        findViewById();
        getData();
        initView();
    }

    protected void findViewById() {
        /**
         * name
         */
        name = (TextView) findViewById(R.id.name);

        /**
         * phone
         */
        phone = (TextView) findViewById(R.id.phone);

        /**
         * 添加联系人
         */
        commit_id = (Button) findViewById(R.id.commit_id);
        /**
         * 分组显示
         */
        role_group = (TextView) findViewById(R.id.role_group);
    }


    protected void initView() {
        if (contacts != null) {
            name.setText(contacts.getName());
            phone.setText(contacts.getPhone());
            role_group.setText(contacts.getType());
        }
        role_group.setOnClickListener(selectGroupOnClickListener);
        commit_id.setOnClickListener(commitOnClickListener);
    }

    /**
     * 分组
     */
    final Activity activity = this;
    private View.OnClickListener selectGroupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(activity, Activity_User_Type.class);
//            startActivityForResult(intent, PICK_CONTACT);
        }
    };

    /**
     * 提交
     */
    private View.OnClickListener commitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Contacters contacters = new Contacters();
            if (name.getText() != null && !name.getText().toString().trim().equals("") && phone.getText() != null && !phone.getText().toString().trim().equals("")) {
                if (role_group.getText() != null) {
                    contacters.setName(name.getText().toString());
                    contacters.setPhone(phone.getText().toString());
                    contacters.setType(role_group.getText().toString());
                }
                if (judje != null) {
                    if (!judje.equals("edit")) {
                        Intent intent = new Intent();
                        intent.putExtra("contactList", (Serializable) contacters);
                        setResult(RESULT_OK, intent);
                        finish();
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("contactList", (Serializable) contacters);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            } else {
                if (name.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(AddContacterActivity.this).setTitle("标题").setMessage("请填写姓名").setPositiveButton("确定", null).show();
                } else if (phone.getText().toString().trim().equals("")) {
                    new AlertDialog.Builder(AddContacterActivity.this).setTitle("标题").setMessage("请填写手机号").setPositiveButton("确定", null).show();
                }
            }

        }
    };

    /**
     * 获取数据
     */
    private void getData() {
        contacts = (Contacters) getIntent().getSerializableExtra("contactList");
        judje = getIntent().getStringExtra("edit");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if ((OnItemClickCallBackImpl.ACTIVITY_REGISTRY_RESPONSE1 == resultCode) && (Activity_Registry_User.ACTIVITY_REGISTRY_REQUEST1 == requestCode)) {
//            String text = role_group.getText().toString();
//            if(data!=null){
//                Bundle bundle = data.getExtras();
//                text = bundle.getString("text");
//            }
//                role_group.setText(text);
//
//
//            Log.i("", "----------------------->" + text);
//        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (contacts != null) {
                Intent intent = new Intent();
                intent.putExtra("contactList", (Serializable) contacts);
                setResult(RESULT_OK, intent);
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
