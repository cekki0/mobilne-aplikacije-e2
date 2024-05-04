package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.repository.CategoryRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategoryService {
    private static final String TAG = "CategoryService";
    private CategoryRepository categoryRepository;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }

    public void addCategory(Category category) {
        categoryRepository.addCategory(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(String categoryName) {
        categoryRepository.deleteCategory(categoryName);
    }

    public Task<List<Category>> getAllCategories() {
        return categoryRepository.getAllCategories().continueWith(task -> {
            if (task.isSuccessful()) {
                List<Category> categories = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    Category category = document.toObject(Category.class);
                    categories.add(category);
                }
                return categories;
            } else {
                throw task.getException();
            }
        });
    }
}
