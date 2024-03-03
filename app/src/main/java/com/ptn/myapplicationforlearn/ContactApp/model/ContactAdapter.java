package com.ptn.myapplicationforlearn.ContactApp.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptn.myapplicationforlearn.ContactApp.helper.ConvertImage;
import com.ptn.myapplicationforlearn.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactsList;
    private OnClickListener onClickListener;


    public ContactAdapter(List<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<Contact> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        contactsList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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

        if(contact.getImage() != null) {
            holder.contactInitialsCircle.setVisibility(View.GONE);
            holder.contactImage.setVisibility(View.VISIBLE);
            holder.contactImage.setImageBitmap(ConvertImage.convertStringToBitmap(contact.getImage()));
        }
        else {

            holder.contactInitialsCircle.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, contact);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Contact model);
    }



    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView contactInitialsCircle;
        public TextView contactName;

        public ImageView contactImage;

        public ContactViewHolder(View view) {
            super(view);
            contactInitialsCircle = view.findViewById(R.id.contact_initials_circle);
            contactName = view.findViewById(R.id.contact_name);
            contactImage = view.findViewById(R.id.contact_image)

   ;
        }


    }



}
