package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Intro21Activity extends AppCompatActivity {
    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    String deviceId = null;
    String accessCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro21);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }

    public void btnNextClicked(View view) {

        // 데이타 유효성 검사
        EditText editId = (EditText)findViewById(R.id.editId);
        EditText editPw = (EditText)findViewById(R.id.editPw);

        deviceId = editId.getText().toString();
        accessCode = editPw.getText().toString();

        if (deviceId == null || deviceId.isEmpty()) {
            Toast.makeText(this, "오류! 기기ID를 할당받아야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (accessCode == null || accessCode.isEmpty()) {
            Toast.makeText(this, "오류! 제어코드를 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 데이타 저장
        ss.put("Main", "Role", "Parent");
        ss.put("Main", "DeviceID", deviceId);
        ss.put("Main", "AccessCode", accessCode);
        ss.put("Main", "IntroDone", "Yes");

        // 다음 화면으로 이동
        Intent intent = new Intent(this, Intro30Activity.class);
        startActivity(intent);
    }
}
