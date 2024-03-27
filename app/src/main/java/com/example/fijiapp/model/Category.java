package com.example.fijiapp.model;

import com.example.fijiapp.model.SubCategory;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    public String Name;
    public String Description;
    public List<SubCategory> SubCategories;

    public Category(String name, String description, List<SubCategory> subCategories) {
        this.Name = name;
        this.Description = description;
        this.SubCategories = subCategories;
    }
}
