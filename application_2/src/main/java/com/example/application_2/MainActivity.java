package com.example.application_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 分数
    private int scoreA = 0;
    private int scoreB = 0;

    // 控件
    private TextView tvScoreA;
    private TextView tvScoreB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        tvScoreA = findViewById(R.id.tv_score_a);
        tvScoreB = findViewById(R.id.tv_score_b);

        Button btnAddThreeA = findViewById(R.id.btn_add_three_a);
        Button btnAddTwoA   = findViewById(R.id.btn_add_two_a);
        Button btnAddOneA   = findViewById(R.id.btn_add_one_a);

        Button btnAddThreeB = findViewById(R.id.btn_add_three_b);
        Button btnAddTwoB   = findViewById(R.id.btn_add_two_b);
        Button btnAddOneB   = findViewById(R.id.btn_add_one_b);

        Button btnReset     = findViewById(R.id.btn_reset);

        // 为Team A的按钮设置点击监听
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

        // 为Team B的按钮设置点击监听
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

        // RESET按钮
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetScores();
            }
        });
    }

    // 加分方法
    private void addScoreA(int points) {
        scoreA += points;
        displayScoreA();
    }

    private void addScoreB(int points) {
        scoreB += points;
        displayScoreB();
    }

    // 重置分数
    private void resetScores() {
        scoreA = 0;
        scoreB = 0;
        displayScoreA();
        displayScoreB();
    }

    // 更新显示
    private void displayScoreA() {
        tvScoreA.setText(String.valueOf(scoreA));
    }

    private void displayScoreB() {
        tvScoreB.setText(String.valueOf(scoreB));
    }
}
