package com.example.fijiapp.model;

import com.google.firebase.firestore.PropertyName;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Event {
    public String EventName;
    public Date Date;
    public LocalTime StartTime;
    public LocalTime EndTime;
    public EventTypes Type;

    public Event() {
        // Required empty constructor for Firestore deserialization
    }

    public Event(String eventName, Date date, LocalTime startTime, LocalTime endTime, EventTypes type) {
        this.EventName = eventName;
        this.Date = date;
        this.StartTime = startTime;
        this.EndTime = endTime;
        this.Type = type;
    }

    @PropertyName("StartTime")
    public String getStartTimeString() {
        if (this.StartTime != null) {
            return this.StartTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        } else {
            return null;
        }
    }

    @PropertyName("StartTime")
    public void setStartTimeString(String timeString) {
        if (timeString != null) {
            this.StartTime = LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME);
        } else {
            this.StartTime = null;
        }
    }

    @PropertyName("EndTime")
    public String getEndTimeString() {
        if (this.EndTime != null) {
            return this.EndTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        } else {
            return null;
        }
    }

    @PropertyName("EndTime")
    public void setEndTimeString(String timeString) {
        if (timeString != null) {
            this.EndTime = LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME);
        } else {
            this.EndTime = null;
        }
    }
}