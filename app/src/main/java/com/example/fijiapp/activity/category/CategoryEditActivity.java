package com.example.fijiapp.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.login.LoginActivity;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.Notification;
import com.example.fijiapp.model.User;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.NotificationService;
import com.example.fijiapp.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CategoryEditActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.example.fijiapp.EXTRA_CATEGORY";

    private EditText editTextName;
    private EditText editTextDescription;
    private Category categoryToUpdate;
    private CategoryService categoryService = new CategoryService();
    private NotificationService notificationService = new NotificationService();
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonEditCategory = findViewById(R.id.buttonEditCategory);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CATEGORY)) {
            categoryToUpdate = (Category) intent.getSerializableExtra(EXTRA_CATEGORY);
            editTextName.setText(categoryToUpdate.Name);
            editTextDescription.setText(categoryToUpdate.Description);
        }

        buttonEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory();
            }
        });
    }

    public void navigateToManagementPage() {
        Intent intent = new Intent(this, CategoryManagementAdminActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateCategory() {

        String name = editTextName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(CategoryEditActivity.this, "Name and description cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        String oldName = categoryToUpdate.Name;
        categoryToUpdate.Name = name;
        categoryToUpdate.Description = description;

        UserService userService = new UserService();
        Task<List<User>> task = userService.getAllServiceProviders();

        categoryService.updateCategory(categoryToUpdate);
        task.addOnCompleteListener(new OnCompleteListener<List<User>>() {
            @Override
            public void onComplete(@NonNull Task<List<User>> task) {
                if (task.isSuccessful()) {
                    List<User> serviceProviders = task.getResult();
                    Log.d("TAG", String.valueOf(serviceProviders.size()));
                    for (User serviceProvider : serviceProviders) {
                        String receiverId = serviceProvider.Id;
                        String notificationMessage = "Category " + oldName + " changed its name to " + name;
                        Notification notification = new Notification("Category updated!", receiverId, currentUserId, notificationMessage);
                        notificationService.addNotification(notification);
                    }
                    Toast.makeText(CategoryEditActivity.this, "Category updated!", Toast.LENGTH_SHORT).show();
                    navigateToManagementPage();
                } else {
                    Log.e("TAG", "Error getting service providers: " + task.getException());
                    Toast.makeText(CategoryEditActivity.this, "Failed to update category. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
