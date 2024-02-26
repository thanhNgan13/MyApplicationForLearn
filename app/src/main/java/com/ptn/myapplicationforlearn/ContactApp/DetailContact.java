package com.ptn.myapplicationforlearn.ContactApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.ContactApp.ui.home.HomeFragment;
import com.ptn.myapplicationforlearn.R;
import com.ptn.myapplicationforlearn.note.MainViewListNote;

public class DetailContact extends AppCompatActivity {

    String[] itemPhoneType = {"Mobile", "Home", "Work", "Main", "Home Fax", "Work Fax", "Pager", "Other"};

    String[] itemEmailType = {"Home", "Work", "Other"};
    AutoCompleteTextView autoCompleteTextViewPhoneType, autoCompleteTextViewEmailType;
    Toolbar toolbar;
    ArrayAdapter<String> adapterItemsPhoneType, adapterItemsEmailType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);

        toolbar = findViewById(R.id.toolbarDetailContact); // Thay thế 'toolbar' bằng id của Toolbar trong layout của bạn
        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Contact"); // Thay "Your New Title" bằng tiêu đề bạn muốn
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        autoCompleteTextViewPhoneType = findViewById(R.id.autoCompleteTextViewPhone);
        autoCompleteTextViewEmailType = findViewById(R.id.autoCompleteTextViewEmail);

        adapterItemsPhoneType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemPhoneType);
        adapterItemsEmailType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemEmailType);

        autoCompleteTextViewPhoneType.setAdapter(adapterItemsPhoneType);
        autoCompleteTextViewEmailType.setAdapter(adapterItemsEmailType);

        autoCompleteTextViewPhoneType.setText(adapterItemsPhoneType.getItem(0), false);
        autoCompleteTextViewEmailType.setText(adapterItemsEmailType.getItem(0), false);

        autoCompleteTextViewPhoneType.setOnItemClickListener((parent, view, position, id) -> {
            String item = adapterItemsPhoneType.getItem(position);
        });

        autoCompleteTextViewEmailType.setOnItemClickListener((parent, view, position, id) -> {
            String item = adapterItemsEmailType.getItem(position);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Quay lại Activity trước đó khi nút quay lại được nhấn
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact_new, menu);
        // Xử lý sự kiện khi nút Save được nhấn
        MenuItem saveItem = menu.findItem(R.id.save);
        saveItem.setOnMenuItemClickListener(item -> {
            // Lấy dữ liệu từ các View
            String firstName = ((EditText)findViewById(R.id.editTextFirstName)).getText().toString();
            String lastName = ((EditText)findViewById(R.id.editTextLastName)).getText().toString();
            String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();
            String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString();


            // Kiểm tra dữ liệu
            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Tạo đối tượng Contact mới
            Contact newContact = new Contact(firstName, lastName);
            // Tạo Intent để gửi đối tượng Contact trở lại
            Intent returnIntent = new Intent();
            returnIntent.putExtra("NEW_CONTACT", newContact);
            setResult(HomeFragment.ADD_NEW_CONTACT, returnIntent);
            finish(); // Đóng Activity này và quay trở lại Activity trước đó

            return true;
        });

        return true;
    }


}