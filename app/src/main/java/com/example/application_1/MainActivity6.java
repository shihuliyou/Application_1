package com.example.application_1;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity6 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        ListView listView = findViewById(R.id.listView);

        List<Map<String, String>> data = new ArrayList<>();
        addItem(data, "第一项", "冰淇淋");
        addItem(data, "第二项", "罗马");
        addItem(data, "第三项", "巴黎");
        addItem(data, "第四项", "法国");
        addItem(data, "第五项", "红酒");
        addItem(data, "第六项", "德国");
        addItem(data, "第七项", "大不列颠及北爱尔兰联合王国");
        addItem(data, "第八项", "的");
        addItem(data, "第九项", "炸鱼与薯条");
        addItem(data, "第十项", "黑暗料理");
        addItem(data, "第十一项", "美利坚合众国的");
        addItem(data, "第十二项", "炸鸡与汉堡");
        addItem(data, "第十三项", "热量炸弹");

        String[] from = { "title", "subtitle" };
        int[] to   = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                from,
                to
        );
        listView.setAdapter(adapter);
    }

    private void addItem(List<Map<String, String>> data, String title, String subtitle) {
        Map<String, String> row = new HashMap<>();
        row.put("title", title);
        row.put("subtitle", subtitle);
        data.add(row);
    }
}
