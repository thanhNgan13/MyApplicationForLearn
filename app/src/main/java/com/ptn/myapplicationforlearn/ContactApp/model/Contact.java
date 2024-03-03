package com.ptn.myapplicationforlearn.ContactApp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Contact implements Parcelable {
    @PrimaryKey
    @NonNull
    String phoneNumber;
    @ColumnInfo
    String firstName;
    @ColumnInfo
    String lastName;
    @ColumnInfo
    String email;
    @ColumnInfo
    String phoneType;
    @ColumnInfo
    String emailType;

    @ColumnInfo
    String image;

    public Contact(String firstName, String lastName, String phoneNumber, String email, String phoneType, String emailType, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.phoneType = phoneType;
        this.emailType = emailType;
        this.image = image;
    }

    public Contact(String firstName, String lastName, String phoneNumber, String email, String phoneType, String emailType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.phoneType = phoneType;
        this.emailType = emailType;
    }

    public Contact() {
    }



    protected Contact(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        phoneType = in.readString();
        emailType = in.readString();
        image = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(phoneType);
        dest.writeString(emailType);
        dest.writeString(image);
    }

    // Getters and Setters for your fields

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
