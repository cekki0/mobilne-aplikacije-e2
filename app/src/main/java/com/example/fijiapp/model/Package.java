package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Package implements Parcelable {
    private String name;
    private String description;
    private int discount;
    private String visible;
    private String available;
    private String category;
    private List<Product> products;
    private List<Service> services;
    private String eventType;
    private int price;
    private List<String> images;
    private String bookingDeadline;
    private String cancellationDeadline;

    public Package(){}
    public Package(String name, String description, int discount, String visible, String available, String category, List<Product> products, List<Service> services, String eventType, int price, List<String> images, String bookingDeadline, String cancellationDeadline) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.visible = visible;
        this.available = available;
        this.category = category;
        this.products = products;
        this.services = services;
        this.eventType = eventType;
        this.price = price;
        this.images = images;
        this.bookingDeadline = bookingDeadline;
        this.cancellationDeadline = cancellationDeadline;
    }

    protected Package(Parcel in) {
        name = in.readString();
        description = in.readString();
        discount = in.readInt();
        visible = in.readString();
        available = in.readString();
        category = in.readString();
        products = in.createTypedArrayList(Product.CREATOR);
        services = in.createTypedArrayList(Service.CREATOR);
        eventType = in.readString();
        price = in.readInt();
        images = in.createStringArrayList();
        bookingDeadline = in.readString();
        cancellationDeadline = in.readString();
    }

    public static final Creator<Package> CREATOR = new Creator<Package>() {
        @Override
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        @Override
        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getBookingDeadline() {
        return bookingDeadline;
    }

    public void setBookingDeadline(String bookingDeadline) {
        this.bookingDeadline = bookingDeadline;
    }

    public String getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(String cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(discount);
        dest.writeString(visible);
        dest.writeString(available);
        dest.writeString(category);
        dest.writeTypedList(products);
        dest.writeTypedList(services);
        dest.writeString(eventType);
        dest.writeInt(price);
        dest.writeStringList(images);
        dest.writeString(bookingDeadline);
        dest.writeString(cancellationDeadline);
    }
}
