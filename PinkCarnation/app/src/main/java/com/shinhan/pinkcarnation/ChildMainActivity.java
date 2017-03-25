package com.shinhan.pinkcarnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChildMainActivity extends AppCompatActivity {

    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chlid_main);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }
}
