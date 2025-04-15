package com.example.application_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private int scoreA = 0;
    private int scoreB = 0;
    private TextView tvScoreA;
    private TextView tvScoreB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvScoreA = findViewById(R.id.tv_score_a);
        tvScoreB = findViewById(R.id.tv_score_b);

        Button btnAddThreeA = findViewById(R.id.btn_add_three_a);
        Button btnAddTwoA   = findViewById(R.id.btn_add_two_a);
        Button btnAddOneA   = findViewById(R.id.btn_add_one_a);

        Button btnAddThreeB = findViewById(R.id.btn_add_three_b);
        Button btnAddTwoB   = findViewById(R.id.btn_add_two_b);
        Button btnAddOneB   = findViewById(R.id.btn_add_one_b);

        Button btnReset     = findViewById(R.id.btn_reset);

        btnAddThreeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreA(3);
            }
        });
        btnAddTwoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreA(2);
            }
        });
        btnAddOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreA(1);
            }
        });

        btnAddThreeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreB(3);
            }
        });
        btnAddTwoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreB(2);
            }
        });
        btnAddOneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addScoreB(1);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetScores();
            }
        });
    }

    private void addScoreA(int points) {
        scoreA += points;
        displayScoreA();
    }

    private void addScoreB(int points) {
        scoreB += points;
        displayScoreB();
    }

    private void resetScores() {
        scoreA = 0;
        scoreB = 0;
        displayScoreA();
        displayScoreB();
    }

    private void displayScoreA() {
        tvScoreA.setText(String.valueOf(scoreA));
    }

    private void displayScoreB() {
        tvScoreB.setText(String.valueOf(scoreB));
    }
}
