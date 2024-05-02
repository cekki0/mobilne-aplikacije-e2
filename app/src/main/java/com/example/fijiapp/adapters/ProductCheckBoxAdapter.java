package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductCheckBoxAdapter extends RecyclerView.Adapter<ProductCheckBoxAdapter.View> {


    private List<Product> products = new ArrayList<>();
    private Context context;

    public ProductCheckBoxAdapter(){}

    public ProductCheckBoxAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public View onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.view.View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_item_product, parent, false);
        return new View(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCheckBoxAdapter.View holder, int position) {
        Product product = products.get(position);
        holder.checkBox.setText(product.Title);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class View extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public View(@NonNull android.view.View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }


}

