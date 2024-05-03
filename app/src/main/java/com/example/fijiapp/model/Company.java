package com.example.fijiapp.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
    public String Email;
    public String Name;
    public String Address;
    public String PhoneNumber;
    public String About;
    public String[] CompanyPictures;
    public String UserId;
    public List<WorkingDay> WorkingDays = new ArrayList<>();
    public List<Event> Events = new ArrayList<Event>();
    public List<Category> Category = new ArrayList<Category>();

    public Company(String email, String name, String address, String phoneNumber, String about,List<WorkingDay> workingDays) {
        Email = email;
        Name = name;
        Address = address;
        PhoneNumber = phoneNumber;
        About = about;
        WorkingDays = workingDays;
    }
}
