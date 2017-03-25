package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    SimpleStorage ss = null;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);

        doMain();
    }

    void doMain(){
        // 인트로 완료 여부에 따라 다음 화면을 결정한다.
        String isDone = ss.get("Main", "IntroDone", "");
        if (isDone.equals("Yes")) {
            String role = ss.get("Main", "Role", "");
            if (role.equals("Parent")) {
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
                startActivity(intent);
            }
        }, 2000);
    }

}
