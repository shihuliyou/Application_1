package com.example.application_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class ConfigActivity extends AppCompatActivity {
    private String currencyName;
    private double currencyRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        currencyName = getIntent().getStringExtra("currencyName");
        currencyRate = getIntent().getDoubleExtra("currencyRate", 1.0);

        TextView tvTitle = findViewById(R.id.text_currency_title);
        tvTitle.setText(currencyName);

        EditText etRmb       = findViewById(R.id.edit_input_rmb);
        Button   btnCalc     = findViewById(R.id.button_calculate);
        TextView tvResult    = findViewById(R.id.text_result);

        btnCalc.setOnClickListener(v -> {
            String s = etRmb.getText().toString().trim();
            if (s.isEmpty()) {
                Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
                return;
            }
            double rmb = Double.parseDouble(s);
            double out = rmb  / currencyRate;
            tvResult.setText(String.format(
                    Locale.getDefault(),
                    "结果：%.2f %s", out, currencyName
            ));
        });
    }
}
