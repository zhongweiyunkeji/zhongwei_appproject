package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.AttractionsListAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.BrandListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;

/**景点列表**
 *
 */

public class Attractions_List_Activty extends BaseActivity {

    private static final String TAG="Attractions_List_Activty";
    /**
     * 返回按钮*
     */
    ImageView backImage;

    /**
     * 标题*
     */
    TextView titleText;

    /**
     * 搜索*
     */
    ImageView searchImageView;


    /****/
    RecyclerView mRecyclerView;

    /**attractionsListAdapter**/
    AttractionsListAdapter attractionsListAdapter;

    ArrayList<Attractions> attractionses;
    /**景区名称**/
    String brandName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);
        getData();
        findViewById();
        initView();


    }


    /**获取数据**/
    private void getData() {
        brandName=getIntent().getExtras().getString("brandName");
        attractionses=getIntent().getParcelableArrayListExtra("attractionses");
        Log.i(TAG,"brandName="+brandName+",attractionses="+attractionses);
    }

    @Override
    protected void findViewById() {
        backImage = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.title_search_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_distance);

    }

    @Override
    protected void initView() {
        titleText.setText(brandName);
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new ItemDivider(this,
                ItemDivider.VERTICAL_LIST));


        attractionsListAdapter = new AttractionsListAdapter(this);

        mRecyclerView.setAdapter(attractionsListAdapter);
        attractionsListAdapter.update(attractionses, true);
    }


    /**
     * 添加商品测试数据*
     */
    private ArrayList<Ecs_brand> addGoods() {
        ArrayList<Ecs_brand> brand_List = new ArrayList<Ecs_brand>();

        for (int i = 0; i < 10; i++) {
            Ecs_brand ecs_brands = new Ecs_brand();
            ecs_brands.setBrand_name("沙坡头");
            ecs_brands.setBrand_desc("这里是一望无际.烟波浩瀚的大沙漠，而且脚下则是我们的母亲河黄河...");
            brand_List.add(ecs_brands);
        }

        return brand_List;
    }

    /**
     * 返回事件*
     */
    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scenic__tickets_, menu);
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


    
}
