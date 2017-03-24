package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Intro10Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro10);
    }

    public void btnChildClicked(View view) {
        Intent intent = new Intent(Intro10Activity.this, Intro22Activity.class);
        startActivity(intent);
    }

    public void btnParentClicked(View view) {
        Intent intent = new Intent(Intro10Activity.this, Intro21Activity.class);
        startActivity(intent);
    }
}
