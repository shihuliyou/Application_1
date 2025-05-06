package com.example.application_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity5 extends AppCompatActivity {
    private static final String TAG = "RateList";
    private static final String TARGET_URL = "https://www.huilvbiao.com/bank/spdb";

    private ListView listView;
    private Button buttonBack;
    private RateAdapter adapter;
    private final List<Rate> rates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        listView   = findViewById(R.id.listView_rates);
        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> finish());

        adapter = new RateAdapter(this, rates);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Rate sel = rates.get(position);
            Intent it = new Intent(MainActivity5.this, ConfigActivity.class);
            it.putExtra("currencyName", sel.getName());
            it.putExtra("currencyRate", sel.getRate());
            startActivity(it);
        });

        fetchRatesFromWeb();
    }

    private void fetchRatesFromWeb() {
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect(TARGET_URL)
                        .header("User-Agent", "Mozilla/5.0")
                        .get();

                Element tbody = doc.selectFirst("table.table-bordered tbody");
                if (tbody == null) throw new Exception("已经改变");

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

                runOnUiThread(() -> {
                    if (tmp.isEmpty()) {
                        Toast.makeText(this, "暂无汇率数据", Toast.LENGTH_SHORT).show();
                    } else {
                        rates.clear();
                        rates.addAll(tmp);
                        adapter.notifyDataSetChanged();
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "出错", e);
                runOnUiThread(() ->
                        Toast.makeText(this, "获取汇率失败", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    private static class RateAdapter extends ArrayAdapter<Rate> {
        private final LayoutInflater inflater;

        RateAdapter(Context ctx, List<Rate> list) {
            super(ctx, 0, list);
            inflater = LayoutInflater.from(ctx);
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_rate, parent, false);
            }
            Rate rate = getItem(pos);
            TextView tvName = convertView.findViewById(R.id.text_currency_name);
            TextView tvRate = convertView.findViewById(R.id.text_currency_rate);

            tvName.setText(rate.getName());
            tvRate.setText(String.format(Locale.getDefault(), "%.4f", rate.getRate()));
            return convertView;
        }
    }

    private static class Rate {
        private final String code, name;
        private final double rate;
        Rate(String code, String name, double rate) {
            this.code = code; this.name = name; this.rate = rate;
        }
        String getCode() { return code; }
        String getName() { return name; }
        double getRate() { return rate; }
    }
}
