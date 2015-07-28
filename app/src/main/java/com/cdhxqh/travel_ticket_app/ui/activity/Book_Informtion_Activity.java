package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cdhxqh.travel_ticket_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/7/28.
 */
public class Book_Informtion_Activity extends BaseActivity{
    private ListView listView_book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_information);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        listView_book_id = (ListView) this.findViewById(R.id.listView_book_id);
    }

    @Override
    protected void initView() {
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String,Object>>();
        HashMap<String, Object> item  = new HashMap<String, Object>();
        HashMap<String, Object> items  = new HashMap<String, Object>();
        HashMap<String, Object> itemss  = new HashMap<String, Object>();
        item.put("tittle","开放时间");
        item.put("content","\u3000\u3000"+"沙坡头位于宁夏回族自治区中卫市，1984年建立，面积1.3万余公顷，主要保护对象为腾格里沙漠景观、自然沙尘植被及其野生动物。");

        data.add(item);

        items.put("tittle", "地理位置");
        items.put("content", "\u3000\u3000" + "沙坡头地区（37°32′N ,105°02′E）位于宁夏回族自治区中卫市，腾格里沙漠东南缘，濒临黄河，属草原化荒漠地带，气候干旱而多风；该地区格状沙丘群由西北向东南倾斜，呈阶梯状分布，以沙漠生态治理与旅游闻名于世。");

        data.add(items);

        itemss.put("tittle", "旅游特色");
        itemss.put("content", "\u3000\u3000" + "特色之二是沙山北面是浩瀚无垠的腾格里沙漠。而沙山南面则是一片郁郁葱葱的沙漠绿洲。游人既可以在这里观赏大沙漠的景色，眺望包兰铁路如一条绿龙伸向远方；又可以骑骆驼在沙漠上走走，照张相片，领略一下沙漠行旅的味道。\n" +
                "特色之三是乘古老的渡河工具羊皮筏，在滔滔黄河之中，渡向彼岸。 这种羊皮筏俗称“排子”，是将山羊割去头蹄，然后将囫囵脱下的羊皮， 扎口，用时以嘴吹气，使之鼓起，十几个“浑脱”制成的“排子”，一 个人就能扛起，非常轻便。游人坐在“排子”上，筏工用桨划筏前进， 非常有趣。\n" +
                "许多人在评价中国旅游区形象的说到，“看的多，玩的少；让人沉思的，让人心情愉快的少”，在宁夏这片被中国旅游界称之为“中国旅游最后的处女地”的土地上，当神秘的面纱被掀起时，一次“看的过瘾，玩的尽兴”现代时尚的沙漠旅游拉开了序幕。因为这里好看，2005年10月被最具权威的《中国地理杂志社》组织国家十几位院士和近百位专家组成的评审团评为“中国最美的五大沙漠”之一；因为这里好玩，在2004年10月被中国电视艺术家协会旅游电视委员会、全国电视旅游节目协作会、中央电视台评为“中国十大最好玩的地方”之一。");

        data.add(itemss);
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.booking_information_item,
                new String[]{"tittle", "content"}, new int[]{R.id.booking_tiitle_id, R.id.booking_content_id});

        //实现列表的显示
        listView_book_id.setAdapter(adapter);
    }
}
