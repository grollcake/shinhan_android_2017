package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Intro30Activity extends AppCompatActivity {
    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro30);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }

    public void btnNextClicked(View view) {
        String role = ss.get("Main", "Role", "");

        Intent intent = null;

        if (role.equals("Parent")) {
            intent = new Intent(this, ParentMainActivity.class);
        } else {
            intent = new Intent(this, ChildMainActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // call this to finish the current activity
    }
}
