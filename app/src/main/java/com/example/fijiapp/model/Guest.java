package com.example.fijiapp.model;

import java.time.LocalTime;
import java.util.Date;

public class Guest {
    public String FirstName;
    public String LastName;
    public Integer Age;
    public Boolean IsInvited;
    public Boolean HasAcceptedInvitation;
    public FoodCriteria FoodCriteria;

    public Guest(){}

    public Guest(String firstName, String lastName, Integer age, Boolean isInvited, Boolean hasAcceptedInvitation, com.example.fijiapp.model.FoodCriteria foodCriteria) {
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        IsInvited = isInvited;
        HasAcceptedInvitation = hasAcceptedInvitation;
        FoodCriteria = foodCriteria;
    }
}
