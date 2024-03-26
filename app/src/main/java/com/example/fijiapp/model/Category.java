// Category.java
package com.example.fijiapp.model;

import java.util.List;

public class Category {
    public String Name;
    public String Description;
    public List<SubCategory> SubCategories;

    public Category(String name, String description) {
        this.Name = name;
        this.Description = description;
    }
}
