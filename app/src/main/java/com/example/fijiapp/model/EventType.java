package com.example.fijiapp.model;

import java.io.Serializable;
import java.util.List;

public class EventType implements Serializable {
    public String name;
    public String description;
    public List<SubCategory> suggestedSubCategories;

    // Constructor
    public EventType(String name, String description, List<SubCategory> suggestedSubCategories) {
        this.name = name;
        this.description = description;
        this.suggestedSubCategories = suggestedSubCategories;
    }
}
