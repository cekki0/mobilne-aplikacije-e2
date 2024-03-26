package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagementAdminActivity extends AppCompatActivity {

    private CategoryAdapter adapter;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management_admin);

        categories.add(new Category("Electronics", "Electronics category"));
        categories.add(new Category("Clothing", "Clothing category"));
        categories.add(new Category("Books", "Books category"));

        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoryAdapter(categories, this);
        recyclerView.setAdapter(adapter);
    }

    public void onAddCategoryBtnClick(View view) {
        Intent intent = new Intent(this, StaffRegistrationActivity.class);
        startActivity(intent);
    }

}