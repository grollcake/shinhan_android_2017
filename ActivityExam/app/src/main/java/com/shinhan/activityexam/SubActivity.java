package com.shinhan.activityexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent intent = getIntent();
        String string = intent.getStringExtra("String");
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        EditText editText = (EditText)findViewById(R.id.edittext);
        editText.setText(string);
    }

    public void onCloseButtonClicked(View view) {
        EditText editText = (EditText)findViewById(R.id.edittext);
        String string = editText.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("Result", string);
        setResult(RESULT_OK, intent);
        finish();
    }
}
