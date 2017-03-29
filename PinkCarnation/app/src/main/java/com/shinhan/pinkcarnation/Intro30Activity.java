package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shinhan.pinkcarnation.PinkcarSerivce.PinkcarApp;

public class Intro30Activity extends AppCompatActivity {
    PinkcarApp APP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro30);
        APP = (PinkcarApp) getApplication();
    }

    public void btnNextClicked(View view) {

        APP.put("Main", "IntroDone", "Yes");

        Intent intent = null;

        if (APP.Role.equals("Parent")) {
            intent = new Intent(this, ParentMainActivity.class);
        } else {
            intent = new Intent(this, ChildMainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // call this to finish the current activity
    }
}
