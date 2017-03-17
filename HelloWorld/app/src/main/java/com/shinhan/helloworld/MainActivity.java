package com.shinhan.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        TextView textview = (TextView)findViewById(R.id.textview);
        textview.setText("안녕, 신한");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButton2Clicked(View view){
        Toast.makeText(MainActivity.this, "두번째 버튼 클릭", Toast.LENGTH_SHORT).show();
    }

    // 웹브라우저 호출
    public void onButton3Clicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.shinhan.com"));
        startActivity(intent);

    }

    // 전화걸기 호출
    public void onButton4Clicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-4204-8070"));
        startActivity(intent);
    }
}
