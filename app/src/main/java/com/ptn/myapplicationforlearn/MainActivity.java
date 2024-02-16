package com.ptn.myapplicationforlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView btnIncrease, btnDecrease;
    TextView txtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        txtCount = findViewById(R.id.txtCount);

        btnIncrease.setOnClickListener(v -> {
            int count = Integer.parseInt(txtCount.getText().toString());
            count++;
            txtCount.setText(String.valueOf(count));
        });

        btnDecrease.setOnClickListener(v -> {
            int count = Integer.parseInt(txtCount.getText().toString());
            count--;
            txtCount.setText(String.valueOf(count));
        });
    }
}












































































































