package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.UpdateServiceActivity;
import com.example.fijiapp.model.Service;
import com.squareup.picasso.Picasso;

import android.view.View;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

   private List<Service> services;
   private Context context;
    private OnItemClickListener listener;
    public ServiceAdapter(List<Service> services, Context context, OnItemClickListener listener) {
        this.services = services;
        this.context = context;
        this.listener = listener;
    }
    public void filterList(List<Service> filteredList){
        services = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_card,parent,false);
    return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service = services.get(position);
        holder.categoryTextView.setText("Category: " + service.getCategory());
        holder.subCategoryTextView.setText("Subcategory: " + service.getSubCategory());
        holder.nameTextView.setText("Name: " + service.getName());
        holder.descriptionTextView.setText("Description: " + service.getDescription());
//        holder.galleryTextView.setText("Gallery: " + service.getGallery());
        holder.specificsTextView.setText("Specifics: " + service.getSpecifics());
        holder.pricePerHourTextView.setText("Price per Hour: " + service.getPricePerHour());
        holder.totalPriceTextView.setText("Total Price: " + service.getTotalPrice());
        holder.durationHoursTextView.setText("Duration Hours: " + service.getDurationHours());
        holder.locationTextView.setText("Location: " + service.getLocation());
        holder.discountTextView.setText("Discount: " + service.getDiscount());
        holder.serviceProvidersTextView.setText("Service Providers: " + service.getServiceProviders());
        holder.eventTypesTextView.setText("Event Types: " + service.getEventTypes());
        holder.bookingDeadlineTextView.setText("Booking Deadline: " + service.getBookingDeadline());
        holder.cancellationDeadlineTextView.setText("Cancellation Deadline: " + service.getCancellationDeadline());
        holder.acceptanceModeTextView.setText("Acceptance Mode: " + service.getAcceptanceMode());
        holder.availableTextView.setText("Available: " + service.getAvailable());
        holder.visibleTextView.setText("Visible: " + service.getVisible());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(services.get(position));
                    }
                }
            }
        });





        List<String> pictureList = service.getGallery();
        if (pictureList != null && !pictureList.isEmpty()) {

            holder.imageViewContainer.removeAllViews();


            for (String imageUrl : pictureList) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                imageView.setLayoutParams(layoutParams);


                Picasso.get().load(imageUrl)
                        .resize(300, 300)
                        .centerCrop()
                        .into(imageView);


                holder.imageViewContainer.addView(imageView);
            }
        } else {
            holder.imageViewContainer.setVisibility(View.GONE);
        }



        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Service selectedService = services.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, UpdateServiceActivity.class);
                intent.putExtra("service", selectedService);
                context.startActivity(intent);
            }
        });

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }




    @Override
    public int getItemCount() {
        return services.size();
    }


    public static class  ViewHolder extends  RecyclerView.ViewHolder{

        TextView categoryTextView;
        TextView subCategoryTextView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView galleryTextView;
        TextView specificsTextView;
        TextView pricePerHourTextView;
        TextView totalPriceTextView;
        TextView durationHoursTextView;
        TextView locationTextView;
        TextView discountTextView;
        TextView serviceProvidersTextView;
        TextView eventTypesTextView;
        TextView bookingDeadlineTextView;
        TextView cancellationDeadlineTextView;
        TextView acceptanceModeTextView;
        TextView availableTextView;
        TextView visibleTextView;
        ImageButton editButton;
        ImageButton deleteButton;
        LinearLayout imageViewContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            subCategoryTextView = itemView.findViewById(R.id.subCategoryTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            //galleryTextView = itemView.findViewById(R.id.galleryTextView);
            specificsTextView = itemView.findViewById(R.id.specificsTextView);
            pricePerHourTextView = itemView.findViewById(R.id.pricePerHourTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);
            durationHoursTextView = itemView.findViewById(R.id.durationHoursTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            discountTextView = itemView.findViewById(R.id.discountTextView);
            serviceProvidersTextView = itemView.findViewById(R.id.serviceProvidersTextView);
            eventTypesTextView = itemView.findViewById(R.id.eventTypesTextView);
            bookingDeadlineTextView = itemView.findViewById(R.id.bookingDeadlineTextView);
            cancellationDeadlineTextView = itemView.findViewById(R.id.cancellationDeadlineTextView);
            acceptanceModeTextView = itemView.findViewById(R.id.acceptanceModeTextView);
            availableTextView = itemView.findViewById(R.id.availableTextView);
            visibleTextView = itemView.findViewById(R.id.visibleTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            imageViewContainer = itemView.findViewById(R.id.imageViewContainer);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Service service);
    }
}
