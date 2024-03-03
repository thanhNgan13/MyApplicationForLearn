package com.ptn.myapplicationforlearn.ContactApp.ui.contact;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptn.myapplicationforlearn.ContactApp.model.AppDatabase;
import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.ContactApp.model.ContactDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Contact>> contactsLiveData;


    private AppDatabase appDatabase;
    private ContactDAO contactDao;

    public ContactViewModel(Application application) {
        super(application);
        contactsLiveData = new MutableLiveData<>();
        contactsLiveData.setValue(new ArrayList<>());
        loadContactsFromDatabase();
    }

    public void loadContactsFromDatabase() {
        AsyncTask.execute(() -> {
            appDatabase = AppDatabase.getINSTANCE(getApplication());
            contactDao = appDatabase.contactDao();
            List<Contact> contacts = contactDao.getAllContact(); // Lấy danh sách contact từ database
            contactsLiveData.postValue(contacts);
        });
    }


    public void addContact(Contact contact) {
        AsyncTask.execute(() -> {
            contactDao.insertContact(contact); // Thêm mới contact vào database
            // Sau khi thêm, cập nhật LiveData bằng cách lấy lại toàn bộ contacts từ database
            List<Contact> contacts = contactDao.getAllContact(); // Giả sử đây là phương thức lấy tất cả contacts
            contactsLiveData.postValue(contacts);
        });
    }


    public ArrayList<Contact> getContactList() {
        return (ArrayList<Contact>) contactsLiveData.getValue();
    }

    public void changeContact(Contact contact, int position) {
        List<Contact> contacts = contactsLiveData.getValue();
        contacts.set(position, contact);
        contactsLiveData.setValue(contacts);
    }

    public void deleteContact(int position) {
        List<Contact> contacts = contactsLiveData.getValue();
        contacts.remove(position);
        contactsLiveData.setValue(contacts);
    }

    public LiveData<List<Contact>> getContactsLiveData() {
        return contactsLiveData;
    }

}