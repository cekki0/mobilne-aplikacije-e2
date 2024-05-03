package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {
    public String Email;
    public String FirstName;
    public String LastName;
    public String Address;
    public String PhoneNumber;
    public String ProfileImage;
//    public Map<WorkDays, WorkHours> WorkHours = new HashMap<>();
    public UserRole Role;
    public Boolean IsActive = false;

    public User(String email,String firstName, String lastName, String address, String phoneNumber, String profileImage, UserRole role) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        PhoneNumber = phoneNumber;
        ProfileImage = profileImage;
        Role = role;
//        WorkHours.put(WorkDays.MON, new WorkHours(LocalTime.parse("08:00"), LocalTime.parse("16:00")));
//        WorkHours.put(WorkDays.TUE, new WorkHours(LocalTime.parse("08:00"), LocalTime.parse("16:00")));
//        WorkHours.put(WorkDays.WED, new WorkHours(LocalTime.parse("08:00"), LocalTime.parse("16:00")));
//        WorkHours.put(WorkDays.THU, new WorkHours(LocalTime.parse("08:00"), LocalTime.parse("16:00")));
//        WorkHours.put(WorkDays.FRI, new WorkHours(LocalTime.parse("08:00"), LocalTime.parse("16:00")));
    }

    public User(String firstName, String lastName, String address, String phoneNumber, UserRole role) {
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        PhoneNumber = phoneNumber;
        Role= role;
    }

    public User() {}

    protected User(Parcel in) {
//        FirstName = in.readString();
//        LastName = in.readString();
//        Address = in.readString();
//        PhoneNumber = in.readString();
//        ProfileImage = in.readString();
//        Role = UserRole.valueOf(in.readString());
//        WorkHours = new HashMap<>();
//        int size = in.readInt();
//        for (int i = 0; i < size; i++) {
//            WorkDays workDay = WorkDays.valueOf(in.readString());
//            WorkHours workHours = in.readParcelable(WorkHours.class.getClassLoader());
//            WorkHours.put(workDay, workHours);
//        }
    }

    public String getFullName(){
        return FirstName + " " + LastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeString(FirstName);
//        dest.writeString(LastName);
//        dest.writeString(Address);
//        dest.writeString(PhoneNumber);
//        dest.writeString(ProfileImage);
//        dest.writeString(Role.toString());
//        dest.writeInt(WorkHours.size());
//        for (Map.Entry<WorkDays, WorkHours> entry : WorkHours.entrySet()) {
//            dest.writeString(entry.getKey().name());
//            dest.writeParcelable(entry.getValue(), flags);
//        }
    }
}

