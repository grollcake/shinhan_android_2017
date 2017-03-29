package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shinhan.pinkcarnation.PinkcarSerivce.PinkcarApp;

public class ChildMainActivity extends AppCompatActivity {

    PinkcarApp APP = null;

    // 화면UI
    TextView txtTargetId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chlid_main);

        APP = (PinkcarApp) getApplication();

        // 화면UI 요소 전역변수 할당
        txtTargetId = (TextView) findViewById(R.id.txtTargetId);
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(this, ChildSettingActivity.class);
        startActivity(intent);
    }
}
