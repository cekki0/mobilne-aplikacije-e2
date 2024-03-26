package com.example.fijiapp.model;

import java.io.Serializable;

public class SubCategory implements Serializable {
    public String Name;
    public String Description;
    public SubCategoryType Type;

    public SubCategory(String name, String description, SubCategoryType type) {
        this.Name = name;
        this.Description = description;
        this.Type = type;
    }
}
