package com.ptn.myapplicationforlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaiTap2 extends AppCompatActivity {

    EditText edtNumberA, edtNumberB;
    ImageView btnAdd, btnSub, btnMul, btnDiv;
    ListView lvResult;

    ArrayList<String> arrResult = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_tap2);

        edtNumberA = findViewById(R.id.edtNumberA);
        edtNumberB = findViewById(R.id.edtNumberB);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        lvResult = findViewById(R.id.lvResult);
        arrResult = new ArrayList<>();

        CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, arrResult);
        lvResult.setAdapter(adapter);



        btnAdd.setOnClickListener(v -> {
            if (!edtNumberA.getText().toString().isEmpty() && !edtNumberB.getText().toString().isEmpty()) {
                float a = Float.parseFloat(edtNumberA.getText().toString());
                float b = Float.parseFloat(edtNumberB.getText().toString());
                String result = a + b + "";
                arrResult.add(result);
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(BaiTap2.this, "Vui lòng nhập đủ số", Toast.LENGTH_SHORT).show();
            }
        });

        btnSub.setOnClickListener(v -> {
            if (!edtNumberA.getText().toString().isEmpty() && !edtNumberB.getText().toString().isEmpty()) {
                float a = Float.parseFloat(edtNumberA.getText().toString());
                float b = Float.parseFloat(edtNumberB.getText().toString());
                String result = a - b + "";
                arrResult.add(result);
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(BaiTap2.this, "Vui lòng nhập đủ số", Toast.LENGTH_SHORT).show();
            }
        });

        btnMul.setOnClickListener(v -> {
            if (!edtNumberA.getText().toString().isEmpty() && !edtNumberB.getText().toString().isEmpty()) {
                float a = Float.parseFloat(edtNumberA.getText().toString());
                float b = Float.parseFloat(edtNumberB.getText().toString());
                String result = a * b + "";
                arrResult.add(result);
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(BaiTap2.this, "Vui lòng nhập đủ số", Toast.LENGTH_SHORT).show();
            }
        });

        btnDiv.setOnClickListener(v -> {
            if (!edtNumberA.getText().toString().isEmpty() && !edtNumberB.getText().toString().isEmpty()) {
                float a = Float.parseFloat(edtNumberA.getText().toString());
                float b = Float.parseFloat(edtNumberB.getText().toString());
                if (b == 0) {
                    Toast.makeText(BaiTap2.this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                }
                else {
                    String result = a / b + "";
                    arrResult.add(result);
                    adapter.notifyDataSetChanged();
                }
            }
            else {
                Toast.makeText(BaiTap2.this, "Vui lòng nhập đủ số", Toast.LENGTH_SHORT).show();
            }
        });




    }
    public class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int textViewResourceId, List<String> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);

            textView.setTextColor(Color.BLACK);

            return textView;
        }
    }


}