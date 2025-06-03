// com/example/application_1/MainActivity5.java
package com.example.application_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity5 extends AppCompatActivity {
    private static final String TAG = "RateList";
    private static final String TARGET_URL = "https://www.huilvbiao.com/bank/spdb";
    private static final String TAG_DB = "DB_RATE";

    private ListView listView;
    private Button buttonBack;
    private RateAdapter adapter;
    private List<Rate> rates;

    private RateDao rateDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        rateDao = new RateDao(this);

        listView   = findViewById(R.id.listView_rates);
        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> finish());

        // 先从数据库加载
        rates = rateDao.getAllRates();
        adapter = new RateAdapter(this, rates);
        listView.setAdapter(adapter);

        logDatabaseContent();

        listView.setOnItemClickListener((p, v, pos, id) -> {
            Rate sel = rates.get(pos);
            Intent it = new Intent(MainActivity5.this, ConfigActivity.class);
            it.putExtra("currencyName", sel.getName());
            it.putExtra("currencyRate", sel.getRate());
            startActivity(it);
        });

        String lastDate = rateDao.getLastUpdateDate();
        String test = "2001-01-01";
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new java.util.Date());
        if (!today.equals(lastDate)) {
            fetchRatesFromWeb();
        }
    }

    private void fetchRatesFromWeb() {
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect(TARGET_URL)
                        .header("User-Agent", "Mozilla/5.0")
                        .get();

                Element tbody = doc.selectFirst("table.table-bordered tbody");
                if (tbody == null) throw new Exception("页面结构已变更");

                Elements rows = tbody.select("tr");
                Pattern p = Pattern.compile("^([\\d\\.]+)$");
                List<Rate> tmp = new ArrayList<>();

                for (Element tr : rows) {
                    String code   = tr.selectFirst("th.table-coin span").text();
                    String buyStr = tr.select("td").get(0).text();
                    Matcher m = p.matcher(buyStr);
                    double buy = m.find() ? Double.parseDouble(m.group(1)) / 100.0 : 0.0;
                    tmp.add(new Rate(code, code, buy));
                }

                // 保存到数据库，并更新 UI
                rateDao.saveRates(tmp);
                runOnUiThread(() -> {
                    rates.clear();
                    rates.addAll(tmp);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(this, "汇率已更新", Toast.LENGTH_SHORT).show();
                });

            } catch (Exception e) {
                Log.e(TAG, "抓取或解析出错", e);
                runOnUiThread(() ->
                        Toast.makeText(this, "获取汇率失败", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
    private void logDatabaseContent() {
        if (rates.isEmpty()) {
            Log.d(TAG_DB, "本地数据库中没有汇率数据");
            return;
        }
        for (Rate r : rates) {
            String msg = String.format(Locale.getDefault(),
                    "%s → %.4f", r.getName(), r.getRate());
            Log.d(TAG_DB, msg);
        }
    }
}
