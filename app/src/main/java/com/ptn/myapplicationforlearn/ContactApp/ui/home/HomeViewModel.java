package com.ptn.myapplicationforlearn.ContactApp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptn.myapplicationforlearn.ContactApp.model.Contact;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class HomeViewModel extends ViewModel {

    private final List<Contact> contactsList = new ArrayList<>();

    public List<Contact> getContactsList () {
        return contactsList;
    }

    public void add(Contact contact) {
        contactsList.add(contact);
    }


    private MutableLiveData<List<Contact>> contactsLiveData = new MutableLiveData<>();

    public void setContacts(List<Contact> contacts) {
        contactsLiveData.setValue(contacts);
    }
    public LiveData<List<Contact>> getContacts() {
        return contactsLiveData;
    }

    public void addContact(Contact contact) {
        List<Contact> contacts = contactsLiveData.getValue();
        contacts.add(contact);
        contactsLiveData.setValue(contacts);
    }

    public ArrayList<Contact> getContactList() {
        return (ArrayList<Contact>) contactsLiveData.getValue();
    }
}