package com.ptn.myapplicationforlearn.NoteApp.note;

import androidx.lifecycle.ViewModel;

import com.ptn.myapplicationforlearn.NoteApp.note.itemL.ItemNote;

import java.util.ArrayList;
import java.util.List;

public class NoteViewModel extends ViewModel {
    private final List<ItemNote> listNote = new ArrayList<>();

    public List<ItemNote> getListNote() {
        return listNote;
    }

    // method add
    public void add(ItemNote itemNote) {
        listNote.add(itemNote);
    }

    // method update
    public void update(int position, ItemNote itemNote) {
        listNote.set(position, itemNote);
    }

    // method delete
    public void delete(int position) {
        listNote.remove(position);
    }

    // method show checkbox
    public void showCheckBox(boolean isShow) {
        for (ItemNote itemNote : listNote) {
            itemNote.setSelected(isShow);
        }
    }
}
