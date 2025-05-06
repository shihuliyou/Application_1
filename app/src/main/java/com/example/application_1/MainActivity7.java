package com.example.application_1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity7 extends AppCompatActivity {
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        items = new ArrayList<>(Arrays.asList(
                "苹果","橘子","柠檬","橙子","草莓","西瓜",
                "哈密瓜","第八项","第九项","第十项","第十一项","第十二项"
        ));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );

        GridView grid = findViewById(R.id.gridView);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position, long id) {
                Toast.makeText(
                        MainActivity7.this,
                        "点击：" + items.get(position),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           int position,
                                           long id) {
                String removed = items.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(
                        MainActivity7.this,
                        "已删除：" + removed,
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });
    }
}
