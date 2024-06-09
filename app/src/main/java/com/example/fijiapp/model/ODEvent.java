package com.example.fijiapp.model;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ODEvent {
    public String EventName;
    public String Description;
    public Integer MaxParticipants;
    public Privacy Privacy;
    public String Location;
    public java.util.Date Date;
    public String Type;
    public List<String> Services = null;

    public ODEvent() {
    }

    public ODEvent(String eventName, String description, Integer maxParticipants, com.example.fijiapp.model.Privacy privacy, String location, java.util.Date date, String type, List<String> services) {
        EventName = eventName;
        Description = description;
        MaxParticipants = maxParticipants;
        Privacy = privacy;
        Location = location;
        Date = date;
        Type = type;
        Services = services;
    }
}
