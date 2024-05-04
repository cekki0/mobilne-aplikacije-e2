package com.example.fijiapp.repository;

import com.example.fijiapp.model.SubCategory;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SubCategoryRepository {
    private CollectionReference subCategoryRef;

    public SubCategoryRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        subCategoryRef = database.collection("subcategories");
    }

    public Task<Void> addSubCategory(SubCategory subCategory) {
        return subCategoryRef.document(subCategory.Name).set(subCategory);
    }

    public Task<Void> updateSubCategory(SubCategory subCategory) {
        return subCategoryRef.document(subCategory.Name).set(subCategory);
    }

    public Task<Void> deleteSubCategory(String subCategoryName) {
        return subCategoryRef.document(subCategoryName).delete();
    }

    public Task<QuerySnapshot> getSubCategoryByName(String subCategoryName) {
        return subCategoryRef.whereEqualTo("Name", subCategoryName).get();
    }

    public Task<QuerySnapshot> getAllSubCategories() {
        return subCategoryRef.get();
    }
}
