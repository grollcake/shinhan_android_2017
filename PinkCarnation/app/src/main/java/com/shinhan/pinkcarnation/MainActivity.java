package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shinhan.pinkcarnation.PinkcarSerivce.PinkcarApp;
import com.shinhan.pinkcarnation.PinkcarSerivce.HttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    PinkcarApp APP = null;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APP = (PinkcarApp) getApplication();
        doMain();
    }

    void doMain(){
        // 인트로 완료 여부에 따라 다음 화면을 결정한다.
        if (APP.IntroDone.equals("Yes")) {
            if (APP.Role.equals("Parent")) {
                intent = new Intent(MainActivity.this, ParentMainActivity.class);
            } else {
                intent = new Intent(MainActivity.this, ChildMainActivity.class);
            }
        } else {
            intent = new Intent(MainActivity.this, Intro10Activity.class);
        }

        // 2초간 멈춘 후에 다음 페이지로 넘어간다.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // call this to finish the current activity
            }
        }, 2000);
    }
}
