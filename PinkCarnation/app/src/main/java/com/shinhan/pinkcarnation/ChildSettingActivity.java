package com.shinhan.pinkcarnation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChildSettingActivity extends AppCompatActivity {

    // 쉐어드 프리퍼런스 접근 클래스
    SimpleStorage ss = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_setting);

        // 쉐어드 프리퍼런스 초기화
        ss = new SimpleStorage(this);
    }

    public void btnBackClicked(View view) {
        finish();
    }

    public void btnResetClicked(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("모든 데이타가 삭제됩니다. 진행하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ss.clear("Main");
                        Toast.makeText (ChildSettingActivity.this,
                                "초기화 되었습니다. 첫 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();

                        // 2초간 멈춘 후에 다음 페이지로 넘어간다.
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(ChildSettingActivity.this, Intro10Activity.class);
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("주의");
        alert.show();
    }
}
