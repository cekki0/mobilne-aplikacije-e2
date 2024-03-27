package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;

import java.util.ArrayList;
import java.util.List;

public class CategoryManagementAdminActivity extends AppCompatActivity {

    private CategoryAdapter adapter;
    private List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management_admin);

        categories  = createCategoriesWithSubcategories();

        RecyclerView recyclerView = findViewById(R.id.categoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CategoryAdapter(categories, this);
        recyclerView.setAdapter(adapter);
    }

    public void onAddCategoryBtnClick(View view) {
        Intent intent = new Intent(this, CategoryAddActivity.class);
        startActivity(intent);
    }

    public static List<Category> createCategoriesWithSubcategories() {
        List<Category> categories = new ArrayList<>();

        List<SubCategory> electronicsSubCategories = new ArrayList<>();
        electronicsSubCategories.add(new SubCategory("Smartphones", "Smartphones desc", SubCategoryType.PRODUCT));
        electronicsSubCategories.add(new SubCategory("Laptops", "Laptops desc", SubCategoryType.PRODUCT));
        electronicsSubCategories.add(new SubCategory("TVs", "TVs desc", SubCategoryType.PRODUCT));


        Category electronicsCategory = new Category("Electronics", "Electronics category desc", electronicsSubCategories);
        categories.add(electronicsCategory);


        List<SubCategory> clothingSubCategories = new ArrayList<>();
        clothingSubCategories.add(new SubCategory("T-Shirts", "T-Shirts desc", SubCategoryType.PRODUCT));
        clothingSubCategories.add(new SubCategory("Jeans", "Jeans desc", SubCategoryType.PRODUCT));
        clothingSubCategories.add(new SubCategory("Dresses", "Dresses desc", SubCategoryType.PRODUCT));
        Category clothingCategory = new Category("Clothing", "Clothing category", clothingSubCategories);
        categories.add(clothingCategory);

        List<SubCategory> booksSubCategories = new ArrayList<>();
        booksSubCategories.add(new SubCategory("Fiction", "Fiction desc", SubCategoryType.PRODUCT));
        booksSubCategories.add(new SubCategory("Non-Fiction", "Non-Fiction desc", SubCategoryType.PRODUCT));
        booksSubCategories.add(new SubCategory("Biographies", "Biographies desc", SubCategoryType.PRODUCT));
        Category booksCategory = new Category("Books", "Books category", booksSubCategories);
        categories.add(booksCategory);

        return categories;
    }

}