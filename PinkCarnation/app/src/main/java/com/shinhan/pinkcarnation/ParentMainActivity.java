package com.shinhan.pinkcarnation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ParentMainActivity extends AppCompatActivity {
    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    // 화면 UI 요소들
    ImageButton btnSettings = null;

    Integer demoIndex = 0;
    private final Integer[] demoPhotos = {R.drawable.example1, R.drawable.example2, R.drawable.example3};
    private final String[] demoMessages = {"사랑하는 사람과 행복한 순간을 공유하세요",
                        "핑크 카네이션은 여러분의 행복을 바랍니다",
                        "자녀분에게 사진을 요청해보세요!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);


        btnSettings = (ImageButton) findViewById(R.id.settings_icon);

        btnSettings.setVisibility(View.INVISIBLE);
        btnSettings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
             public boolean onLongClick(View v) {
                Intent intent = new Intent(ParentMainActivity.this, ParentSettingActivity.class);
                startActivity(intent);
                return true;
             }
         });

        doDemo();
    }

    private void doDemo() {
        ImageView photoFrame = (ImageView) findViewById(R.id.photoframe);
        photoFrame.setImageResource(demoPhotos[demoIndex]);
        TextView message = (TextView)findViewById(R.id.message);
        String msg = "\"" + demoMessages[demoIndex] + "\"";
        message.setText(msg);

        demoIndex = (demoIndex == demoPhotos.length - 1) ? 0 : demoIndex+1;

        // 5초 단위로 사진 변경
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                doDemo();
            }
        }, 5000);
    }


    public void onPhotoframeClicked(View view) {

        btnSettings.setVisibility(View.VISIBLE);

        // 5초 후에는 흐릿하게 보이게 변경
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                btnSettings.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }


    public void onSettingsClicked(View view) {

    }
}
