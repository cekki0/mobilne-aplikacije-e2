package com.example.fijiapp.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.login.LoginActivity;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.service.CategoryService;
import com.google.android.material.button.MaterialButton;

public class CategoryEditActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.example.fijiapp.EXTRA_CATEGORY";

    private EditText editTextName;
    private EditText editTextDescription;
    private Category categoryToUpdate;
    private String oldName;
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonEditCategory = findViewById(R.id.buttonEditCategory);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CATEGORY)) {
            categoryToUpdate = (Category) intent.getSerializableExtra(EXTRA_CATEGORY);
            editTextName.setText(categoryToUpdate.Name);
            editTextDescription.setText(categoryToUpdate.Description);
            oldName = categoryToUpdate.Name;
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

        categoryToUpdate.Name = name;
        categoryToUpdate.Description = description;

        categoryService.deleteCategory(oldName);
        categoryService.updateCategory(categoryToUpdate);
        Toast.makeText(CategoryEditActivity.this, "Category updated!", Toast.LENGTH_SHORT).show();
        navigateToManagementPage();
    }
}
