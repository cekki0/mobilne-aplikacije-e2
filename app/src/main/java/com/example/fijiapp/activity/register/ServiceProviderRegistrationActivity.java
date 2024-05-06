package com.example.fijiapp.activity.register;

import static com.example.fijiapp.model.UserRole.SERVICE_PROVIDER;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.event.EventTypeAddActivity;
import com.example.fijiapp.activity.login.LoginActivity;
import com.example.fijiapp.adapters.CategoryListAdapter;
import com.example.fijiapp.adapters.EventTypeListAdapter;
import com.example.fijiapp.adapters.WorkingDayAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.example.fijiapp.model.WorkingDay;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.EventTypeService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceProviderRegistrationActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<WorkingDay> workingDayList;
    private List<Category> categoryList;
    private List<EventType> eventTypeList;
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextFirstName,
            editTextLastName, editTextAddress, editTextPhoneNumber,
            editTextCompanyEmail, editTextCompanyName, editTextCompanyAddress,
            editTextCompanyPhoneNumber, editTextCompanyAbout;

    private Spinner spinnerServiceCategories, spinnerServiceEventTypes, spinnerServiceWorkDays, spinnerStartTime, spinnerEndTime;
    private Button buttonRegister, buttonUploadProfilePicture, buttonUploadCompanyPictures,
            buttonServiceCategoryAdd, buttonServiceEventTypeAdd, buttonServiceWorkDayAdd;

    private CheckBox checkBoxWorkingDay;

    private RecyclerView recyclerViewWorkingDays,recyclerViewCategories,recyclerViewEventTypes;
    private Boolean isBoxChecked = true;
    private WorkingDayAdapter workingDayAdapter;
    private CategoryListAdapter categoryListAdapter;
    private EventTypeListAdapter eventTypeListAdapter;
    private FirebaseAuth mAuth;
    private List<Category> categories;
    private List<EventType> eventTypes;
    private List<String> selectedCategories = new ArrayList<>();
    private List<String> selectedEventTypes = new ArrayList<>();
    private CategoryService categoryService = new CategoryService();
    private EventTypeService eventTypeService = new EventTypeService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_registration);
        workingDayList = new ArrayList<>();
        categoryList = new ArrayList<>();
        eventTypeList = new ArrayList<>();

        //Personal info
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        //Company info
        editTextCompanyEmail = findViewById(R.id.editTextCompanyEmail);
        editTextCompanyName = findViewById(R.id.editTextCompanyName);
        editTextCompanyAddress = findViewById(R.id.editTextCompanyAddress);
        editTextCompanyPhoneNumber = findViewById(R.id.editTextCompanyPhoneNumber);
        editTextCompanyAbout = findViewById(R.id.editTextCompanyAbout);

        //Buttons
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonUploadProfilePicture = findViewById(R.id.buttonUploadProfilePicture);
        buttonUploadCompanyPictures = findViewById(R.id.buttonUploadCompanyPictures);
        buttonServiceCategoryAdd = findViewById(R.id.buttonServiceCategoryAdd);
        buttonServiceEventTypeAdd = findViewById(R.id.buttonServiceEventTypeAdd);
        buttonServiceWorkDayAdd = findViewById(R.id.buttonServiceWorkDayAdd);

        spinnerServiceCategories = findViewById(R.id.spinnerServiceCategories);
        spinnerServiceEventTypes = findViewById(R.id.spinnerServiceEventTypes);
        spinnerServiceWorkDays = findViewById(R.id.spinnerServiceWorkDays);
        spinnerStartTime = findViewById(R.id.spinnerStartTime);
        spinnerEndTime = findViewById(R.id.spinnerEndTime);

        checkBoxWorkingDay = findViewById(R.id.checkBoxWorkingDay);

        recyclerViewWorkingDays = findViewById(R.id.recyclerViewWorkingDays);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewWorkingDays.setLayoutManager(layoutManager);
        workingDayAdapter = new WorkingDayAdapter(workingDayList);
        recyclerViewWorkingDays.setAdapter(workingDayAdapter);

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        LinearLayoutManager layoutCategoryManager = new LinearLayoutManager(this);
        recyclerViewCategories.setLayoutManager(layoutCategoryManager);
        categoryListAdapter = new CategoryListAdapter(categoryList);
        recyclerViewCategories.setAdapter(categoryListAdapter);

        recyclerViewEventTypes = findViewById(R.id.recyclerViewEventTypes);
        LinearLayoutManager layoutEventTypesManager = new LinearLayoutManager(this);
        recyclerViewEventTypes.setLayoutManager(layoutEventTypesManager);
        eventTypeListAdapter = new EventTypeListAdapter(eventTypeList);
        recyclerViewEventTypes.setAdapter(eventTypeListAdapter);

        fetchCategories();
        fetchEventTypes();

        String[] serviceWorkDays = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        ArrayAdapter<String> workDaysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceWorkDays);
        workDaysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceWorkDays.setAdapter(workDaysAdapter);

        String[] timeSlots = {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
        ArrayAdapter<String> startTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStartTime.setAdapter(startTimeAdapter);
        ArrayAdapter<String> endTimeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlots);
        endTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEndTime.setAdapter(endTimeAdapter);

        checkBoxWorkingDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isBoxChecked = true;
                    findViewById(R.id.timeSlotsLayout).setVisibility(View.VISIBLE);
                } else {
                    isBoxChecked = false;
                    findViewById(R.id.timeSlotsLayout).setVisibility(View.GONE);
                }
            }
        });

        buttonServiceWorkDayAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkingDay();
            }
        });

        buttonServiceCategoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        buttonServiceEventTypeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventType();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void setCategoriesAdapter()
    {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.Name);
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceCategories.setAdapter(categoryAdapter);
    }

    private void setEventTypesAdapter()
    {
        List<String> eventTypeNames = new ArrayList<>();
        for (EventType eventType : eventTypes) {
            eventTypeNames.add(eventType.Name);
        }
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventTypeNames);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServiceEventTypes.setAdapter(eventAdapter);
    }

    private void fetchCategories() {
        categoryService.getAllCategories()
                .addOnCompleteListener(new OnCompleteListener<List<Category>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Category>> task) {
                        if (task.isSuccessful()) {
                            categories = task.getResult();
                            setCategoriesAdapter();
                        } else {
                            Toast.makeText(ServiceProviderRegistrationActivity.this,
                                    "Failed to fetch categories", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchEventTypes() {
        eventTypeService.getAllEventTypes()
                .addOnCompleteListener(new OnCompleteListener<List<EventType>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<EventType>> task) {
                        if (task.isSuccessful()) {
                            eventTypes = task.getResult();
                            setEventTypesAdapter();
                        } else {
                            Toast.makeText(ServiceProviderRegistrationActivity.this,
                                    "Failed to fetch event types", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addEventType() {
        String selectedEventTypeName = spinnerServiceEventTypes.getSelectedItem().toString();
        boolean nameExists = false;
        if(!eventTypeList.isEmpty())
        {
            for (EventType eventType : eventTypeList) {
                if (eventType.Name.equalsIgnoreCase(selectedEventTypeName)) {
                    nameExists = true;
                    break;
                }
            }
        }

        if (!nameExists) {
            if (!eventTypes.isEmpty()) {
                for (EventType et : eventTypes) {
                    if (selectedEventTypeName.equalsIgnoreCase(et.Name)) {
                        selectedEventTypes.add(et.Id);
                        eventTypeList.add(et);
                    }
                }
                Toast.makeText(this, "Added " + selectedEventTypeName, Toast.LENGTH_SHORT).show();
                eventTypeListAdapter.notifyDataSetChanged();
            }
        }
        else
            Toast.makeText(this, "Already added " + selectedEventTypeName, Toast.LENGTH_SHORT).show();
    }

    private void addCategory() {
        String selectedCategoryName = spinnerServiceCategories.getSelectedItem().toString();
        boolean nameExists = false;
        if(!categoryList.isEmpty())
        {
            for (Category category : categoryList) {
                if (category.Name.equalsIgnoreCase(selectedCategoryName)) {
                    nameExists = true;
                    break;
                }
            }
        }

        if (!nameExists) {
            Category category = new Category();
            if (!categories.isEmpty()) {
                for (Category c : categories) {
                    if (selectedCategoryName.equalsIgnoreCase(c.Name))
                    {
                        Log.d("CHECK","udje "+c.Id);
                        selectedCategories.add(c.Id);
                        categoryList.add(c);
                        category = c;
                    }
                }
                Toast.makeText(this, "Added " + category.Name, Toast.LENGTH_SHORT).show();
                categoryListAdapter.notifyDataSetChanged();
            }
        }
        else
            Toast.makeText(this, "Already added " + selectedCategoryName, Toast.LENGTH_SHORT).show();
    }

    private void addWorkingDay() {
        String selectedDay = spinnerServiceWorkDays.getSelectedItem().toString();
        String startTime = spinnerStartTime.getSelectedItem().toString();
        String endTime = spinnerEndTime.getSelectedItem().toString();

        boolean dayExists = false;
        for (WorkingDay day : workingDayList) {
            if (day.workDay.toString().equalsIgnoreCase(selectedDay)) {
                dayExists = true;
                break;
            }
        }

        if (!dayExists) {
            try {
                WorkingDay workingDay;
                if (isBoxChecked) {
                    LocalTime start = LocalTime.parse(startTime);
                    LocalTime end = LocalTime.parse(endTime);
                    WorkHours workHours = new WorkHours(start, end);
                    workingDay = new WorkingDay(WorkDays.valueOf(selectedDay.toUpperCase()), workHours);
                } else {
                    workingDay = new WorkingDay(WorkDays.valueOf(selectedDay.toUpperCase()), null);
                }
                workingDayList.add(workingDay);
                if (isBoxChecked)
                    Toast.makeText(this, "Added " + selectedDay + " with start time: " + startTime + " and end time: " + endTime, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Added " + selectedDay + " as non-working day ", Toast.LENGTH_SHORT).show();

                workingDayAdapter.notifyDataSetChanged();
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, "Invalid time range: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, selectedDay + " has already been added", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String companyEmail = editTextCompanyEmail.getText().toString().trim();
        String companyName = editTextCompanyName.getText().toString().trim();
        String companyAddress = editTextCompanyAddress.getText().toString().trim();
        String companyPhoneNumber = editTextCompanyPhoneNumber.getText().toString().trim();
        String companyAbout = editTextCompanyAbout.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "Enter valid email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Confirm your password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Enter first name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "Enter last name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(getApplicationContext(), "Enter phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyEmail)) {
            Toast.makeText(getApplicationContext(), "Enter company email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(companyEmail)) {
            Toast.makeText(getApplicationContext(), "Enter valid company email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyName)) {
            Toast.makeText(getApplicationContext(), "Enter company name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyAddress)) {
            Toast.makeText(getApplicationContext(), "Enter company address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyPhoneNumber)) {
            Toast.makeText(getApplicationContext(), "Enter company phone number!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(companyAbout)) {
            Toast.makeText(getApplicationContext(), "Enter information about the company!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (workingDayList.size() < 7) {
            Toast.makeText(getApplicationContext(), "Enter all working days!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedCategories.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Select one or more categories!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                            saveUserToFirestore(new User(firstName,lastName,address,phoneNumber,SERVICE_PROVIDER),user,new Company(companyEmail,companyName,companyAddress,companyPhoneNumber,companyAbout,workingDayList,selectedCategories,selectedEventTypes));
                        } else {
                            Toast.makeText(ServiceProviderRegistrationActivity.this, "Error occurred!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveCompanyToFirestore(Company company) {
        db.collection("companies")
                .add(company)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ServiceProviderRegistrationActivity.this, "Company saved successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ServiceProviderRegistrationActivity.this, "Error saving company: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToFirestore(User user, FirebaseUser firebaseUser, Company company) {
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            String email = firebaseUser.getEmail();

            user.Email=email;

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("users")
                    .document(uid)
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            company.OwnerEmail=email;
                            saveCompanyToFirestore(company);
                            Toast.makeText(ServiceProviderRegistrationActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            navigateToLoginPage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ServiceProviderRegistrationActivity.this, "Error saving user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void navigateToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}