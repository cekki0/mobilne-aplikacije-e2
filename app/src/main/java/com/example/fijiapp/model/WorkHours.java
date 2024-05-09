package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WorkHours implements Parcelable, Serializable {
    public LocalTime StartTime;
    public LocalTime EndTime;

    public int stability; // Ja nemam pojma sta je ovo stvarno, u bazi iz nekog razloga postoji, tkd moram i vamo ubaciti sta sad

    public WorkHours(LocalTime startTime, LocalTime endTime) {
        Validate(startTime, endTime);
        StartTime = startTime;
        EndTime = endTime;
    }

    public WorkHours(String startTime, String endTime) {
        Validate(LocalTime.parse(startTime), LocalTime.parse(endTime));
        StartTime = LocalTime.parse(startTime);
        EndTime = LocalTime.parse(endTime);
    }

    public WorkHours() {
    }

    private void Validate(LocalTime startTime, LocalTime endTime) {
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }

    protected WorkHours(Parcel in) {
        StartTime = LocalTime.parse(in.readString());
        EndTime = LocalTime.parse(in.readString());
    }

    public static final Creator<WorkHours> CREATOR = new Creator<WorkHours>() {
        @Override
        public WorkHours createFromParcel(Parcel in) {
            return new WorkHours(in);
        }

        @Override
        public WorkHours[] newArray(int size) {
            return new WorkHours[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(StartTime.toString());
        dest.writeString(EndTime.toString());
    }

    @Override
    public int describeContents() {
        return 0;
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
