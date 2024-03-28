package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Product implements Parcelable {


    public enum EventType{SVADBA, RODJENDAN, KRSTENJE, RODJENJE}

    public int Id;
    public String Category;
    public String SubCategory;
    public String Title;
    public String Description;
    public int Price;
    public int Discount;
    public int NewPrice;
    public ArrayList<String> PictureList;
    public String Event;
    public String Available;
    public String Visible;

    public Product(String category, String subCategory, String title, String description, int price, int discount, int newPrice, ArrayList<String> pictureList, String event, String available, String visible) {
        Category = category;
        SubCategory = subCategory;
        Title = title;
        Description = description;
        Price = price;
        Discount = discount;
        NewPrice = newPrice;
        PictureList = pictureList;
        Event = event;
        Available = available;
        Visible = visible;
    }

    protected Product(Parcel in) {
        Category = in.readString();
        SubCategory = in.readString();
        Title = in.readString();
        Description = in.readString();
        Price = in.readInt();
        Discount = in.readInt();
        NewPrice = in.readInt();
        PictureList = in.createStringArrayList();
        Available = in.readString();
        Visible = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(Category);
        dest.writeString(SubCategory);
        dest.writeString(Title);
        dest.writeString(Description);
        dest.writeInt(Price);
        dest.writeInt(Discount);
        dest.writeInt(NewPrice);
        dest.writeStringList(PictureList);
        dest.writeString(String.valueOf(Event));
        dest.writeString(Available);
        dest.writeString(Visible);
    }
}