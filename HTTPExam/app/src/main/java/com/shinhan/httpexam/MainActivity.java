package com.shinhan.httpexam;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        EditText editText = (EditText) findViewById(R.id.input01);
        String urlString = editText.getText().toString();
        if (urlString.indexOf("http") != -1) {
            new LoadHTML().execute(urlString);
        }
    }

    class LoadHTML extends AsyncTask<String, String, String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        // 백그라운드 작업 들어가기 직전에 처리되는 부분
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("HTML 요청 중...");
            dialog.show(); // 프로그레스 다이알로그 보여주기
        }

        // 백그라운드 작업 종료 후 처리되는 부분
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss(); // 프로그레스 다이알로그 닫기
            TextView textView = (TextView) findViewById(R.id.txtMsg);
            textView.setText(s);
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
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    int resCode = conn.getResponseCode();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    // 한줄씩 읽어들여서 StringBuffer 객체에 추가
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                    }
                    reader.close();
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output.toString();
        }
    }

}
