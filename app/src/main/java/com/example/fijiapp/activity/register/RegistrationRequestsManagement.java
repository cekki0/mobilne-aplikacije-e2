package com.example.fijiapp.activity.register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.adapters.RegistrationRequestAdapter;
import com.example.fijiapp.adapters.UserAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.User;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRequestsManagement extends AppCompatActivity {

    private RegistrationRequestAdapter adapter;
    private List<User> users;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_requests_management);
        searchEditText = findViewById(R.id.searchEditText);
        ImageButton searchButton = findViewById(R.id.searchButton);
        fetchAndInitializeRecyclerView();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                searchUsers(query);
            }
        });
    }

    private void searchUsers(String query) {
        Log.d("GAS",query.toLowerCase());
        List<User> filteredList = new ArrayList<>();
        for (User user : users) {
            if (user.Company.Name.toLowerCase().contains(query.toLowerCase()) ||
                    user.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    user.Email.toLowerCase().contains(query.toLowerCase()) ||
                    user.Company.Email.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        initializeRecyclerView(filteredList);
    }


    private void fetchAndInitializeRecyclerView() {
        UserService userService = new UserService();
        userService.getAllUsers()
                .addOnCompleteListener(new OnCompleteListener<List<User>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<User>> task) {
                        if (task.isSuccessful()) {
                            users = task.getResult();
                            initializeRecyclerView(users);
                        } else {
                            Toast.makeText(RegistrationRequestsManagement.this,
                                    "Failed to fetch requests", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeRecyclerView(List<User> users) {
        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RegistrationRequestAdapter(users, this);
        recyclerView.setAdapter(adapter);
    }
}