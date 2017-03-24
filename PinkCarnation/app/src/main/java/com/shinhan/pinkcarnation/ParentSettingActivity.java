package com.shinhan.pinkcarnation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ParentSettingActivity extends AppCompatActivity {

    final String [] TR_MODE = {"5분", "10분", "30분", "1시간"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_setting);
    }
}
