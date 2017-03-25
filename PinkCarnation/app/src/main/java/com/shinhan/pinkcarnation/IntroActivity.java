package com.shinhan.pinkcarnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class IntroActivity extends AppCompatActivity {

    SimpleStorage ss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }
}
