package com.example.fijiapp.model;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {
    public String Name;
    public String Description;
    public List<String> SubCategoryNames;
    public List<SubCategory> SubCategories = new ArrayList<>();

    public Category(String name, String description, List<String> subCategoryNames) {
        this.Name = name;
        this.Description = description;
        this.SubCategoryNames = subCategoryNames;
    }
}
