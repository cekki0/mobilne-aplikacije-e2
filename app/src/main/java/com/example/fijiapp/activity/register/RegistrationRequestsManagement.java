package com.example.fijiapp.activity.register;

import android.os.Bundle;
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

import java.util.List;

public class RegistrationRequestsManagement extends AppCompatActivity {

    private RegistrationRequestAdapter adapter;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_requests_management);

        fetchAndInitializeRecyclerView();
    }

    private void fetchAndInitializeRecyclerView() {
        UserService userService = new UserService();
        userService.getAllUsers()
                .addOnCompleteListener(new OnCompleteListener<List<User>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<User>> task) {
                        if (task.isSuccessful()) {
                            users = task.getResult();
                            initializeRecyclerView();
                        } else {
                            Toast.makeText(RegistrationRequestsManagement.this,
                                    "Failed to fetch requests", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RegistrationRequestAdapter(users, this);
        recyclerView.setAdapter(adapter);
    }
}