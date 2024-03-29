package com.ptn.myapplicationforlearn.ContactApp.ui.home;

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
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ptn.myapplicationforlearn.ContactApp.ChangeAddContact;
import com.ptn.myapplicationforlearn.ContactApp.model.ContactAdapter;
import com.ptn.myapplicationforlearn.ContactApp.model.Contact;
import com.ptn.myapplicationforlearn.R;
import com.ptn.myapplicationforlearn.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public static final int ADD_NEW_CONTACT = 1001;
    public static final int CHANGE_CONTACT = 1002;

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private ContactAdapter contactAdapter;

    private ArrayList<Contact> contactList;

    ActivityResultLauncher<Intent> activityLauncher;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        requireActivity().setTitle("Contact");

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Tạo sự kiện cho nút add
        binding.btnAddContact.bringToFront();
        binding.btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangeAddContact.class);
                activityLauncher.launch(intent); // Sử dụng activityLauncher ở đây
            }
        });

        contactList = homeViewModel.getContactList();
        // Khởi tạo Adapter và thiết lập RecyclerView
        contactAdapter = new ContactAdapter(contactList); // Bạn có thể truyền danh sách rỗng ở đây hoặc danh sách từ ViewModel
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(contactAdapter);

        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == ADD_NEW_CONTACT && result.getData() != null) {
                        Contact contact = (Contact) result.getData().getParcelableExtra ("NEW_CONTACT");
                        homeViewModel.addContact(contact);
                        contactAdapter.notifyDataSetChanged();                    }
                });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void filter(String text) {
        ArrayList<Contact> filteredlist = new ArrayList<>();
        for (Contact item : contactList) {
            if (item.getFirstName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        contactAdapter.filterList(filteredlist);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toobar_search_contact, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (searchView != null) {
            searchView.setQueryHint("Search Contact");
            // Thiết lập listener
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // Do the search
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    // Do the search
                    filter(newText);

                    return false;
                }
            });
        }  // Xử lý trường hợp searchView là null


        super.onCreateOptionsMenu(menu, inflater);
    }



}