package com.example.application_1;

import static android.content.Intent.getIntent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    private EditText etDollarRate, etEuroRate, etWonRate;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        etDollarRate = findViewById(R.id.etDollarRate);
        etEuroRate = findViewById(R.id.etEuroRate);
        etWonRate = findViewById(R.id.etWonRate);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate_key", 0.0f);
        float euro2   = intent.getFloatExtra("euro_rate_key",   0.0f);
        float won2    = intent.getFloatExtra("won_rate_key",    0.0f);

        etDollarRate.setText(String.valueOf(dollar2));
        etEuroRate.setText(String.valueOf(euro2));
        etWonRate.setText(String.valueOf(won2));

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                float newDollar = Float.parseFloat(etDollarRate.getText().toString().trim());
                float newEuro   = Float.parseFloat(etEuroRate.getText().toString().trim());
                float newWon    = Float.parseFloat(etWonRate.getText().toString().trim());

                Intent backIntent = new Intent();
                backIntent.putExtra("dollar_rate_key", newDollar);
                backIntent.putExtra("euro_rate_key", newEuro);
                backIntent.putExtra("won_rate_key", newWon);

                setResult(RESULT_OK, backIntent);
                finish();
            }
        });
    }
}
