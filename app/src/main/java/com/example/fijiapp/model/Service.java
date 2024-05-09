package com.example.fijiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Service implements Parcelable {
    public String category;
    public String subCategory;
    public String name;
    public String description;
    public List<String> gallery;
    public String specifics;
    public int pricePerHour;
    public int totalPrice;
    public int durationHours;
    public String location;
    public int discount;
    public List<String> serviceProviders;
    public List<String> eventTypes;
    public String bookingDeadline;
    public String cancellationDeadline;
    public String acceptanceMode;
    public String available;
    public String visible;
    public String status;

    public Service() {}

    public Service(String category, String subCategory, String name, String description, List<String> gallery, String specifics, int pricePerHour, int totalPrice, int durationHours, String location, int discount, List<String> serviceProviders, List<String> eventTypes, String bookingDeadline, String cancellationDeadline, String acceptanceMode, String available, String visible, String status) {
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.description = description;
        this.gallery = gallery;
        this.specifics = specifics;
        this.pricePerHour = pricePerHour;
        this.totalPrice = totalPrice;
        this.durationHours = durationHours;
        this.location = location;
        this.discount = discount;
        this.serviceProviders = serviceProviders;
        this.eventTypes = eventTypes;
        this.bookingDeadline = bookingDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.acceptanceMode = acceptanceMode;
        this.available = available;
        this.visible = visible;
        this.status = status;
    }

    public Service(String category, String subcategory, String name, String description, List<String> pictureList, String specifics, int pricePerHour, int totalPrice, int durationHours, String location, int discount, String serviceProviders, String eventTypes, String bookingDeadline, String cancellationDeadline, String acceptanceMode, String availableText, String visibleText, String customSubcategory, String status) {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

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

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public List<String> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<String> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
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
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAcceptanceMode() {
        return acceptanceMode;
    }

    public void setAcceptanceMode(String acceptanceMode) {
        this.acceptanceMode = acceptanceMode;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    protected Service(Parcel in) {
        category = in.readString();
        subCategory = in.readString();
        name = in.readString();
        description = in.readString();
        gallery = in.createStringArrayList();
        specifics = in.readString();
        pricePerHour = in.readInt();
        totalPrice = in.readInt();
        durationHours = in.readInt();
        location = in.readString();
        discount = in.readInt();
        serviceProviders = in.createStringArrayList();
        eventTypes = in.createStringArrayList();
        bookingDeadline = in.readString();
        cancellationDeadline = in.readString();
        acceptanceMode = in.readString();
        available = in.readString();
        visible = in.readString();
    }


    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(subCategory);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeStringList(gallery);
        dest.writeString(specifics);
        dest.writeInt(pricePerHour);
        dest.writeInt(totalPrice);
        dest.writeInt(durationHours);
        dest.writeString(location);
        dest.writeInt(discount);
        dest.writeStringList(serviceProviders);
        dest.writeStringList(eventTypes);
        dest.writeString(bookingDeadline);
        dest.writeString(cancellationDeadline);
        dest.writeString(acceptanceMode);
        dest.writeString(available);
        dest.writeString(visible);
    }
}
