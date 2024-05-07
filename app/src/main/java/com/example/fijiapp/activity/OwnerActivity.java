package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.UserAdapter;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.UserRole;
import com.example.fijiapp.service.OwnerService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OwnerActivity extends AppCompatActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserAdapter adapter;
    private List<User> users = new ArrayList<>();

    private OwnerService ownerService = new OwnerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUserAuth = mAuth.getCurrentUser();

        assert currentUserAuth != null;
        String currentEmail = currentUserAuth.getEmail();

        ownerService.getStaffByOwnerEmail(currentEmail)
                .addOnSuccessListener(users -> {
                    this.users = users;
                    RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));

                    adapter = new UserAdapter(users, this);
                    recyclerView.setAdapter(adapter);
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                });


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

    private void populateSpinners() {
        // Get reference to the Spinners
        Spinner spinnerServiceType = findViewById(R.id.spinnerServiceType);
        Spinner spinnerServiceSubtype = findViewById(R.id.spinnerServiceSubtype);
    }

    public void onAddStaffBtnClick(View view) {
        Intent intent = new Intent(OwnerActivity.this, StaffRegistrationActivity.class);
        startActivity(intent);
    }
}