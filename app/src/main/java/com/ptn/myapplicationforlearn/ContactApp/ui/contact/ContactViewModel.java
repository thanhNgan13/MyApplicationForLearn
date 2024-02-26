package com.ptn.myapplicationforlearn.ContactApp.ui.contact;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptn.myapplicationforlearn.ContactApp.model.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactViewModel extends ViewModel {
    private final MutableLiveData<List<Contact>> contactsLiveData;


    public ContactViewModel() {
        contactsLiveData = new MutableLiveData<>();
        contactsLiveData.setValue(new ArrayList<>(Arrays.asList(
                new Contact("Phan", "Thanh", "123456789", "abc@gmail.com", "Mobile", "Home"),
                new Contact("Linh", "Dao", "987654321", "linh@example.com", "Work", "Work"),
                new Contact("Nam", "Pham", "456123789", "nam@domain.com", "Pager", "Other"),
                new Contact("Huyen", "Tran", "321456987", "huyen@website.com", "Home", "Home")
        )));


    }

    public void addContact(Contact contact) {
        List<Contact> contacts = contactsLiveData.getValue();
        contacts.add(contact);
        contactsLiveData.setValue(contacts);
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
}