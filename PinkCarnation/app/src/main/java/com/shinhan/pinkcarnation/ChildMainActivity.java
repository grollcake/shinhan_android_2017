package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChildMainActivity extends AppCompatActivity {

    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    // 화면UI
    TextView txtTargetId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chlid_main);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);

        // 화면UI 요소 전역변수 할당
        txtTargetId = (TextView) findViewById(R.id.txtTargetId);

        // 화면UI 요소 초기값 할당
        txtTargetId.setText(ss.get("Main", "TargetID", ""));
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(this, ChildSettingActivity.class);
        startActivity(intent);
    }
}
