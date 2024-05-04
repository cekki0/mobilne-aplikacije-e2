package com.example.fijiapp.service;

import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.repository.SubCategoryRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryService {
    private SubCategoryRepository subCategoryRepository;

    public SubCategoryService() {
        subCategoryRepository = new SubCategoryRepository();
    }

    public Task<Void> addSubCategory(SubCategory subCategory) {
        return subCategoryRepository.addSubCategory(subCategory);
    }

    public Task<Void> updateSubCategory(SubCategory subCategory) {
        return subCategoryRepository.updateSubCategory(subCategory);
    }

    public Task<Void> deleteSubCategory(String subCategoryName) {
        return subCategoryRepository.deleteSubCategory(subCategoryName);
    }

    public Task<List<SubCategory>> getAllSubCategories() {
        return subCategoryRepository.getAllSubCategories().continueWith(task -> {
            if (task.isSuccessful()) {
                List<SubCategory> subCategories = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    SubCategory subCategory = document.toObject(SubCategory.class);
                    subCategories.add(subCategory);
                }
                return subCategories;
            } else {
                throw task.getException();
            }
        });
    }
}
