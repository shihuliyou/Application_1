package com.example.application_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextWeight, editTextHeight;
    private TextView textViewResult, textViewSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        textViewSuggestion = findViewById(R.id.textViewSuggestion);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String weightInput = editTextWeight.getText().toString();
                String heightInput = editTextHeight.getText().toString();

                if (weightInput.isEmpty() || heightInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入体重和身高", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double weight = Double.parseDouble(weightInput);
                    double height = Double.parseDouble(heightInput);

                    double bmi = weight / (height * height);
                    @SuppressLint("DefaultLocale") String resultText = String.format("BMI 结果：%.2f", bmi);
                    textViewResult.setText(resultText);

                    String suggestion;
                    if (bmi < 18.5) {
                        suggestion = "偏瘦：建议适当增加营养";
                    } else if (bmi >= 18.5 && bmi < 24.9) {
                        suggestion = "正常：建议继续保持";
                    } else if (bmi >= 25 && bmi < 29.9) {
                        suggestion = "超重：建议适量运动";
                    } else {
                        suggestion = "肥胖：建议积极锻炼，控制饮食";
                    }
                    textViewSuggestion.setText("健康建议：" + suggestion);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}