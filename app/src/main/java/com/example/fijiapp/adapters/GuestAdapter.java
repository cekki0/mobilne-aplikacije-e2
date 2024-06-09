package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.GuestActivity;
import com.example.fijiapp.activity.GuestDialog;
import com.example.fijiapp.model.Guest;

import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.ViewHolder> {
    private List<Guest> guests;
    private Context context;

    public GuestAdapter(List<Guest> dataSet, Context context) {
        this.guests = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public GuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_card, parent, false);
        return new GuestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestAdapter.ViewHolder holder, int position) {
        Guest guest = guests.get(position);
        holder.firstNameView.setText("First name: " + guest.FirstName);
        holder.lastNameView.setText("Last name: " + guest.LastName);
        holder.ageView.setText("Age: " + guest.Age);
        holder.isInvitedView.setText("Invited: " + guest.IsInvited);
        holder.hasAcceptedInvitationView.setText("Accepted: " + guest.HasAcceptedInvitation);
        holder.foodCriteriaView.setText("Food criteria: " + guest.FoodCriteria);
    }

    @Override
    public int getItemCount() {
        return guests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameView;
        TextView lastNameView;
        TextView ageView;
        TextView isInvitedView;
        TextView hasAcceptedInvitationView;
        TextView foodCriteriaView;
        Button addGuestButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameView = itemView.findViewById(R.id.firstNameView);
            lastNameView = itemView.findViewById(R.id.lastNameView);
            ageView = itemView.findViewById(R.id.ageView);
            isInvitedView = itemView.findViewById(R.id.isInvitedView);
            hasAcceptedInvitationView = itemView.findViewById(R.id.hasAcceptedInvitationView);
            foodCriteriaView = itemView.findViewById(R.id.foodCriteriaView);
            addGuestButton = itemView.findViewById(R.id.addGuestButton);
        }
    }
}
