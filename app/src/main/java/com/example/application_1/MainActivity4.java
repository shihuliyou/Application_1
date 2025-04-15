package com.example.application_1;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    private Button btnThreadTest;
    private Button btnHandlerThreadTest;
    private TextView tvResult;

    private HandlerThread mHandlerThread;
    private Handler mHandlerThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // 初始化控件
        btnThreadTest = findViewById(R.id.btnThreadTest);
        btnHandlerThreadTest = findViewById(R.id.btnHandlerThreadTest);
        tvResult = findViewById(R.id.tvResult);
        mHandlerThread = new HandlerThread("TestHandlerThread");
        mHandlerThread.start();
        // 通过 HandlerThread 的 Looper 创建 Handler
        mHandlerThreadHandler = new Handler(mHandlerThread.getLooper());

        btnHandlerThreadTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandlerThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvResult.setText("HandlerThread 测试完成！");
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
    }
}
