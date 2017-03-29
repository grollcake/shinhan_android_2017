package com.shinhan.pinkcarnation.PinkcarSerivce;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;


public class HttpService extends AsyncTask<String, String, String>{
    static final String API_URL = "http://192.168.200.31:50000/pinkcar";

    Integer resCode;
    String resMessage;
    String error = null;
    HttpCallBack httpCallBack;

    public HttpService(HttpCallBack callback) {
        this.httpCallBack = callback;
    }

    // 백그라운드 작업 들어가기 직전에 처리되는 부분
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 백그라운드 작업 종료 후 처리되는 부분
    @Override
    protected void onPostExecute(String resMessage) {
        super.onPostExecute(resMessage);
        this.resMessage = resMessage;

        Log.d("HttpService-ResCode", this.resCode+"");
        Log.d("HttpService-ResMessage", this.resMessage);
        if (this.error != null) {
            Log.d("HttpService-Error", this.error);
        }
        if (this.resCode == null) this.resCode = 0;
        if (this.resMessage == null) this.resMessage = "";
        if (this.httpCallBack != null) this.httpCallBack.callback(this.resCode, this.resMessage, this.error);
    }

    // 실제 통신이 처리되는 부분
    @Override
    protected String doInBackground(String... params) {

        Log.d("HttpService-Request", params[0]);

        StringBuilder output = new StringBuilder();

        // 통신 부분은 반드시 try-catch로 예외처리 한다.
        try {
            URL url = new URL(this.API_URL); // 전달받은 urlString으로 url 객체 생성
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10 * 1000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // set request json params
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(params[0]);
                wr.flush();
                wr.close();

                this.resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                // 한줄씩 읽어들여서 StringBuffer 객체에 추가
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                reader.close();
                conn.disconnect();
            }

        } catch (SocketTimeoutException e) {
            this.error = "서버에 접근할 수 없습니다 [" + e.getMessage() + "]";
            return "";
        } catch (FileNotFoundException e) {
            this.error = "서버에 접근할 수 없습니다 [" + e.getMessage() + "]";
            return "";
        }
        catch (Exception e) {
            e.printStackTrace();
            this.error = e.getMessage();
            return "";
        }

        return output.toString();
    }
}
