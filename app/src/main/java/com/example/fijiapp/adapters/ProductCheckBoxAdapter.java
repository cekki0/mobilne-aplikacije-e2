package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.List;
public class ProductCheckBoxAdapter extends RecyclerView.Adapter<ProductCheckBoxAdapter.ViewHolder> {

    private Context context;
    private List<Product> products;
    private List<Product> selectedProducts = new ArrayList<>();

    public ProductCheckBoxAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.checkBox.setText(product.Title);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedProducts.add(product);
            } else {
                selectedProducts.remove(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}