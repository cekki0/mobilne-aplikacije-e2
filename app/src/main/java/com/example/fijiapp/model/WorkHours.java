package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalTime;

public class WorkHours implements Parcelable {
    public LocalTime StartTime;
    public LocalTime EndTime;

    public WorkHours(LocalTime startTime, LocalTime endTime) {
        StartTime = startTime;
        EndTime = endTime;
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
}
