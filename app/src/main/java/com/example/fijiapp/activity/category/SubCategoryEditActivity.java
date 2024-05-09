package com.example.fijiapp.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.SubCategoryService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;

public class SubCategoryEditActivity extends AppCompatActivity {

    private EditText editTextName, editTextDescription;
    private Spinner subCategoryTypeSpinner;
    private SubCategoryService subCategoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_edit);

        subCategoryService = new SubCategoryService();

        SubCategory subCategory = (SubCategory) getIntent().getSerializableExtra("SUBCATEGORY");

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        subCategoryTypeSpinner = findViewById(R.id.subCategoryTypeSpinner);

        editTextName.setText(subCategory.Name);
        editTextDescription.setText(subCategory.Description);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.subcategory_types_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subCategoryTypeSpinner.setAdapter(adapter);

        if (subCategory.Type != null) {
            int index = adapter.getPosition(subCategory.Type.toString());
            subCategoryTypeSpinner.setSelection(index);
        }

        MaterialButton buttonEditSubCategory = findViewById(R.id.buttonEditSubCategory);
        buttonEditSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSubCategory(subCategory);
            }
        });
    }

    private void updateSubCategory(SubCategory subCategory) {
        String updatedName = editTextName.getText().toString().trim();
        String updatedDescription = editTextDescription.getText().toString().trim();
        SubCategoryType updatedType = SubCategoryType.values()[subCategoryTypeSpinner.getSelectedItemPosition()];

        if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedDescription)) {
            Toast.makeText(getApplicationContext(), "Name and description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        subCategory.Name = updatedName;
        subCategory.Description = updatedDescription;
        subCategory.Type = updatedType;

        subCategoryService.updateSubCategory(subCategory)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "SubCategory Updated!", Toast.LENGTH_SHORT).show();
                        navigateToManagementPage();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to update SubCategory: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void navigateToManagementPage() {
        Intent intent = new Intent(this, CategoryManagementAdminActivity.class);
        startActivity(intent);
        finish();
    }
}


