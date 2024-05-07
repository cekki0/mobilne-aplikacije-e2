package com.example.fijiapp.model;

public class WorkingDay {
    public WorkDays WorkDay;
    public WorkHours WorkHours;

    public WorkingDay(WorkDays workDay, WorkHours workHours) {
        this.WorkDay = workDay;
        this.WorkHours = workHours;
    }

    public WorkingDay() {
    }
}
