package com.example.application_1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.util.Log;  // ← 新增

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RateList extends AppCompatActivity {
    private static final String TAG = "RateList";  // ← 日志标签
    private static final String TARGET_URL = "https://www.huilvbiao.com/mid/";

    private ListView listView;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);

        listView   = findViewById(R.id.listView_rates);
        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> finish());

        fetchRatesFromWeb();
    }

    private void fetchRatesFromWeb() {
        new Thread(() -> {
            try {
                Log.d(TAG, "开始连接 " + TARGET_URL);
                Document doc = Jsoup.connect(TARGET_URL)
                        .header("User-Agent", "Mozilla/5.0")
                        .get();
                Log.d(TAG, "页面加载完成");

                Element lprDiv = doc.select("div.lpr").first();
                if (lprDiv == null) {
                    Log.e(TAG, "未找到 div.lpr 节点");
                    throw new Exception("未找到汇率节点");
                }

                Pattern pattern = Pattern.compile("^([1-9]\\d*|100)([^\\d]+)对([\\d\\.]+)人民币$");
                Elements ps = lprDiv.select("p");

                ArrayList<String> items = new ArrayList<>();
                for (Element p : ps) {
                    String text = p.text().trim();
                    Log.d(TAG,text);  // ← 输出原始文本

                    Matcher m = pattern.matcher(text);
                    if (m.find()) {
                        String prefix = m.group(1);
                        String name   = m.group(2).replace("对", "").trim();
                        double rate   = Double.parseDouble(m.group(3));
                        if ("100".equals(prefix)) rate /= 100.0;

                        String entry = String.format("%s : %.4f RMB/%s", name, rate, name);
                        Log.d(TAG,entry);  // ← 输出解析结果

                        items.add(entry);
                    }
                }

                if (items.isEmpty()) {
                    items.add("暂无汇率数据");
                    Log.w(TAG, "未解析到任何汇率");
                }

                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            RateList.this,
                            android.R.layout.simple_list_item_1,
                            items
                    );
                    listView.setAdapter(adapter);
                });
            } catch (Exception e) {
                Log.e(TAG, "抓取或解析出错", e);
                runOnUiThread(() -> {
                    Toast.makeText(RateList.this, "获取汇率失败", Toast.LENGTH_SHORT).show();
                    ArrayList<String> error = new ArrayList<>();
                    error.add("获取汇率失败");
                    listView.setAdapter(new ArrayAdapter<>(
                            RateList.this,
                            android.R.layout.simple_list_item_1,
                            error
                    ));
                });
            }
        }).start();
    }
}
