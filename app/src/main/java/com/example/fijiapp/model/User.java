package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    @Exclude
    public String Id;
    public String Email;
    public String FirstName;
    public String LastName;
    public String Address;
    public String PhoneNumber;
    public String ProfileImage;
    public List<WorkingDay> WorkingDays = new ArrayList<>();
    public UserRole Role;
    public Boolean IsActive = true;
    @Exclude
    public Company Company;

    public User(String email, String firstName, String lastName, String address, String phoneNumber, String profileImage, UserRole role, List<WorkingDay> workingDays) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        PhoneNumber = phoneNumber;
        ProfileImage = profileImage;
        Role = role;
        WorkingDays = workingDays;
    }

    public User(String email, String firstName, String lastName, String address, String phoneNumber, String profileImage, UserRole role) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        PhoneNumber = phoneNumber;
        ProfileImage = profileImage;
        Role = role;
    }

    public User(String email, String firstName, String lastName, String address, String phoneNumber, UserRole role) {
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        Address = address;
        PhoneNumber = phoneNumber;
        Role = role;
    }

    public User() {
    }

    protected User(Parcel in) {
        Email = in.readString();
        FirstName = in.readString();
        LastName = in.readString();
        Address = in.readString();
        PhoneNumber = in.readString();
        ProfileImage = in.readString();
        Role = UserRole.valueOf(in.readString());
        WorkingDays = new ArrayList<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            WorkDays workDay = WorkDays.valueOf(in.readString());
            WorkHours workHours = in.readParcelable(WorkHours.class.getClassLoader());
            WorkingDays.add(new WorkingDay(workDay, workHours));
        }
    }

    public String getFullName() {
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
        dest.writeString(Email);
        dest.writeString(FirstName);
        dest.writeString(LastName);
        dest.writeString(Address);
        dest.writeString(PhoneNumber);
        dest.writeString(ProfileImage);
        dest.writeString(Role.toString());
        dest.writeInt(WorkingDays.size());
        for (WorkingDay wd : WorkingDays) {
            dest.writeString(wd.WorkDay.name());
            dest.writeParcelable(wd.WorkHours, flags);
        }
    }
}

