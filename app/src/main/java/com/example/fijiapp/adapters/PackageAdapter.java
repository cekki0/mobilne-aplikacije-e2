package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.UpdatePackageActivity;
import com.example.fijiapp.UpdateProductActivity;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private List<Package> packages = new ArrayList<>();
    private Context context;



    public PackageAdapter(){}
    public PackageAdapter(List<Package> packages, Context context) {
        this.packages = packages;
        this.context = context;
    }


    public void filterList(List<Package> filteredList) {
        packages = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_card,parent,false);
        return new PackageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.ViewHolder holder, int position) {
        Package pack = packages.get(position);
        holder.nameTextView.setText("Name: " + pack.getName());
        holder.descriptionTextView.setText("Description: " + pack.getDescription());
        holder.discountTextView.setText("Discount: " + pack.getDiscount());
        holder.visibleTextView.setText("Visible: " + pack.getVisible());
        holder.availableTextView.setText("Available: " + pack.getAvailable());
        holder.categoryTextView.setText("Category: " + pack.getCategory());
        holder.eventTypeTextView.setText("Event Type: " + pack.getEventType());
        holder.priceTextView.setText("Price: " + pack.getPrice());
        holder.bookingDeadlineTextView.setText("Booking Deadline: " + pack.getBookingDeadline());
        holder.cancellationDeadlineTextView.setText("Cancellation Deadline: " + pack.getCancellationDeadline());
        holder.imagesTextView.setText(("Galerry: " + pack.getImages()));


        List<Product> productList = pack.getProducts();
        StringBuilder productText = new StringBuilder("Products: ");
        for (Product product : productList) {
            productText.append(product.Title).append(", ");
        }
        holder.productsTextView.setText(productText.toString());


        List<Service> serviceList = pack.getServices();//svi servisi
        StringBuilder serviceText = new StringBuilder();
        for (Service service : serviceList) {
            serviceText.append(service.getName()).append(", ");
        }
        holder.servicesTextView.setText(serviceText.toString());

        //Koji nisu u paketu
        List<Service> serviceNotInPackageList =new ArrayList<>();
        List<Service> notInPackageList = new ArrayList<>();
        for(Service service : serviceList){
            if(!serviceList.contains(service.getName())){
                notInPackageList.add(service);
            }
        }







        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Package selectedPackage = packages.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, UpdatePackageActivity.class);
                intent.putExtra("package", selectedPackage);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView discountTextView;
        private TextView visibleTextView;
        private TextView availableTextView;
        private TextView categoryTextView;
        private TextView eventTypeTextView;
        private TextView priceTextView;
        private TextView bookingDeadlineTextView;
        private TextView cancellationDeadlineTextView;
        private TextView imagesTextView;
        private TextView productsTextView;
        private TextView servicesTextView;
        private ImageButton editButton;
        private ImageButton deleteButton;
        private TextView servicesEditText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            discountTextView = itemView.findViewById(R.id.discountTextView);
            visibleTextView = itemView.findViewById(R.id.visibleTextView);
            availableTextView = itemView.findViewById(R.id.availableTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            eventTypeTextView = itemView.findViewById(R.id.eventTypeTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            bookingDeadlineTextView = itemView.findViewById(R.id.bookingDeadlineTextView);
            cancellationDeadlineTextView = itemView.findViewById(R.id.cancellationDeadlineTextView);
            imagesTextView = itemView.findViewById(R.id.imagesTextView);
            productsTextView = itemView.findViewById(R.id.productsTextView);
            servicesTextView = itemView.findViewById(R.id.servicesTextView);
            editButton = itemView.findViewById(R.id.editButton);
            servicesEditText = itemView.findViewById(R.id.servicesTextView);
        }


    }




}
