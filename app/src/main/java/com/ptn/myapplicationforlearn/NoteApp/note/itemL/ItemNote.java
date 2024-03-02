package com.ptn.myapplicationforlearn.NoteApp.note.itemL;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemNote implements Parcelable {
    private String itemNameTitle, itemNameContent, itemNameDateCreate;

    private boolean isSelected = false;

    // Constructor
    public ItemNote(String itemNameTitle, String itemNameContent, String itemNameDateCreate) {
        this.itemNameTitle = itemNameTitle;
        this.itemNameContent = itemNameContent;
        this.itemNameDateCreate = itemNameDateCreate;
    }

    public ItemNote() {

    }

    // Getters
    public String getItemNameTitle() {
        return itemNameTitle;
    }

    public String getItemNameContent() {
        return itemNameContent;
    }

    public String getItemNameDateCreate() {
        return itemNameDateCreate;
    }

    public boolean isSelected() {
        return isSelected;
    }

    // Parcelable implementation
    protected ItemNote(Parcel in) {
        itemNameTitle = in.readString();
        itemNameContent = in.readString();
        itemNameDateCreate = in.readString();
    }

    public static final Creator<ItemNote> CREATOR = new Creator<ItemNote>() {
        @Override
        public ItemNote createFromParcel(Parcel in) {
            return new ItemNote(in);
        }

        @Override
        public ItemNote[] newArray(int size) {
            return new ItemNote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(itemNameTitle);
        parcel.writeString(itemNameContent);
        parcel.writeString(itemNameDateCreate);
    }

    public void setTitle(String modifiedTitle) {
        this.itemNameTitle = modifiedTitle;
    }

    public void setContent(String modifiedContent) {
        this.itemNameContent = modifiedContent;
    }

    public void setDateCreate(String modifiedDateCreate) {
        this.itemNameDateCreate = modifiedDateCreate;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
