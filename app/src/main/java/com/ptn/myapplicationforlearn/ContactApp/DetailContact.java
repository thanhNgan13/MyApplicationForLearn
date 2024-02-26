package com.ptn.myapplicationforlearn.ContactApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment;
import com.ptn.myapplicationforlearn.R;

public class DetailContact extends AppCompatActivity {

    String[] itemPhoneType = {"Mobile", "Home", "Work", "Main", "Home Fax", "Work Fax", "Pager", "Other"};

    String[] itemEmailType = {"Home", "Work", "Other"};
    AutoCompleteTextView autoCompleteTextViewPhoneType, autoCompleteTextViewEmailType;
    Toolbar toolbar;

    Contact contactChange;
    int positionChange;
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

        contactChange = getIntent().getParcelableExtra("CHANGE_CONTACT_REQUEST");
        positionChange = getIntent().getIntExtra("POSITION_CONTACT_REQUEST", -1);

        // Nếu có dữ liệu truyền qua thì hiển thị lên các View
        if (contactChange != null) {
            ((EditText)findViewById(R.id.editTextFirstName)).setText(contactChange.getFirstName());
            ((EditText)findViewById(R.id.editTextLastName)).setText(contactChange.getLastName());
            ((EditText)findViewById(R.id.editTextPhone)).setText(contactChange.getPhoneNumber());
            ((EditText)findViewById(R.id.editTextEmail)).setText(contactChange.getEmail());
            autoCompleteTextViewPhoneType.setText(contactChange.getPhoneType(), false);
            autoCompleteTextViewEmailType.setText(contactChange.getEmailType(), false);
        }
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
        getMenuInflater().inflate(R.menu.menu_contact_new_and_change, menu);
        // Xử lý sự kiện khi nút Save được nhấn
        MenuItem saveItem = menu.findItem(R.id.save);
        MenuItem changeItem = menu.findItem(R.id.change);
        if (getIntent().getParcelableExtra("CHANGE_CONTACT_REQUEST") != null) {
            saveItem.setVisible(false);
            changeItem.setVisible(true);
        }
        else {
            saveItem.setVisible(true);
            changeItem.setVisible(false);
        }

        changeItem.setOnMenuItemClickListener(item -> {
            returnResultForIntent(contactChange, ContactFragment.CHANGE_CONTACT);
            return  true;
        });


        saveItem.setOnMenuItemClickListener(item -> {
            // Lấy dữ liệu từ các View
            String firstName = ((EditText)findViewById(R.id.editTextFirstName)).getText().toString();
            String lastName = ((EditText)findViewById(R.id.editTextLastName)).getText().toString();
            String phone = ((EditText)findViewById(R.id.editTextPhone)).getText().toString();



            // Kiểm tra dữ liệu
            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()){
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return false;
            }

            returnResultForIntent(new Contact(), ContactFragment.ADD_NEW_CONTACT);


            return true;
        });

        return true;
    }

    void returnResultForIntent(Contact contact, int mode) {

        // Lấy dữ liệu từ các View


        Intent returnIntent = new Intent();
        contact.setFirstName(((EditText)findViewById(R.id.editTextFirstName)).getText().toString());
        contact.setLastName(((EditText)findViewById(R.id.editTextLastName)).getText().toString());
        contact.setPhoneNumber(((EditText)findViewById(R.id.editTextPhone)).getText().toString());
        contact.setEmail(((EditText)findViewById(R.id.editTextEmail)).getText().toString());
        contact.setPhoneType(autoCompleteTextViewPhoneType.getText().toString());
        contact.setEmailType(autoCompleteTextViewEmailType.getText().toString());

        if (mode == ContactFragment.ADD_NEW_CONTACT) {
            returnIntent.putExtra("NEW_CONTACT", contact);
            setResult(ContactFragment.ADD_NEW_CONTACT, returnIntent);
        }
        else {
            returnIntent.putExtra("CHANGE_CONTACT_SEND", contact);
            returnIntent.putExtra("POSITION_CONTACT_SEND", positionChange);
            setResult(ContactFragment.CHANGE_CONTACT, returnIntent);
        }
        finish();
    }


}