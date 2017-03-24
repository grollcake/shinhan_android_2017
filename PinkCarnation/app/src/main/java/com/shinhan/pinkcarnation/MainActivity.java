package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doMain();
    }

    void doMain(){
        // 2초간 멈춘 후에 다음 페이지로 넘어간다.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(MainActivity.this, Intro10Activity.class);
                startActivity(intent);
            }
        }, 2000);
    }

}
