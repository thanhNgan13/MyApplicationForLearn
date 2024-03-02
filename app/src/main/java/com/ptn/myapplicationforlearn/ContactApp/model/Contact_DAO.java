package com.ptn.myapplicationforlearn.ContactApp.model;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Contact_DAO {
    void insertContact(Contact... contacts);
    void updateContact(Contact contact);
    void deleteContact(Contact contact);
    void deleteAllContact();
    @Query("SELECT * FROM Contact")
    List<Contact> getAllContact();
    void getContactById(int id);
    void getContactByName(String name);
    void getContactByPhone(String phone);
    void getContactByEmail(String email);
    void getContactByPhoneType(String phoneType);
    void getContactByEmailType(String emailType);

}
