package com.ptn.myapplicationforlearn.ContactApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptn.myapplicationforlearn.R;

public class DetailContactt extends AppCompatActivity {

    ImageView imageContact, btnEdit;

    TextView tvName, tvPhone, tvEmail, tvAddress, tvType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contactt);
        imageContact = findViewById(R.id.contactImage);
        tvName = findViewById(R.id.txtName);
        tvPhone = findViewById(R.id.txtPhone);
        tvType = findViewById(R.id.txtTypePhone);

        btnEdit = findViewById(R.id.btnEdit);

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailContactt.this, ChangeContact.class);
            startActivity(intent);
        });
    }
}