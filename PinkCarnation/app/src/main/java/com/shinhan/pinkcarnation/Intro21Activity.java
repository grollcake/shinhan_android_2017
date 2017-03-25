package com.shinhan.pinkcarnation;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Intro21Activity extends AppCompatActivity {
    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    String deviceId = null;
    String accessCode = null;

    // 화면 UI 요소들
    TextView txtDeviceId = null;
    TextView txtStatus = null;
    EditText editPw = null;
    ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro21);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);

        // 화면 UI 요소들 변수에 할당
        txtDeviceId = (TextView)findViewById(R.id.txtDeviceid);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        editPw = (EditText)findViewById(R.id.editPw);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        // 화면 UI 요소 초기상태 세팅
        editPw.setEnabled(false);


        registerDevice("REQUEST");
    }

    private void registerDevice(String s) {
        if (s.equals("REQUEST")) {
            JSONObject param = new JSONObject();
            try {
                param.put("cmd", "device_register");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String paramStr = param.toString();
            Log.d("param", paramStr);

            new ServiceCall("1").execute("http://ifwind.cf:5000/pinkcar", paramStr);
        }

        else  {
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
                JSONObject data = jObject.getJSONObject("data");
                String deviceid = data.getString("deviceid");
                txtDeviceId.setText(deviceid);
                this.deviceId = deviceid;
                editPw.setEnabled(true);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerAccessCode(String s) {
        if (s.equals("REQUEST")) {
            // 제어코드(패스워드) 등록
            JSONObject param = new JSONObject();
            JSONObject com = new JSONObject();
            JSONObject biz = new JSONObject();
            try {
                param.put("cmd", "device_register");

                com.put("deviceid", deviceId);
                param.put("com", com);

                biz.put("accesscode", accessCode);
                param.put("biz", biz);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String paramStr = param.toString();
            Log.d("param", paramStr);

            new ServiceCall("2").execute("http://ifwind.cf:5000/pinkcar", paramStr);
        } 
        
        else {
            boolean isOk = false;
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
                String result = jObject.getString("result");
                
                if (result.equals("OK")) {
                    String serverResponseAccessCode = jObject.getJSONObject("data").getString("accesscode");
                    if (accessCode.equals(serverResponseAccessCode)) {
                        Toast.makeText(this, "제어코드를 등록했습니다", Toast.LENGTH_SHORT).show();

                        // 데이타 저장
                        ss.put("Main", "Role", "Parent");
                        ss.put("Main", "DeviceID", deviceId);
                        ss.put("Main", "AccessCode", accessCode);
                        ss.put("Main", "IntroDone", "Yes");

                        // 다음 화면으로 이동
                        Intent intent = new Intent(this, Intro30Activity.class);
                        startActivity(intent);

                        isOk = true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!isOk) {
                Toast.makeText(this, "오류! 제어코드를 등록하지 못했습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnNextClicked(View view) {

        deviceId = txtDeviceId.getText().toString();
        accessCode = editPw.getText().toString();

        if (deviceId == null || deviceId.isEmpty()) {
            Toast.makeText(this, "오류! 기기ID를 할당받아야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (accessCode == null || accessCode.isEmpty()) {
            Toast.makeText(this, "오류! 제어코드를 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (accessCode.length() != 6) {
            Toast.makeText(this, "오류! 제어코드는 6자리로 입력해야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        registerAccessCode("REQUEST");
    }

    public void btnCopyClicked(View view) {
        if (deviceId != null && !deviceId.equals("")) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Pinkcar", deviceId);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "기기ID를 복사했습니다.\n카톡 등으로 자녀에게 전달해주세요", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "복사할 기기ID 값이 없습니다.", Toast.LENGTH_SHORT).show();
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
            if (this.mode.equals("1")) {
                txtStatus.setText("기기ID를 요청중입니다.\n잠시만 기다려주세요.");
            }
            else if (this.mode.equals("2")) {
                txtStatus.setText("패스워드를 등록 중입니다. 잠시만 기다려주세요.");
            }
            progressBar.setVisibility(View.VISIBLE);
        }

        // 백그라운드 작업 종료 후 처리되는 부분
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressBar.setVisibility(View.INVISIBLE);

            Log.d("server-response", s);

            if (this.mode.equals("1")) {
                txtStatus.setText("기기ID를 할당 받았습니다.\n제어코드를 입력해주세요.");
                registerDevice(s);
            }
            else if (this.mode.equals("2")) {
                txtStatus.setText("제어코드를 등록했습니다.");
                registerAccessCode(s);
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
