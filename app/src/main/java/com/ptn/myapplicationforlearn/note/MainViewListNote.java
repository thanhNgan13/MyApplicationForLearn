package com.ptn.myapplicationforlearn.note;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ptn.myapplicationforlearn.R;
import com.ptn.myapplicationforlearn.itemL.CustomListAdapter;
import com.ptn.myapplicationforlearn.itemL.ItemNote;

import java.util.ArrayList;
import java.util.List;

// Implement ViewModel for list
//-hange value in DetailsActivity and the value will be changed in MainActivity
public class MainViewListNote extends AppCompatActivity {

    public static final int ADD_NEW_NOTE = 101;
    public static final int CHANGE_NOTE = 102;
    EditText editSearch;
    TextView txtTitle;
    LinearLayout layoutListNote;
    ImageView btnAddNote;
    ListView listViewNote;
    List<ItemNote> listData;
    ActivityResultLauncher<Intent> activityLauncher;
    // Declare ViewModel
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);
        editSearch = findViewById(R.id.editSearch);
        txtTitle = findViewById(R.id.txtTitle);
        layoutListNote = findViewById(R.id.layoutListNote);
        btnAddNote = findViewById(R.id.btnAddNote);
        listViewNote = findViewById(R.id.lvNote);
        btnAddNote.bringToFront();

        // Get ViewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        // Get list note from ViewModel
        CustomListAdapter adapter = new CustomListAdapter(this, noteViewModel.getListNote());
        listViewNote.setAdapter(adapter);

        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetailsNote.class);
            activityLauncher.launch(intent); // Sử dụng ActivityResultLauncher
        });

        listViewNote.setOnItemClickListener((parent, view, position, id) -> {
            // Open DetailsNoteActivity
            ItemNote itemNote = (ItemNote) parent.getItemAtPosition(position);
            Intent intent = new Intent(this, DetailsNote.class);
            intent.putExtra("EXTRA_ITEM_NOTE", itemNote);
            intent.putExtra("EXTRA_ITEM_POSITION_SEND", position); // Truyền vị trí
            activityLauncher.launch(intent); // Sử dụng ActivityResultLauncher
        });

        listViewNote.setOnItemLongClickListener((parent, view, position, id) -> {
            // Hiển thị checkbox để xóa
            adapter.setShowCheckbox(true);
            noteViewModel.showCheckBox(false);
            ((BaseAdapter) listViewNote.getAdapter()).notifyDataSetChanged();
            return true;
        });

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == MainViewListNote.CHANGE_NOTE && result.getData() != null) {
                        ItemNote returnedNote = result.getData().getParcelableExtra("RETURN_CHANGED_NOTE");
                        int position = result.getData().getIntExtra("EXTRA_ITEM_POSITION", -1);
                        if (position != -1) {// Cập nhật đối tượng trong list và thông báo adapter
                            noteViewModel.update(position, returnedNote);
                            ((BaseAdapter) listViewNote.getAdapter()).notifyDataSetChanged();
                        }
                    }
                    else if (result.getResultCode() == MainViewListNote.ADD_NEW_NOTE && result.getData() != null) {
                        ItemNote returnedNote = result.getData().getParcelableExtra("RETURN_NEW_NOTE");
                        noteViewModel.add(returnedNote);
                        ((BaseAdapter) listViewNote.getAdapter()).notifyDataSetChanged();
                    }
                });

    }
}