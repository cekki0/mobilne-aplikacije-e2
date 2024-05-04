package com.example.fijiapp.service;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.repository.CategoryRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository;
    private SubCategoryService subCategoryService;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
        subCategoryService = new SubCategoryService();
    }

    public void addCategory(Category category) {
        categoryRepository.addCategory(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(String categoryId) {
        categoryRepository.deleteCategory(categoryId);
    }

    public Task<List<Category>> getAllCategories() {
        return categoryRepository.getAllCategories().continueWithTask(task -> {
            if (task.isSuccessful()) {
                List<Task<Void>> subCategoryTasks = new ArrayList<>();
                List<Category> categories = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    Category category = document.toObject(Category.class);
                    if (category != null && !category.SubCategoryIds.isEmpty()) {
                        category.Id = document.getId();
                        List<SubCategory> subCategories = new ArrayList<>();
                        for (String subCategoryId : category.SubCategoryIds) {
                            Task<SubCategory> subCategoryTask = subCategoryService.getSubCategoryById(subCategoryId);
                            subCategoryTasks.add(subCategoryTask.onSuccessTask(subCategory -> {
                                if (subCategory != null) {
                                    subCategories.add(subCategory);
                                }
                                return null;
                            }));
                        }
                        category.SubCategories = subCategories;
                    }
                    categories.add(category);
                }
                return Tasks.whenAll(subCategoryTasks).continueWith(ignored -> categories);
            } else {
                throw task.getException();
            }
        });
    }

}
