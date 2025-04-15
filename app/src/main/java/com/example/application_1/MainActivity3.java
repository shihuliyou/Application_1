package com.example.application_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity3 extends AppCompatActivity {
    private EditText editTextAmount;
    private Spinner spinnerCurrency;
    private TextView textViewResult;
    private Button buttonConvert;
    private HashMap<String, Double> exchangeRates = new HashMap<>();
    private String[] supportedCurrencies = {"美元", "欧元", "日元", "港币", "英镑", "澳大利亚元", "新西兰元", "新加坡元"};

    private static final String TARGET_URL = "https://www.huilvbiao.com/mid/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 绑定UI组件
        editTextAmount = findViewById(R.id.editText_amount);
        spinnerCurrency = findViewById(R.id.spinner_currency);
        textViewResult = findViewById(R.id.textView_result);
        buttonConvert = findViewById(R.id.button_convert);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, supportedCurrencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);

        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String inputStr = editTextAmount.getText().toString().trim();
                if(inputStr.isEmpty()){
                    textViewResult.setText("请输入金额");
                    return;
                }

                final double rmbAmount;
                try {
                    rmbAmount = Double.parseDouble(inputStr);
                } catch (NumberFormatException e) {
                    textViewResult.setText("金额格式不正确");
                    return;
                }

                final String targetCurrency = spinnerCurrency.getSelectedItem().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc = Jsoup.connect(TARGET_URL)
                                    .header("User-Agent", "Mozilla/5.0")
                                    .get();
                            Element lprDiv = doc.select("div.lpr").first();
                            if(lprDiv == null){
                                throw new Exception("未找到包含汇率数据的区域");
                            }
                            exchangeRates.clear();
                            Pattern pattern = Pattern.compile("^([1-9]\\d*|100)([^\\d]+)对([\\d\\.]+)人民币$");
                            Elements ps = lprDiv.select("p");
                            for (Element p : ps) {
                                String text = p.text().trim();
                                Matcher m = pattern.matcher(text);
                                if(m.find()){
                                    String prefix = m.group(1);
                                    String currencyName = m.group(2);
                                    currencyName = currencyName.replace("对", "").trim();
                                    String rateValueStr = m.group(3);
                                    double rateValue = Double.parseDouble(rateValueStr);
                                    double unitRate = rateValue;
                                    if(prefix.equals("100")){
                                        unitRate = rateValue / 100.0;
                                    }
                                    exchangeRates.put(currencyName, unitRate);
                                }
                            }
                            if(!exchangeRates.containsKey(targetCurrency)) {
                                runOnUiThread(() ->
                                        textViewResult.setText("未找到" + targetCurrency + "汇率数据")
                                );
                                return;
                            }
                            final double targetRate = exchangeRates.get(targetCurrency);
                            final double convertedAmount = rmbAmount / targetRate;
                            runOnUiThread(() -> {
                                String result = String.format("%.2f RMB = %.2f %s", rmbAmount, convertedAmount, targetCurrency);
                                textViewResult.setText(result);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(() ->
                                    textViewResult.setText("获取数据出错")
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() ->
                                    textViewResult.setText(e.getMessage())
                            );
                        }
                    }
                }).start();
            }
        });
    }
}
