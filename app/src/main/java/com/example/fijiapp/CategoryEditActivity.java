package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fijiapp.model.Category;
import com.google.android.material.button.MaterialButton;

public class CategoryEditActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "com.example.fijiapp.EXTRA_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CATEGORY)) {
            Category category = (Category) intent.getSerializableExtra(EXTRA_CATEGORY);
            editTextName.setText(category.Name);
            editTextDescription.setText(category.Description);
        }

        MaterialButton buttonEditCategory = findViewById(R.id.buttonEditCategory);
        buttonEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Category Saved!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}