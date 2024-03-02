package com.ptn.myapplicationforlearn.NoteApp.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptn.myapplicationforlearn.R;
import com.ptn.myapplicationforlearn.NoteApp.note.itemL.ItemNote;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsNote extends AppCompatActivity {

    ImageView btnBack;
    EditText editTitleNote, editContentNote;
    TextView txtDateCreateNote;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);
        btnBack = findViewById(R.id.btnBack);
        editTitleNote = findViewById(R.id.editTitleNote);
        editContentNote = findViewById(R.id.editContentNote);
        txtDateCreateNote = findViewById(R.id.txtDateCreateNote);
        // Lấy đối tượng ItemNote từ Intent
        ItemNote itemNote = getIntent().getParcelableExtra("EXTRA_ITEM_NOTE");
        position = getIntent().getIntExtra("EXTRA_ITEM_POSITION_SEND", -1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String dateNow = sdf.format(Calendar.getInstance().getTime());
        // Nếu ItemNote không null thì hiển thị dữ liệu lên giao diện
        if (itemNote != null) {
            editTitleNote.setText(itemNote.getItemNameTitle());
            editContentNote.setText(itemNote.getItemNameContent());
            txtDateCreateNote.setText(itemNote.getItemNameDateCreate());
            btnBack.setOnClickListener(v -> {
                returnIntent(itemNote, MainViewListNote.CHANGE_NOTE, editTitleNote.getText().toString(), editContentNote.getText().toString(), dateNow);

            });
        }
        else {
            txtDateCreateNote.setText(dateNow);
            btnBack.setOnClickListener(v -> {
                returnIntent(new ItemNote(), MainViewListNote.ADD_NEW_NOTE, editTitleNote.getText().toString(), editContentNote.getText().toString(), txtDateCreateNote.getText().toString());
            });
        }

    }
    void returnIntent(ItemNote itemNote, int Mode, String title, String content, String dateCreate) {
        Intent returnIntent = new Intent();
        itemNote.setTitle(title);
        itemNote.setContent(content);
        itemNote.setDateCreate(dateCreate);
        if (Mode == MainViewListNote.ADD_NEW_NOTE) {
            returnIntent.putExtra("RETURN_NEW_NOTE", itemNote);
            setResult(MainViewListNote.ADD_NEW_NOTE, returnIntent);
        }
        else {
            returnIntent.putExtra("RETURN_CHANGED_NOTE", itemNote);
            returnIntent.putExtra("EXTRA_ITEM_POSITION", position);
            setResult(MainViewListNote.CHANGE_NOTE, returnIntent);
        }
        finish();
    }
}