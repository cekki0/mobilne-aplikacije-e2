package com.example.fijiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fijiapp.activity.CategoryEditActivity;
import com.example.fijiapp.R;
import com.example.fijiapp.model.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(List<Category> dataSet, Context context) {
        this.categories = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.nameTextView.setText(category.Name);
        holder.descTextView.setText(category.Description);

        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(category.SubCategories, context);
        holder.subCategoryRecyclerView.setAdapter(subCategoryAdapter);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryEditActivity.class);
                intent.putExtra(CategoryEditActivity.EXTRA_CATEGORY, category);
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), category.Name + " Category Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descTextView;
        ImageButton editButton;
        ImageButton deleteButton;

        RecyclerView subCategoryRecyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.categoryNameTextView);
            descTextView = itemView.findViewById(R.id.categoryDescTextView);
            editButton = itemView.findViewById(R.id.editCategoryBtn);
            deleteButton = itemView.findViewById(R.id.deleteCategoryBtn);
            subCategoryRecyclerView = itemView.findViewById(R.id.subcategoryRecyclerView);
            subCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
