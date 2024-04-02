package com.example.fijiapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.model.SubCategory;

import java.util.List;

public class SubCategoryAddAdapter extends RecyclerView.Adapter<SubCategoryAddAdapter.SubCategoryViewHolder> {
    private List<SubCategory> subCategoryList;

    public SubCategoryAddAdapter(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategory subCategory = subCategoryList.get(position);
        holder.bind(subCategory);
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView descriptionTextView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(android.R.id.text1);
            descriptionTextView = itemView.findViewById(android.R.id.text2);
        }

        public void bind(SubCategory subCategory) {
            nameTextView.setText(subCategory.Name);
            descriptionTextView.setText(subCategory.Description);
        }
    }
}
