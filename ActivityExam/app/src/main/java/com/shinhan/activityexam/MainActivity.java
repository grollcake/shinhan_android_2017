package com.shinhan.activityexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButton1Clicked(View view) {
        EditText editText = (EditText)findViewById(R.id.edittext);
        String string = editText.getText().toString();

//        Intent intent = new Intent(MainActivity.this, SubActivity.class);
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra("String", string);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                EditText editText = (EditText)findViewById(R.id.edittext);
                String string = data.getStringExtra("Result");
                editText.setText(string);
            }
        }
    }
}
