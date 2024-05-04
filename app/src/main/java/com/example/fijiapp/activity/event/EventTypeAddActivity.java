package com.example.fijiapp.activity.event;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;

import java.util.ArrayList;
import java.util.List;

public class EventTypeAddActivity extends AppCompatActivity {

    private EditText eventTypeNameEditText;
    private EditText eventTypeDescriptionEditText;
    private Button addEventTypeButton;
    private Button addSubcategoryButton;
    private Spinner subcategorySpinner;
    private ListView subcategoryListView;
    private List<SubCategory> subCategories;
    private List<String> recommendedSubcategories;
    private ArrayAdapter<String> subcategoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_add);

        subCategories = initData();
        recommendedSubcategories = new ArrayList<>();

        eventTypeNameEditText = findViewById(R.id.eventTypeNameEditText);
        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        addEventTypeButton = findViewById(R.id.addEventTypeButton);
        addSubcategoryButton = findViewById(R.id.addSubcategoryButton);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        subcategoryListView = findViewById(R.id.subcategoryListView);

        List<String> subcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            subcategoryNames.add(subCategory.Name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(spinnerAdapter);

        subcategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recommendedSubcategories);
        subcategoryListView.setAdapter(subcategoryListAdapter);

        addEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventType();
            }
        });

        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubCategoryToList();
            }
        });
    }

    private void addEventType() {
        String name = eventTypeNameEditText.getText().toString().trim();
        String description = eventTypeDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please enter both event type name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        List<SubCategory> selectedSubcategories = new ArrayList<>();
        for (String subcategoryName : recommendedSubcategories) {
            for (SubCategory subCategory : subCategories) {
                if (subCategory.Name.equals(subcategoryName)) {
                    selectedSubcategories.add(subCategory);
                    break;
                }
            }
        }

        EventType eventType = new EventType(name, description, selectedSubcategories);
    }


    private void addSubCategoryToList() {
        String selectedSubCategory = subcategorySpinner.getSelectedItem().toString();
        recommendedSubcategories.add(selectedSubCategory);
        subcategorySpinner.setSelection(0);
        subcategoryListAdapter.notifyDataSetChanged();
    }


    private List<SubCategory> initData() {
        List<SubCategory> subCategories = new ArrayList<>();

        SubCategory subCategory1 = new SubCategory("Catering", "Catering service", SubCategoryType.SERVICE);
        SubCategory subCategory2 = new SubCategory("Floral", "Floral arrangement service", SubCategoryType.SERVICE);
        subCategories.add(subCategory1);
        subCategories.add(subCategory2);

        return subCategories;
    }
}
