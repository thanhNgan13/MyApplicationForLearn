package com.ptn.myapplicationforlearn.ContactApp;

import static com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment.CONTACT_CHANGE_REQUEST;
import static com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment.CONTACT_CHANGE_SEND;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.ptn.myapplicationforlearn.ContactApp.helper.ConvertImage;
import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.ContactApp.ui.contact.ContactFragment;
import com.ptn.myapplicationforlearn.R;

public class DetailContact extends AppCompatActivity {

    ImageView imageContact, btnEdit;
    TextView tvName, tvPhone, tvType, txtAboutMe;
    Toolbar toolbar;
    Contact contactDetail;

    ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail_activity);
        imageContact = findViewById(R.id.contactImage);
        tvName = findViewById(R.id.txtName);
        tvPhone = findViewById(R.id.txtPhone);
        tvType = findViewById(R.id.txtTypePhone);
        btnEdit = findViewById(R.id.btnEdit);
        txtAboutMe = findViewById(R.id.txtAboutMe);
        toolbar = findViewById(R.id.toolbarDetailContact);

        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Contact"); // Thay "Your New Title" bằng tiêu đề bạn muốn
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        contactDetail = (Contact) getIntent().getParcelableExtra(ContactFragment.CONTACT_DETAIL_REQUEST);

        if (contactDetail != null) {
            tvName.setText(String.format("%s %s", contactDetail.getFirstName(), contactDetail.getLastName()));
            tvPhone.setText(contactDetail.getPhoneNumber());
            tvType.setText(contactDetail.getPhoneType());
            txtAboutMe.setText(String.format("About %s", contactDetail.getFirstName()));
            if (contactDetail.getImage() != null){
            imageContact.setImageBitmap(ConvertImage.convertStringToBitmap(contactDetail.getImage()));}
            else {
                imageContact.setImageResource(R.drawable.user_contact);
            }
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailContact.this, ChangeAddContact.class);
            intent.putExtra(CONTACT_CHANGE_REQUEST, contactDetail);
            activityLauncher.launch(intent);
        });


        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == ContactFragment.CHANGE_CONTACT && result.getData() != null){
                        try {
                            contactDetail = (Contact) result.getData().getParcelableExtra(CONTACT_CHANGE_SEND);
                            assert contactDetail != null;
                            tvName.setText(String.format("%s %s", contactDetail.getFirstName(), contactDetail.getLastName()));
                            tvPhone.setText(contactDetail.getPhoneNumber());
                            tvType.setText(contactDetail.getPhoneType());
                            txtAboutMe.setText(String.format("About %s", contactDetail.getFirstName()));
                            if (contactDetail.getImage() != null){
                                imageContact.setImageBitmap(ConvertImage.convertStringToBitmap(contactDetail.getImage()));}
                            else {
                                imageContact.setImageResource(R.drawable.user_contact);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {//
            Intent returnIntent = new Intent();
            returnIntent.putExtra(ContactFragment.CONTACT_DETAIL_SEND, contactDetail);
            setResult(ContactFragment.DETAIL_CONTACT, returnIntent);// Quay lại Activity trước đó khi nút quay lại được nhấn
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}