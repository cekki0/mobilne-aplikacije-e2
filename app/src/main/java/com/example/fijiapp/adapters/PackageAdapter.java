package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.UpdatePackageActivity;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private List<Package> packages = new ArrayList<>();
    private Context context;
    private FirebaseFirestore db;

    public PackageAdapter(List<Package> packages, Context context) {
        this.packages = packages;
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_card, parent, false);
        return new ViewHolder(view);
    }

    public void filterList(List<Package> filteredList) {
        packages = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Package pack = packages.get(position);

        holder.nameTextView.setText("Name: " + pack.getName());
        holder.descriptionTextView.setText("Description: " + pack.getDescription());
        holder.discountTextView.setText("Discount: " + pack.getDiscount());
        holder.visibleTextView.setText("Visible: " + pack.getVisible());
        holder.availableTextView.setText("Available: " + pack.getAvailable());
        holder.categoryTextView.setText("Category: " + pack.getCategory());
        holder.eventTypeTextView.setText("Event Type: " + pack.eventType);
        holder.priceTextView.setText("Price: " + pack.getPrice());
        holder.bookingDeadlineTextView.setText("Booking Deadline: " + pack.bookingDeadline);
        holder.cancellationDeadlineTextView.setText("Cancellation Deadline: " + pack.cancellationDeadline);
        holder.imagesTextView.setText(("Gallery: " + pack.getImages()));

        db.collection("packages")
                .whereEqualTo("name", pack.getName())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            List<Product> productList = documentSnapshot.toObject(Package.class).getProducts();
                            if (productList != null) {
                                StringBuilder productText = new StringBuilder("Products: ");
                                for (Product product : productList) {
                                    productText.append(product.Title).append(", ");
                                }
                                holder.productsTextView.setText(productText.toString());
                            }
                        }
                    }
                });


        db.collection("packages")
                .whereEqualTo("name", pack.getName())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                            List<Service> serviceList = documentSnapshot.toObject(Package.class).getServices();
                            if (serviceList != null) {
                                StringBuilder serviceText = new StringBuilder("Services: ");
                                for (Service service : serviceList) {
                                    serviceText.append(service.getName()).append(", ");
                                }
                                holder.servicesTextView.setText(serviceText.toString());
                            }
                        }
                    }
                });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Package selectedPackage = packages.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, UpdatePackageActivity.class);
                intent.putExtra("package", selectedPackage);
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onPackageDeleteClick(pack);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnPackageDeleteClickListener {
        void onPackageDeleteClick(Package pack);
    }

    private OnPackageDeleteClickListener deleteClickListener;

    public void setOnPackageDeleteClickListener(OnPackageDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }
}