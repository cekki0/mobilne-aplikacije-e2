// SubCategory.java
package com.example.fijiapp.model;

public class SubCategory {
    public Category Category;
    public String Name;
    public String Description;
    public SubCategoryType Type;

    public SubCategory(Category category, String name, String description, SubCategoryType type) {
        this.Category = category;
        this.Name = name;
        this.Description = description;
        this.Type = type;
    }
}
