package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;


public class LoginActivity extends BaseActivity {
    private static final String TAG="LoginActivity2";

    private TextView TextViewPassWord;
    private ImageView image_view_id1;
    private Button button_id1;
    private LinearLayout LinearLayout_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            CharSequence str = bundle.getCharSequence("RegisterActivity");
            if(str!=null){
                ((EditText)findViewById(R.id.editText2)).setText(str);
            }
        }

        TextViewPassWord = (TextView) findViewById(R.id.TextViewPassWord);
        TextViewPassWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, PhoneActivity.class);
                startActivity(intent);
            }
        });

//        image_view_id1 = (ImageView) findViewById(R.id.image_view_id1);
//        image_view_id1.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });

        LinearLayout_id = (LinearLayout) findViewById(R.id.LinearLayout_id);
        LinearLayout_id.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * �󶨿ؼ�id
     */
    protected  void findViewById(){

    };

    /**
     * ��ʼ���ؼ�
     */
    protected  void initView(){

    };
}
