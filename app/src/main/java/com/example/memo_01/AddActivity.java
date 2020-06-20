package com.example.memo_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText edtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtText = findViewById(R.id.edt_memo);

        findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edtText.getText().toString();

                if (str.length() > 0) {
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);

                    // AddActivity 에서 입력받은 문자열을 MainActivity 로 보냄
                    Intent intent = new Intent();
                    intent.putExtra("main", str);
                    intent.putExtra("sub", substr);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}