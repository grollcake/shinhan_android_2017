package com.shinhan.pinkcarnation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shinhan.pinkcarnation.PinkcarSerivce.HttpCallBack;
import com.shinhan.pinkcarnation.PinkcarSerivce.HttpService;
import com.shinhan.pinkcarnation.PinkcarSerivce.PinkcarApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Intro21Activity extends AppCompatActivity {

    PinkcarApp APP = null;

    // 화면 UI 요소들
    LinearLayout grpDeviceId = null;
    TextView txtStatus = null;
    TextView txtDeviceId = null;
    EditText edtDevicePw = null;
    Button btnRegister = null;
    Button btnNext = null;
    ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro21);

        APP = (PinkcarApp) getApplication();

        // 화면 UI 요소들 변수에 할당
        grpDeviceId = (LinearLayout) findViewById(R.id.grpDeviceId);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        txtDeviceId = (TextView)findViewById(R.id.txtDeviceId);
        edtDevicePw = (EditText)findViewById(R.id.edtDevicePw);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnNext = (Button)findViewById(R.id.btnNext);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // 화면 UI 요소 초기상태 세팅
        grpDeviceId.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        txtStatus.setText("비밀번호를 생성해야 합니다\n숫자 6자리로 입력하세요");

        // 시작
        doMain();
    }

    private void doMain() {

    }

    public void btnNextClicked(View view) {
        Intent intent = new Intent(this, Intro30Activity.class);
        startActivity(intent);
    }

    public void btnCopyClicked(View view) {
        String deviceId = txtDeviceId.getText().toString();
        if (deviceId != null && !deviceId.equals("")) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Pinkcar", deviceId);
            clipboard.setPrimaryClip(clip);
            APP.toast("기기ID를 복사했습니다.\n카톡 등으로 자녀에게 전달해주세요");
        } else {
            APP.toast("복사할 기기ID 값이 없습니다");
        }
    }

    public void btnRegisterClicked(View view) {
        if (edtDevicePw.getText().toString().length() != 6 ) {
            APP.toast("비밀번호를 6자리로 입력하세요");
            return;
        }

        deviceRegister();
    }

    private void deviceRegister () {
        // 신규 기기의 패스워드를 등록하고 기기ID를 받아온다.
        JSONObject biz = new JSONObject();
        try {
            biz.put("devicepw", edtDevicePw.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            APP.toast("오류! " + e.getMessage());
            return;
        }

        APP.HttpService("device_register", biz, new HttpCallBack() {
            @Override
            public void callback(Integer resCode, String resMessage, String error) {
                if (error != null) {
                    APP.toast("서버오류! " + error);
                    return;
                }

                try {
                    JSONObject jsonRes = new JSONObject(resMessage);
                    String result = jsonRes.getString("result");
                    if (result.equals("OK")) {
                        JSONObject jsonData = jsonRes.getJSONObject("data");
                        String deviceId = jsonData.getString("deviceid");
                        String devicePw = jsonData.getString("devicepw");

                        // 앱환경으로 저장
                        APP.put("Main", "Role", "Parent");
                        APP.put("Main", "DevicePW", deviceId);
                        APP.put("Main", "DevicePW", devicePw);

                        // 기기ID와 다음 버튼 화면에 표시
                        txtDeviceId.setText(APP.DeviceID);
                        grpDeviceId.setVisibility(View.VISIBLE);
                        btnNext.setVisibility(View.VISIBLE);
                        edtDevicePw.setEnabled(false);
                        btnRegister.setVisibility(View.INVISIBLE);
                        txtStatus.setText("기기를 등록했습니다\n발급된 기기ID를 복사하여 전달하세요");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
