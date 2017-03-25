package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Intro22Activity extends AppCompatActivity {
    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    String deviceId = null;
    String targetId = null;
    String accessCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro22);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }

    public void btnNextClicked(View view) {
        // 데이타 유효성 검사
        EditText editId = (EditText)findViewById(R.id.editId);
        EditText editPw = (EditText)findViewById(R.id.editPw);

        targetId = editId.getText().toString();
        accessCode = editPw.getText().toString();

        if (targetId == null || targetId.isEmpty()) {
            Toast.makeText(this, "오류! 기기ID가 공백입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (accessCode == null || accessCode.isEmpty()) {
            Toast.makeText(this, "오류! 제어코드가 공백입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 데이타 저장
        ss.put("Main", "Role", "Child");
        ss.put("Main", "DeviceID", deviceId);
        ss.put("Main", "TargetID", targetId);
        ss.put("Main", "AccessCode", accessCode);
        ss.put("Main", "IntroDone", "Yes");

        // 페이지 이동
        Intent intent = new Intent(this, Intro30Activity.class);
        startActivity(intent);
    }
}
