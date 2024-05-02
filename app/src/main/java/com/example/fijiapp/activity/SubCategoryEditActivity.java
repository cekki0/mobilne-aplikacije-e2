package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fijiapp.R;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.google.android.material.button.MaterialButton;

public class SubCategoryEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_edit);

        Intent intent = getIntent();
        SubCategory subCategory = (SubCategory) intent.getSerializableExtra("SUBCATEGORY");

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        Spinner subCategoryTypeSpinner = findViewById(R.id.subCategoryTypeSpinner);

        editTextName.setText(subCategory.Name);
        editTextDescription.setText(subCategory.Description);
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
                Toast.makeText(getApplicationContext(), "SubCategory Saved!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
