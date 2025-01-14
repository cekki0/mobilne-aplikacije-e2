package com.example.fijiapp.model;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class Company {
    public String Email;
    public String Name;
    public String Address;
    public String PhoneNumber;
    public String About;
    public String[] CompanyPictures;
    public String OwnerEmail;
    public List<WorkingDay> WorkingDays = new ArrayList<>();
    public List<String> CategoryIds = new ArrayList<>();
    public List<String> EventTypeIds = new ArrayList<>();
    @Exclude
    public List<Category> Categories = new ArrayList<Category>();
    @Exclude
    public List<EventType> EventTypes = new ArrayList<EventType>();

    public Company(String email, String name, String address, String phoneNumber, String about, List<WorkingDay> workingDays, List<String> categoryIds, List<String> eventTypeIds) {
        Email = email;
        Name = name;
        Address = address;
        PhoneNumber = phoneNumber;
        About = about;
        WorkingDays = workingDays;
        CategoryIds = categoryIds;
        EventTypeIds = eventTypeIds;
    }

    public Company() {
    }
}
