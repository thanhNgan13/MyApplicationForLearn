package com.ptn.myapplicationforlearn.ContactApp.custom;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactsList;

    public ContactAdapter(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_custom_recycler_view, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        holder.contactName.setText(contact.getFirstName() + " " + contact.getLastName());
        holder.contactInitialsCircle.setText(contact.getFirstName().substring(0, 1));
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }



    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView contactInitialsCircle;
        public TextView contactName;

        public ContactViewHolder(View view) {
            super(view);
            contactInitialsCircle = view.findViewById(R.id.contact_initials_circle);
            contactName = view.findViewById(R.id.contact_name);
        }
    }



}
