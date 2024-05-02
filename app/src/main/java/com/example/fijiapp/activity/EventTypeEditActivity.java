package com.example.fijiapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class EventTypeEditActivity extends AppCompatActivity {

    public EditText eventTypeDescriptionEditText;
    public Button saveChangesButton;
    public Button addSubcategoryButton;
    public Spinner subcategorySpinner;
    public ListView subcategoryListView;
    public ArrayAdapter<String> subcategoryListAdapter;

    public EventType eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_edit);

        eventType = (EventType) getIntent().getSerializableExtra("eventType");

        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        subcategoryListView = findViewById(R.id.subcategoryListView);
        addSubcategoryButton = findViewById(R.id.addSubcategoryButton); // Initialize addSubcategoryButton here

        eventTypeDescriptionEditText.setText(eventType.description);

        List<SubCategory> subCategories = eventType.suggestedSubCategories;
        List<String> subcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            subcategoryNames.add(subCategory.Name);
        }

        subcategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subcategoryNames);
        subcategoryListView.setAdapter(subcategoryListAdapter);

        List<SubCategory> allSubCategories = initData();
        List<String> allSubcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : allSubCategories) {
            allSubcategoryNames.add(subCategory.Name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allSubcategoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(spinnerAdapter);

        subcategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Remove the clicked subcategory from the list view
                String removedSubcategory = subcategoryListAdapter.getItem(position);
                subcategoryListAdapter.remove(removedSubcategory);

                // Update the eventType.suggestedSubCategories
                List<SubCategory> updatedSubCategories = new ArrayList<>();
                for (int i = 0; i < subcategoryListAdapter.getCount(); i++) {
                    String subcategoryName = subcategoryListAdapter.getItem(i);
                    for (SubCategory subCategory : eventType.suggestedSubCategories) {
                        if (subCategory.Name.equals(subcategoryName)) {
                            updatedSubCategories.add(subCategory);
                            break;
                        }
                    }
                }
                eventType.suggestedSubCategories = updatedSubCategories;

                // Notify the adapter of the change
                subcategoryListAdapter.notifyDataSetChanged();
            }
        });

        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubCategoryToList();
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void addSubCategoryToList() {
        String selectedSubCategory = subcategorySpinner.getSelectedItem().toString();

        // Check if the selected subcategory is already added
        boolean alreadyAdded = false;
        for (int i = 0; i < subcategoryListAdapter.getCount(); i++) {
            if (selectedSubCategory.equals(subcategoryListAdapter.getItem(i))) {
                alreadyAdded = true;
                break;
            }
        }

        if (alreadyAdded) {
            Toast.makeText(this, "Subcategory already added", Toast.LENGTH_SHORT).show();
        } else {
            subcategoryListAdapter.add(selectedSubCategory);
            subcategoryListAdapter.notifyDataSetChanged();
        }
    }


    private void saveChanges() {
        String description = eventTypeDescriptionEditText.getText().toString().trim();

        if (description.isEmpty()) {
            Toast.makeText(this, "Please enter the event type description", Toast.LENGTH_SHORT).show();
            return;
        }

        eventType.description = description;

        // Save the modified subcategories list to eventType
        List<SubCategory> selectedSubcategories = new ArrayList<>();
        for (int i = 0; i < subcategoryListAdapter.getCount(); i++) {
            String subcategoryName = subcategoryListAdapter.getItem(i);
            for (SubCategory subCategory : initData()) {
                if (subCategory.Name.equals(subcategoryName)) {
                    selectedSubcategories.add(subCategory);
                    break;
                }
            }
        }
        eventType.suggestedSubCategories = selectedSubcategories;

        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        finish();
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
