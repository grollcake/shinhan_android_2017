package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Intro30Activity extends AppCompatActivity {
    SimpleStorage ss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ss = new SimpleStorage(this);
        setContentView(R.layout.activity_intro30);
    }

    public void btnNextClicked(View view) {
        String role = ss.get("Main", "Role", "");

        Intent intent = null;

        if (role.equals("Parent")) {
            intent = new Intent(this, ParentMainActivity.class);
        } else {
            intent = new Intent(this, ChildMainActivity.class);
        }

        startActivity(intent);
    }
}
