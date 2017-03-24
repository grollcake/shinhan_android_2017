package com.shinhan.threadexam;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressHandler progressHandler = new ProgressHandler();
    Handler handler = new Handler();
    ProgressRunnable progressRunnable = new ProgressRunnable();

    boolean isRunning = false;
    ProgressBar progressBar1, progressBar2;
    TextView textView1, textView2;

    public class ProgressHandler extends Handler {  // 스레드 대신 메인UI 접근
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar1.incrementProgressBy(5);
            if (progressBar1.getProgress() == progressBar1.getMax()) {
                textView1.setText("Done");
            } else {
                textView1.setText("Working..." + progressBar1.getProgress());
            }
        }
    }

    public class ProgressRunnable implements Runnable {  // 스레드 대신 메인UI 접근
        @Override
        public void run() {
            progressBar2.incrementProgressBy(1);
            if (progressBar2.getProgress() == progressBar2.getMax()) {
                textView2.setText("Done");
            } else {
                textView2.setText("Working..." + progressBar2.getProgress());
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView) findViewById(R.id.textview1);
        textView2 = (TextView) findViewById(R.id.textview2);
        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar1.setProgress(0);
        progressBar2.setProgress(0);

        Thread thread = new Thread(new Runnable() { // 스레드 정의
            @Override
            public void run() { // 스레드에서 실행되는 영역 (메인UI 접근 불가)
                try {
                    for(int i=0; i<20 && isRunning; i++) {
                        Thread.sleep(1000);
                        // 1.핸들러를 이용한 메시지 전송 방법
                        Message msg = progressHandler.obtainMessage();
                        progressHandler.sendMessage(msg);

                        // 2. Runnable 객체를 실행하는 방법
                        handler.post(progressRunnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        isRunning = true;
        thread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
}
