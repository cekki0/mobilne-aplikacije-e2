package com.example.fijiapp.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class SubCategory implements Serializable {
    @Exclude
    public String Id;
    public String Name;
    public String Description;
    public SubCategoryType Type;

    public SubCategory() {
        // Required empty constructor for Firestore deserialization
    }
    public SubCategory(String name, String description, SubCategoryType type) {
        this.Name = name;
        this.Description = description;
        this.Type = type;
    }

    public SubCategory() {

    }
}
