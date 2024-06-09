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
import com.example.fijiapp.activity.StaffDetailActivity;
import com.example.fijiapp.activity.event.EventTypeEditActivity;
import com.example.fijiapp.activity.register.RegistrationRequestsManagement;
import com.example.fijiapp.model.User;
import com.example.fijiapp.service.UserService;

import java.util.List;

public class RegistrationRequestAdapter extends RecyclerView.Adapter<RegistrationRequestAdapter.ViewHolder> {
    private List<User> users;
    private Context context;
    private UserService userService = new UserService();


    public RegistrationRequestAdapter(List<User> dataSet, Context context) {
        this.users = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_request_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.emailTextView.setText("Email: " + user.Email);
        holder.nameTextView.setText("Name: " + user.getFullName());
        holder.addressTextView.setText("Address: " + user.Address);
        holder.phoneTextView.setText("Phone: " + user.PhoneNumber);
        if(user.Company!=null)
        {
            holder.companyEmailTextView.setText("Company Email: " + user.Company.Email);
            holder.companyNameTextView.setText("Company Name: " + user.Company.Name);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StaffDetailActivity.class);
                intent.putExtra(StaffDetailActivity.EXTRA_USER, user);
                context.startActivity(intent);
            }
        });
        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.IsActive=true;
                userService.updateUser(user);
                Intent intent = new Intent(context, RegistrationRequestsManagement.class);
                context.startActivity(intent);
            }
        });

        holder.denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.deleteUser(user);
                Intent intent = new Intent(context, RegistrationRequestsManagement.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void filterList(List<User> filteredList) {
        users = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        TextView companyEmailTextView;
        TextView nameTextView;
        TextView companyNameTextView;
        TextView addressTextView;
        TextView phoneTextView;
        Button approveButton;
        Button denyButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            approveButton = itemView.findViewById(R.id.approveButton);
            denyButton = itemView.findViewById(R.id.denyButton);
            companyEmailTextView = itemView.findViewById(R.id.companyEmailTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
        }
    }
}