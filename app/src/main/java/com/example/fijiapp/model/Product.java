package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import java.io.Serializable;
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
    public String Status;
    public ImageButton editButton;
public  Product(){}
    public Product(String category, String subCategory, String title, String description, int price, int discount, int newPrice, ArrayList<String> pictureList, String event, String available, String visible, String status) {
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
        Status = status;
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
        Event =  in.readString();
        Status = in.readString();
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

        dest.writeString(Available);
        dest.writeString(Visible);
        dest.writeString(Event);
        dest.writeString(Status);

    }

//    public String getTitle() {
//        return Title;
//    }
//
//    public String getCategory() {
//        return Category;
//    }
//
//    // Getter method for Price
//    public int getPrice() {
//        return Price;
//    }
//
//    // Getter method for SubCategory
//    public String getSubCategory() {
//        return SubCategory;
//    }
}
