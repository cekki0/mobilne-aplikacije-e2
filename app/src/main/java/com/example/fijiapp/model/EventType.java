package com.example.fijiapp.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.List;

public class EventType implements Serializable {
    @Exclude
    public String Id;
    public String Name;
    public String Description;
    public List<String> SuggestedSubCategoryIds;
    @Exclude
    public List<SubCategory> SuggestedSubCategories;
    public Boolean isActive = true;

    public EventType() {}

    public EventType(String name, String description, List<String> suggestedSubCategoryIds) {
        this.Name = name;
        this.Description = description;
        this.SuggestedSubCategoryIds = suggestedSubCategoryIds;
    }
}
