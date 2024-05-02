package com.example.fijiapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.SubCategoryAdapter;
import com.example.fijiapp.adapters.SubCategoryAddAdapter;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CategoryAddActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SubCategoryAddAdapter adapter;
    private List<SubCategory> subCategoryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);

        recyclerView = findViewById(R.id.recyclerViewWorkingDays);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubCategoryAddAdapter(subCategoryList);
        recyclerView.setAdapter(adapter);

        Button buttonAddSubCategory = findViewById(R.id.buttonAddSubCategory);
        buttonAddSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText editTextSubCategoryName = findViewById(R.id.editTextSubCategoryName);
                TextInputEditText editTextSubCategoryDescription = findViewById(R.id.editTextSubCategoryDescription);
                Spinner subCategoryTypeSpinner = findViewById(R.id.subCategoryTypeSpinner);
                String name = editTextSubCategoryName.getText().toString();
                String description = editTextSubCategoryDescription.getText().toString();
                SubCategoryType type = SubCategoryType.values()[subCategoryTypeSpinner.getSelectedItemPosition()];

                SubCategory subCategory = new SubCategory(name, description, type);
                addSubCategory(subCategory);
            }
        });
    }

    public void addSubCategory(SubCategory subCategory) {
        subCategoryList.add(subCategory);
        adapter.notifyDataSetChanged();
    }
}
