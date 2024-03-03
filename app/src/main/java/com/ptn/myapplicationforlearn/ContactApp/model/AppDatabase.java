package com.ptn.myapplicationforlearn.ContactApp.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Contact.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO contactDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getINSTANCE(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "contact-database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
