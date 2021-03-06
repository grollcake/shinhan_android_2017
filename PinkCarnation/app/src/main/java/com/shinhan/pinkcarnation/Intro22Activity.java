package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shinhan.pinkcarnation.PinkcarSerivce.PinkcarApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Intro22Activity extends AppCompatActivity {
    PinkcarApp APP = null;

    // 화면 UI 객체 선언
    EditText editAccessCode = null;
    EditText editTargetId = null;
    TextView txtStatus = null;
    ProgressBar progressBar = null;

    String deviceId = null;
    String targetId = null;
    String accessCode = null;
    Boolean linkOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro22);
        APP = (PinkcarApp) getApplication();

        // 화면 UI 객체 변수할당
        editTargetId = (EditText) findViewById(R.id.editTargetId);
        editAccessCode = (EditText) findViewById(R.id.editAccessCode);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // 기기ID 할당 요청
        registerDevice("REQUEST");
        txtStatus.setText("기기ID를 할당받는 중입니다..");
    }

    public void btnNextClicked(View view) {
        // 데이타 유효성 검사
        targetId = editTargetId.getText().toString();
        accessCode = editAccessCode.getText().toString();

        if (!linkOk) {
            Toast.makeText(this, "오류! 기기 등록 후 진행할 수 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 데이타 저장
        APP.put("Main", "Role", "Child");
        APP.put("Main", "DeviceID", this.deviceId);
        APP.put("Main", "TargetID", this.targetId);
        APP.put("Main", "AccessCode", this.accessCode);
        APP.put("Main", "IntroDone", "Yes");

        // 페이지 이동
        Intent intent = new Intent(this, Intro30Activity.class);
        startActivity(intent);
    }

    public void btnVerifyClicked(View view) {
        if (this.deviceId == null || this.deviceId.isEmpty()) {
            Toast.makeText(this, "오류! 기기ID가 할당되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editAccessCode.getText().toString() == null ||
                editAccessCode.getText().toString().length() != 6) {
            Toast.makeText(this, "오류! 제어코드는 6자리로 입력해야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editTargetId.getText().toString() == null || editTargetId.getText().toString().equals("")) {
            Toast.makeText(this, "오류! 기기ID가 할당되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( this.accessCode == null ) {
            registerDeviceAccessCode("REQUEST");
            txtStatus.setText("기기연결을 시도합니다..");
        } else {
            getDeviceStatus("REQUEST");
            txtStatus.setText("기기연결을 시도합니다..");
        }
    }

    private void registerDevice(String s) {
        if (s.equals("REQUEST")) {
            JSONObject param = new JSONObject();
            JSONObject com = new JSONObject();
            try {
                com.put("deviceid", "NEW");
                com.put("targetid", "NEW");
                com.put("accesscode", "");
                param.put("cmd", "device_register");
                param.put("com", com);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String paramStr = param.toString();
            Log.d("param", paramStr);

            new ServiceCall("1").execute("http://ifwind.cf:50000/pinkcar", paramStr);
        }

        else {
            boolean isOk = false;
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
                JSONObject data = jObject.getJSONObject("data");
                String deviceid = data.getString("deviceid");
                this.deviceId = deviceid;
                isOk = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!isOk) {
                txtStatus.setText("오류! 기기ID를 할당받지 못했습니다");
                Toast.makeText(this, "오류! 기기ID를 할당받지 못했습니다.", Toast.LENGTH_SHORT).show();
            } else {
                txtStatus.setText("기기ID를 할당받았습니다.\n" + this.deviceId + "\n연결할 기기ID를 입력하세요");
            }
        }
    }

    private void registerDeviceAccessCode(String s) {
        if (s.equals("REQUEST")) {
            accessCode = editAccessCode.getText().toString();

            JSONObject param = new JSONObject();
            JSONObject com = new JSONObject();
            JSONObject biz = new JSONObject();
            try {
                param.put("cmd", "device_register");
                com.put("deviceid", this.deviceId);
                com.put("targetid", this.deviceId);
                com.put("accesscode", "");
                param.put("com", com);
                biz.put("accesscode", accessCode);
                param.put("biz", biz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String paramStr = param.toString();
            Log.d("param", paramStr);

            new ServiceCall("2").execute("http://ifwind.cf:50000/pinkcar", paramStr);
        }

        else {
            boolean isOk = false;
            String errMessage = "";
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
                String result = jObject.getString("result");
                if (result.equals("OK")) {
                    isOk = true;
                    this.accessCode = accessCode;
                } else {
                    errMessage = jObject.getString("error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!isOk) {
                txtStatus.setText("오류! 등록하지 못했습니다\n" + errMessage);
                Toast.makeText(this, "오류! 등록하지 못했습니다\n" + errMessage, Toast.LENGTH_SHORT).show();
            } else {
                getDeviceStatus("REQUEST");
            }
        }
    }

    private void getDeviceStatus(String s) {
        if (s.equals("REQUEST")) {
            accessCode = editAccessCode.getText().toString();

            JSONObject param = new JSONObject();
            JSONObject com = new JSONObject();
            try {
                param.put("cmd", "device_get_status");
                com.put("deviceid", this.deviceId);
                com.put("targetid", editTargetId.getText().toString());
                com.put("accesscode", accessCode);
                param.put("com", com);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String paramStr = param.toString();
            Log.d("param", paramStr);

            new ServiceCall("3").execute("http://ifwind.cf:50000/pinkcar", paramStr);
        }

        else {
            boolean isOk = false;
            String errMessage = "";
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
                String result = jObject.getString("result");
                if (result.equals("OK")) {
                    isOk = true;
                    this.linkOk = true;
                } else {
                    errMessage = jObject.getString("error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!isOk) {
                txtStatus.setText("오류! 대상기기ID 정보가 올바르지 않습니다\n" + errMessage);
                Toast.makeText(this, "오류! 대상기기ID 정보가 올바르지 않습니다\n" + errMessage, Toast.LENGTH_SHORT).show();
            } else {
                txtStatus.setText("기기연결이 완료되었습니다.");
            }
        }
    }

    class ServiceCall extends AsyncTask<String, String, String> {

        String mode = null;

        ServiceCall(String mode) {
            this.mode = mode;
        }

        // 백그라운드 작업 들어가기 직전에 처리되는 부분
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        // 백그라운드 작업 종료 후 처리되는 부분
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.INVISIBLE);

            Log.d("server-response", s);

            if (this.mode.equals("1")) {
                registerDevice(s);
            }
            else if (this.mode.equals("2")) {
                registerDeviceAccessCode(s);
            }
            else if (this.mode.equals("3")) {
                getDeviceStatus(s);
            }
        }

        // 실제 통신이 처리되는 부분
        @Override
        protected String doInBackground(String... params) {
            StringBuilder output = new StringBuilder();

            // 통신 부분은 반드시 try-catch로 예외처리 한다.
            try {
                URL url = new URL(params[0]); // 전달받은 urlString으로 url 객체 생성
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(10 * 1000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // set request json params
                    DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
                    wr.writeBytes (params[1]);
                    wr.flush ();
                    wr.close ();

                    int resCode = conn.getResponseCode();
                    if (resCode >= 200 && resCode < 400) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        // 한줄씩 읽어들여서 StringBuffer 객체에 추가
                        while ((line = reader.readLine()) != null) {
                            output.append(line);
                        }
                        reader.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output.toString();
        }
    }
}
