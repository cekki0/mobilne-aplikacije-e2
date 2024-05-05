package com.example.fijiapp.activity.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.EventTypeService;
import com.example.fijiapp.service.SubCategoryService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
    public SubCategoryService subCategoryService = new SubCategoryService();
    public List<SubCategory> subCategories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_edit);

        eventType = (EventType) getIntent().getSerializableExtra("eventType");

        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        subcategoryListView = findViewById(R.id.subcategoryListView);
        addSubcategoryButton = findViewById(R.id.addSubcategoryButton);

        eventTypeDescriptionEditText.setText(eventType.Description);

        List<SubCategory> subCategories = eventType.SuggestedSubCategories;
        List<String> subcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            subcategoryNames.add(subCategory.Name);
        }

        subcategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subcategoryNames);
        subcategoryListView.setAdapter(subcategoryListAdapter);

        fetchSubCategories();

        subcategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String removedSubcategory = subcategoryListAdapter.getItem(position);
                subcategoryListAdapter.remove(removedSubcategory);
                List<SubCategory> updatedSubCategories = new ArrayList<>();
                List<String> updatedSubCategoriesIds = new ArrayList<>();
                for (int i = 0; i < subcategoryListAdapter.getCount(); i++) {
                    String subcategoryName = subcategoryListAdapter.getItem(i);
                    for (SubCategory subCategory : eventType.SuggestedSubCategories) {
                        if (subCategory.Name.equals(subcategoryName)) {
                            updatedSubCategories.add(subCategory);
                            updatedSubCategoriesIds.add(subCategory.Id);
                            break;
                        }
                    }
                }
                eventType.SuggestedSubCategories = updatedSubCategories;
                eventType.SuggestedSubCategoryIds = updatedSubCategoriesIds;
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

    private void setAdapter()
    {
        List<String> allSubcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            allSubcategoryNames.add(subCategory.Name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allSubcategoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(spinnerAdapter);
    }

    private void fetchSubCategories() {
        subCategoryService.getAllSubCategories()
                .addOnCompleteListener(new OnCompleteListener<List<SubCategory>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<SubCategory>> task) {
                        if (task.isSuccessful()) {
                            subCategories = task.getResult();
                            setAdapter();
                        } else {
                            Toast.makeText(EventTypeEditActivity.this,
                                    "Failed to fetch sub categories", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addSubCategoryToList() {
        String selectedSubCategory = subcategorySpinner.getSelectedItem().toString();

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
            Toast.makeText(this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }



        eventType.Description = description;

        List<SubCategory> selectedSubcategories = new ArrayList<>();
        List<String> selectedSubcategoryIds = new ArrayList<>();
        for (int i = 0; i < subcategoryListAdapter.getCount(); i++) {
            String subcategoryName = subcategoryListAdapter.getItem(i);
            for (SubCategory subCategory : subCategories) {
                if (subCategory.Name.equals(subcategoryName)) {
                    selectedSubcategories.add(subCategory);
                    selectedSubcategoryIds.add(subCategory.Id);
                    break;
                }
            }
        }
        eventType.SuggestedSubCategories = selectedSubcategories;
        eventType.SuggestedSubCategoryIds = selectedSubcategoryIds;

        if (eventType.SuggestedSubCategoryIds.size() < 1) {
            Toast.makeText(this, "Must add one or more subcategories!", Toast.LENGTH_SHORT).show();
            return;
        }

        EventTypeService eventTypeService = new EventTypeService();
        eventTypeService.updateEventType(eventType);
        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EventTypeManagementActivity.class);
        startActivity(intent);
        finish();
    }
}
