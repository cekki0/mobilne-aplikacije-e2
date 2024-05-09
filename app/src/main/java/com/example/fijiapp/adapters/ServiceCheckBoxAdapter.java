package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Service;
import  android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;
public class ServiceCheckBoxAdapter extends RecyclerView.Adapter<ServiceCheckBoxAdapter.ViewHolder> {

    private Context context;
    private List<Service> services;
    private List<Service> selectedServices = new ArrayList<>();

    public ServiceCheckBoxAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = services.get(position);
        holder.checkBox.setText(service.getName());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedServices.add(service);
            } else {
                selectedServices.remove(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public List<Service> getSelectedServices() {
        return selectedServices;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}

