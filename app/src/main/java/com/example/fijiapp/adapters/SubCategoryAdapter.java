package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.activity.category.SubCategoryEditActivity;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.repository.SubCategoryRepository;
import com.example.fijiapp.service.SubCategoryService;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private List<SubCategory> subCategories;
    private Context context;
    private SubCategoryService subCategoryService = new SubCategoryService();

    public SubCategoryAdapter(List<SubCategory> dataSet, Context context) {
        this.subCategories = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
        holder.nameTextView.setText(subCategory.Name);
        holder.descTextView.setText(subCategory.Description);
        holder.typeTextView.setText(subCategory.Type.toString());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubCategoryEditActivity.class);
                intent.putExtra("SUBCATEGORY", subCategory);
                context.startActivity(intent);
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCategoryService.deleteSubCategory(subCategory);
                Toast.makeText(context.getApplicationContext(), subCategory.Name + " SubCategory Deleted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CategoryManagementAdminActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descTextView;
        TextView typeTextView;
        ImageButton editButton;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.subcategoryNameTextView);
            descTextView = itemView.findViewById(R.id.subcategoryDescTextView);
            typeTextView = itemView.findViewById(R.id.subcategoryTypeTextView);
            editButton = itemView.findViewById(R.id.editSubcategoryBtn);
            deleteButton = itemView.findViewById(R.id.deleteSubcategoryBtn);
        }
    }
}
