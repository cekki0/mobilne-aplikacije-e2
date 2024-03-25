package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;

import com.example.fijiapp.adapters.UserAdapter;
import com.example.fijiapp.model.User;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OwnerActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        users.add(new User("user1@example.com", "password1", "John", "Doe", "123 Main St", "1234567890", "https://picsum.photos/400"));
        users.add(new User("user2@example.com", "password2", "Jane", "Smith", "456 Elm St", "0987654321", "https://picsum.photos/600"));
        users.add(new User("user3@example.com", "password3", "Alice", "Johnson", "789 Oak St", "9876543210", "https://picsum.photos/800"));

        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UserAdapter(users, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String query) {
        List<User> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(users);
        } else {
            String queryLowerCase = query.toLowerCase().trim();
            for (User user : users) {
                if (user.FirstName.toLowerCase().contains(queryLowerCase) ||
                        user.LastName.toLowerCase().contains(queryLowerCase) ||
                        user.Email.toLowerCase().contains(queryLowerCase)) {
                    filteredList.add(user);
                }
            }
        }
        adapter.filterList(filteredList);
    }

    public void onAddStaffBtnClick(View view) {
        Intent intent = new Intent(OwnerActivity.this, StaffRegistrationActivity.class);
        startActivity(intent);
    }
}