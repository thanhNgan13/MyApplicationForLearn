package com.ptn.myapplicationforlearn.ContactApp;

import static com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment.CONTACT_CHANGE_REQUEST;
import static com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment.CONTACT_CHANGE_SEND;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ptn.myapplicationforlearn.ContactApp.helper.ConvertImage;
import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment;
import com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactViewModel;
import com.ptn.myapplicationforlearn.R;

import java.io.IOException;

public class ChangeAddContact extends AppCompatActivity {

    String[] itemPhoneType = {"Mobile", "Home", "Work", "Main", "Home Fax", "Work Fax", "Pager", "Other"};

    String[] itemEmailType = {"Home", "Work", "Other"};
    AutoCompleteTextView autoCompleteTextViewPhoneType, autoCompleteTextViewEmailType;
    Toolbar toolbar;

    Contact contactChange;
    ArrayAdapter<String> adapterItemsPhoneType, adapterItemsEmailType;

    ImageView contactImage, contactImageDefault;
    ImageButton btnAddPhotoContact;

    boolean isCheckSelectImage = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_change_add_activity);

        toolbar = findViewById(R.id.toolbarDetailContact);
        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Contact");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ContactViewModel contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);

        contactImage = findViewById(R.id.contactImage);
        contactImageDefault = findViewById(R.id.contactImageDefault);
        btnAddPhotoContact = findViewById(R.id.btnAddPhotoContact);

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

        contactChange = getIntent().getParcelableExtra(CONTACT_CHANGE_REQUEST);

        // Nếu có dữ liệu truyền qua thì hiển thị lên các View
        if (contactChange != null) {
            getSupportActionBar().setTitle("Change Contact");
            ((EditText)findViewById(R.    id.editTextFirstName)).setText(contactChange.getFirstName());
            ((EditText)findViewById(R.id.editTextLastName)).setText(contactChange.getLastName());
            ((EditText)findViewById(R.id.editTextPhone)).setText(contactChange.getPhoneNumber());
            ((EditText)findViewById(R.id.editTextEmail)).setText(contactChange.getEmail());
            autoCompleteTextViewPhoneType.setText(contactChange.getPhoneType(), false);
            autoCompleteTextViewEmailType.setText(contactChange.getEmailType(), false);
            contactImage.setImageBitmap(ConvertImage.convertStringToBitmap(contactChange.getImage()));
            contactImage.setVisibility(View.VISIBLE);
            contactImageDefault.setVisibility(View.GONE);
            if (contactChange.getImage() != null) {
                contactImage.setImageBitmap(ConvertImage.convertStringToBitmap(contactChange.getImage()));
                contactImageDefault.setVisibility(View.GONE);
                contactImage.setVisibility(View.VISIBLE);
            }
            else {
                contactImage.setImageResource(R.drawable.user_contact);
                contactImageDefault.setVisibility(View.VISIBLE);
                contactImage.setVisibility(View.GONE);
            }
        }



        contactViewModel.getToastMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
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
        getMenuInflater().inflate(R.menu.menu_contact_new_and_change, menu);
        // Xử lý sự kiện khi nút Save được nhấn
        MenuItem saveItem = menu.findItem(R.id.save);
        MenuItem changeItem = menu.findItem(R.id.change);
        if (getIntent().getParcelableExtra(CONTACT_CHANGE_REQUEST) != null) {
            saveItem.setVisible(false);
            changeItem.setVisible(true);
        }
        else {
            saveItem.setVisible(true);
            changeItem.setVisible(false);
        }

        // Xử lý sự kiện khi nút Change được nhấn
        changeItem.setOnMenuItemClickListener(item -> {
            returnResultForIntent(contactChange, ContactFragment.CHANGE_CONTACT, "");
            return true;
        });

        // Xử lý sự kiện khi nút Save được nhấn
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
            returnResultForIntent(new Contact(), ContactFragment.ADD_NEW_CONTACT, "");
            return true;
        });

        return true;
    }

    void returnResultForIntent(Contact contact, int mode, String dataImage) {
        // Lấy dữ liệu từ các View
        Intent returnIntent = new Intent();
        contact.setFirstName(((EditText)findViewById(R.id.editTextFirstName)).getText().toString());
        contact.setLastName(((EditText)findViewById(R.id.editTextLastName)).getText().toString());
        contact.setPhoneNumber(((EditText)findViewById(R.id.editTextPhone)).getText().toString());
        contact.setEmail(((EditText)findViewById(R.id.editTextEmail)).getText().toString());
        contact.setPhoneType(autoCompleteTextViewPhoneType.getText().toString());
        contact.setEmailType(autoCompleteTextViewEmailType.getText().toString());
        contact.setImage(ConvertImage.convertBitmapToString(contactImage));
        if (mode == ContactFragment.ADD_NEW_CONTACT) {
            returnIntent.putExtra("NEW_CONTACT", contact);
            setResult(ContactFragment.ADD_NEW_CONTACT, returnIntent);
        }
        else {
            returnIntent.putExtra(CONTACT_CHANGE_SEND, contact);
            setResult(ContactFragment.CHANGE_CONTACT, returnIntent);
        }
        finish();
    }


    public void btnOpenImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
        isCheckSelectImage = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                Bitmap selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                contactImage.setImageBitmap(selectedBitmap);
                contactImageDefault.setVisibility(View.GONE);
                contactImage.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("SetImage", "Not Success: " + e.getMessage());

            }
        }
    }


}