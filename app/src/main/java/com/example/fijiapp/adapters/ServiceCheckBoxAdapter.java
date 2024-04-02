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

import java.util.List;

public class ServiceCheckBoxAdapter extends RecyclerView.Adapter<ServiceCheckBoxAdapter.ViewHolder> {

    private Context context;
    private List<Service> serviceList;

    public ServiceCheckBoxAdapter(){}


    @NonNull
    @Override
    public ServiceCheckBoxAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCheckBoxAdapter.ViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.bind(service);
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public ServiceCheckBoxAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }


    public class ViewHolder  extends RecyclerView.ViewHolder{

        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
        public void bind(Service service) {
            checkBox.setText(service.getName());
        }
    }
}
