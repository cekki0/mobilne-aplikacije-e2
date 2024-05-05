package com.example.fijiapp.activity.event;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.EventTypeService;
import com.example.fijiapp.service.SubCategoryService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class EventTypeAddActivity extends AppCompatActivity {

    private EditText eventTypeNameEditText;
    private EditText eventTypeDescriptionEditText;
    private Button addEventTypeButton;
    private Button addSubcategoryButton;
    private Spinner subcategorySpinner;
    private ListView subcategoryListView;
    private List<SubCategory> subCategories = new ArrayList<>();
    private List<String> recommendedSubcategories = new ArrayList<>();
    private ArrayAdapter<String> subcategoryListAdapter;
    private SubCategoryService subCategoryService = new SubCategoryService();
    private EventTypeService eventTypeService = new EventTypeService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_type_add);

        fetchSubCategories();

        eventTypeNameEditText = findViewById(R.id.eventTypeNameEditText);
        eventTypeDescriptionEditText = findViewById(R.id.eventTypeDescriptionEditText);
        addEventTypeButton = findViewById(R.id.addEventTypeButton);
        addSubcategoryButton = findViewById(R.id.addSubcategoryButton);
        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        subcategoryListView = findViewById(R.id.subcategoryListView);

        addEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEventType();
            }
        });

        addSubcategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubCategoryToList();
            }
        });
    }

    private void setAdapter()
    {
        List<String> subcategoryNames = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            subcategoryNames.add(subCategory.Name);
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subcategoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subcategorySpinner.setAdapter(spinnerAdapter);

        subcategoryListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recommendedSubcategories);
        subcategoryListView.setAdapter(subcategoryListAdapter);
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
                            Toast.makeText(EventTypeAddActivity.this,
                                    "Failed to fetch sub categories", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createEventType() {
        String name = eventTypeNameEditText.getText().toString().trim();
        String description = eventTypeDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Name and description cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recommendedSubcategories.size() < 1) {
            Toast.makeText(this, "Must add one or more subcategories!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> suggestedSubCategoryIds = new ArrayList<>();
        for (String subcategoryName : recommendedSubcategories) {
            for (SubCategory subCategory : subCategories) {
                if (subCategory.Name.equals(subcategoryName)) {
                    suggestedSubCategoryIds.add(subCategory.Id);
                    break;
                }
            }
        }

        EventType eventType = new EventType(name, description, suggestedSubCategoryIds);
        eventTypeService.addEventType(eventType);
        Toast.makeText(getApplicationContext(), "Event type created!", Toast.LENGTH_SHORT).show();
        navigateToManagementPage();
    }

    public void navigateToManagementPage() {
        Intent intent = new Intent(this, EventTypeManagementActivity.class);
        startActivity(intent);
    }

    private void addSubCategoryToList() {
        if(subCategories.isEmpty()){
            return;
        }
        String selectedSubCategory = subcategorySpinner.getSelectedItem().toString();
        recommendedSubcategories.add(selectedSubCategory);
        subcategorySpinner.setSelection(0);
        subcategoryListAdapter.notifyDataSetChanged();
    }
}
