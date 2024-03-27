package com.example.fijiapp.model;

import java.time.LocalTime;
import java.util.Date;

public class Event {
    public String EventName;
    public Date Date;
    public LocalTime StartTime;
    public LocalTime EndTime;
    public EventType Type;

    public Event(String eventName, Date date, LocalTime startTime, LocalTime endTime, EventType type) {
        this.EventName = eventName;
        this.Date = date;
        this.StartTime = startTime;
        this.EndTime = endTime;
        this.Type = type;
    }


}