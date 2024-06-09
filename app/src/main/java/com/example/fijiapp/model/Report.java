package com.example.fijiapp.model;

public class Report {
    public String EventId;
    public String Reason;

    public Report() {
    }

    public Report(String id, String reason) {
        this.EventId = id;
        this.Reason = reason;
    }
}
