package com.example.fijiapp.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.SubCategoryAddAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.SubCategoryService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CategoryAddActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubCategoryAddAdapter adapter;
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private Button buttonAddSubCategory, buttonAddCategory;
    private TextInputEditText subCategoryName,subCategoryDescription,categoryName,categoryDescription;
    private Spinner subCategoryTypeSpinner;
    private SubCategoryService subCategoryService = new SubCategoryService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        recyclerView = findViewById(R.id.recyclerViewWorkingDays);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubCategoryAddAdapter(subCategoryList);
        recyclerView.setAdapter(adapter);

        categoryName = findViewById(R.id.editTextCategoryName);
        categoryDescription = findViewById(R.id.editTextCategoryDescription);

        subCategoryName = findViewById(R.id.editTextSubCategoryName);
        subCategoryDescription = findViewById(R.id.editTextSubCategoryDescription);

        buttonAddSubCategory = findViewById(R.id.buttonAddSubCategory);
        buttonAddCategory = findViewById(R.id.buttonAddCategory);
        subCategoryTypeSpinner = findViewById(R.id.subCategoryTypeSpinner);

        buttonAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubCategory();
            }
        });

        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });
    }

    private void createCategory() {
        String name = categoryName.getText().toString();
        String description = categoryDescription.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Enter category name and description!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (subCategoryList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Add one or more sub categories!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> subCategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategoryList) {
            subCategoryNames.add(subCategory.Name);
            subCategoryService.addSubCategory(subCategory);
        }

        Category category = new Category(name, description, subCategoryNames);
        categoryService.addCategory(category);
        Toast.makeText(getApplicationContext(), "Category created!", Toast.LENGTH_SHORT).show();
        navigateToManagementPage();
    }


    private void addSubCategory()
    {
        String name = subCategoryName.getText().toString();
        String description = subCategoryDescription.getText().toString();
        SubCategoryType type = SubCategoryType.values()[subCategoryTypeSpinner.getSelectedItemPosition()];

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "Enter sub category name and description!", Toast.LENGTH_SHORT).show();
            return;
        }

        SubCategory subCategory = new SubCategory(name, description, type);
        subCategoryList.add(subCategory);
        adapter.notifyDataSetChanged();
    }

    public void navigateToManagementPage() {
        Intent intent = new Intent(this, CategoryManagementAdminActivity.class);
        startActivity(intent);
    }
}
