package com.ptn.myapplicationforlearn.ContactApp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert
    void insertContact(Contact... contacts);
    @Update
    void updateContact(Contact contact);
    @Delete
    void deleteContact(Contact contact);
    @Query("SELECT * FROM Contact")
    List<Contact> getAllContact();

}
