package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;

    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.titleTextView.setText("Title: " + product.Title);
        holder.descriptionTextView.setText("Description: " + product.Description);
        holder.priceTextView.setText("Price: " + product.Price);
        holder.discountTextView.setText("Discount: " + product.Discount);
        holder.newPriceTextView.setText("New Price: " + product.NewPrice);
        holder.categoryTextView.setText("Category: " + product.Category);
        holder.subcategoryTextView.setText("Subcategory: " + product.SubCategory);
        holder.eventTextView.setText("Event: " + product.Event);
        holder.availableTextView.setText("Available: " + product.Available);
        holder.visibleTextView.setText("Visible: " + product.Visible);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("PRODUCT_ID", product.Id); // Salje Id i na osnovu njega prikazuje detalje na drugoj stranici
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void filterList(List<Product> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        TextView discountTextView;
        TextView newPriceTextView;
        TextView categoryTextView;
        TextView subcategoryTextView;
        TextView pictureListTextView;
        TextView eventTextView;
        TextView availableTextView;
        TextView visibleTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            discountTextView = itemView.findViewById(R.id.discountTextView);
            newPriceTextView = itemView.findViewById(R.id.newPriceTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            subcategoryTextView = itemView.findViewById(R.id.subcategoryTextView);
            pictureListTextView = itemView.findViewById(R.id.pictureListTextView);
            eventTextView = itemView.findViewById(R.id.eventTextView);
            availableTextView = itemView.findViewById(R.id.availableTextView);
            visibleTextView = itemView.findViewById(R.id.visibleTextView);
        }
    }

}


