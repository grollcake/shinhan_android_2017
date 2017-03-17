package com.shinhan.linearlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    public void onButtonClicked(View view) {
        ImageView imageView1 = (ImageView)findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView)findViewById(R.id.imageview2);
        Button button = (Button)view;

        if (button.getId() == R.id.button1) {
            // 첫번째 버튼 클릭 시
            imageView1.setImageResource(R.drawable.carnation1);
            imageView2.setImageResource(R.drawable.carnation2);
        } else {
            // 두번째 버튼 클릭 시
            imageView1.setImageResource(R.drawable.carnation2);
            imageView2.setImageResource(R.drawable.carnation3);
        }
    }
}
