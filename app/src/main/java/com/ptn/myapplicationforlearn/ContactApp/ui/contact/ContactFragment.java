package com.ptn.myapplicationforlearn.ContactApp.ui.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptn.myapplicationforlearn.ContactApp.ChangeAddContact;
import com.ptn.myapplicationforlearn.ContactApp.DetailContact;
import com.ptn.myapplicationforlearn.ContactApp.model.ContactAdapter;
import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.R;
import com.ptn.myapplicationforlearn.databinding.FragmentContactBinding;

import java.util.ArrayList;

public class ContactFragment extends Fragment {
    public static final int ADD_NEW_CONTACT = 1003;
    public static final int CHANGE_CONTACT = 1002;
    public static final int DETAIL_CONTACT = 1005;

    public static final String CONTACT_DETAIL_REQUEST = "CONTACT_DETAIL_REQUEST";
    public static final String CONTACT_DETAIL_SEND = "CONTACT_DETAIL_SEND";

    public static final String CONTACT_CHANGE_REQUEST = "CONTACT_CHANGE_REQUEST";

    public static final String CONTACT_CHANGE_SEND = "CONTACT_CHANGE_SEND";


    private ContactAdapter contactAdapter;

    private ArrayList<Contact> contactList;

    ActivityResultLauncher<Intent> activityLauncher;

    private FragmentContactBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ContactViewModel contactViewModel =
                new ViewModelProvider(this).get(ContactViewModel.class);

        binding = FragmentContactBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        contactList = contactViewModel.getContactList();
        // Khởi tạo Adapter và thiết lập RecyclerView
        contactAdapter = new ContactAdapter(contactList); // Bạn có thể truyền danh sách rỗng ở đây hoặc danh sách từ ViewModel
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(contactAdapter);


        contactViewModel.getContactsLiveData().observe(getViewLifecycleOwner(), contacts -> {
            // Cập nhật contactList với dữ liệu mới
            contactList.clear();
            contactList.addAll(contacts);
            // Thông báo cho adapter về sự thay đổi dữ liệu để cập nhật RecyclerView
            contactAdapter.notifyDataSetChanged();
        });


        // Thiết lập sự kiện click cho nút thêm mới
        binding.btnAddNewContact.bringToFront();
        binding.btnAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeAddContact.class);
                activityLauncher.launch(intent);
            }
        });


        // Thiết lập sự kiện click cho item trên RecyclerView
        contactAdapter.setOnClickListener(new ContactAdapter.OnClickListener() {
            @Override
            public void onClick(Contact model) {
                Intent intent = new Intent(getContext(), DetailContact.class);
                intent.putExtra(CONTACT_DETAIL_REQUEST, model);
                activityLauncher.launch(intent);
            }
        });

        // Khởi tạo ActivityResultLauncher để nhận kết quả trả về từ Activity
        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == ADD_NEW_CONTACT && result.getData() != null) {
                        Contact contact = (Contact) result.getData().getParcelableExtra("NEW_CONTACT");
                        contactViewModel.addContact(contact);
                        contactAdapter.notifyDataSetChanged();
                    } else if (result.getResultCode() == DETAIL_CONTACT && result.getData() != null) {
                        Contact contact = (Contact) result.getData().getParcelableExtra(CONTACT_DETAIL_SEND);
                        contactViewModel.changeContact(contact);
                        contactAdapter.notifyDataSetChanged();

                    }
                });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toobar_search_contact, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search contact");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void filter(String text) {
        ArrayList<Contact> filteredlist = new ArrayList<>();
        for (Contact item : contactList) {
            String fullName = item.getFirstName() + " " + item.getLastName();
            if (fullName.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        contactAdapter.filterList(filteredlist);
    }
}