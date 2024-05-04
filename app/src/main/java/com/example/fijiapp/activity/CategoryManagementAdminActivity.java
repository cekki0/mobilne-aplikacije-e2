package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.service.CategoryService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class CategoryManagementAdminActivity extends AppCompatActivity {

    private CategoryAdapter adapter;
    private List<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management_admin);

        fetchAndInitializeRecyclerView();
    }

    private void fetchAndInitializeRecyclerView() {
        CategoryService categoryService = new CategoryService();
        categoryService.getAllCategories()
                .addOnCompleteListener(new OnCompleteListener<List<Category>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Category>> task) {
                        if (task.isSuccessful()) {
                            categories = task.getResult();
                            initializeRecyclerView();
                        } else {
                            Toast.makeText(CategoryManagementAdminActivity.this,
                                    "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoryAdapter(categories, this);
        recyclerView.setAdapter(adapter);
    }

    public void onAddCategoryBtnClick(View view) {
        Intent intent = new Intent(this, CategoryAddActivity.class);
        startActivity(intent);
    }
}
