package com.example.fijiapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fijiapp.R;
import com.example.fijiapp.model.FoodCriteria;
import com.example.fijiapp.model.Guest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GuestDialog extends Dialog {

    public interface GuestDialogListener {
        void onGuestAdded(Guest guest);
    }
    private GuestDialogListener listener;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText editTextAge;
    private CheckBox checkBoxIsInvited;
    private CheckBox checkBoxHasAcceptedInvitation;
    private Spinner spinnerFoodCriteria;
    private Button buttonAddGuest;

    private FirebaseFirestore db;


    public void setGuestDialogListener(GuestDialogListener listener) {
        this.listener = listener;
    }

    public GuestDialog(@NonNull Context context) {
        super(context);
    }

    public GuestDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected GuestDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guest);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        editTextAge = findViewById(R.id.ageEditText);
        checkBoxIsInvited = findViewById(R.id.checkBoxIsInvited);
        checkBoxHasAcceptedInvitation = findViewById(R.id.checkBoxHasAcceptedInvitation);
        spinnerFoodCriteria = findViewById(R.id.spinnerFoodCriteria);
        buttonAddGuest = findViewById(R.id.buttonAddGuest);

        db = FirebaseFirestore.getInstance();

        populateFoodCriteria();

        buttonAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGuestToFirestore();
            }
        });
    }

    private void populateFoodCriteria() {
        // Define event types
        String[] foodCriteria = {"VEGAN", "VEGETERIAN", "NORMAL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, foodCriteria);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerEventType = findViewById(R.id.spinnerFoodCriteria);
        spinnerEventType.setAdapter(adapter);
    }

    private void addGuestToFirestore() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        int age = Integer.parseInt(editTextAge.getText().toString());
        boolean isInvited = checkBoxIsInvited.isChecked();
        boolean hasAcceptedInvitation = checkBoxHasAcceptedInvitation.isChecked();
        String foodCriteria = spinnerFoodCriteria.getSelectedItem().toString();

        Map<String, Object> guest = new HashMap<>();
        guest.put("FirstName", firstName);
        guest.put("LastName", lastName);
        guest.put("Age", age);
        guest.put("IsInvited", isInvited);
        guest.put("HasAcceptedInvitation", hasAcceptedInvitation);
        guest.put("FoodCriteria", foodCriteria);

        FoodCriteria selectedFoodCriteria;
        if(foodCriteria == "NORMAL"){
            selectedFoodCriteria = FoodCriteria.NORMAL;
        } else if(foodCriteria == "VEGAN"){
            selectedFoodCriteria = FoodCriteria.VEGAN;
        }
        else{
            selectedFoodCriteria = FoodCriteria.VEGETERIAN;
        }

        Guest newGuest = new Guest(firstName, lastName, age, isInvited, hasAcceptedInvitation, selectedFoodCriteria);

        db.collection("guests")
                .add(guest)
                .addOnSuccessListener(documentReference -> {
                    listener.onGuestAdded(newGuest);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                });
    }
}