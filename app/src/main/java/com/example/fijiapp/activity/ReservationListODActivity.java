package com.example.fijiapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.ODEventAdapter;
import com.example.fijiapp.adapters.ODReservationAdapter;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.ReservationStatus;
import com.example.fijiapp.model.User;
import com.example.fijiapp.service.OwnerService;
import com.example.fijiapp.service.UserService;
import com.example.fijiapp.utils.OnRatingCommentListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReservationListODActivity extends AppCompatActivity implements OnRatingCommentListener {

    private RecyclerView recyclerViewReservations;
    private ODReservationAdapter reservationAdapter;
    private List<ODEvent> reservationList;
    private OwnerService userService = new OwnerService();
    private User currentUser;
    private Spinner spinnerReservationStatus;
    private ReservationStatus selectedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_od);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        spinnerReservationStatus = findViewById(R.id.spinner_reservation_status);


        FirebaseUser currentUserAuth = mAuth.getCurrentUser();

        assert currentUserAuth != null;
        userService.getUserByEmail(currentUserAuth.getEmail()).addOnSuccessListener(extendedUser -> {
                    if (extendedUser != null) {
                        currentUser = extendedUser;
                        recyclerViewReservations = findViewById(R.id.recyclerViewReservationsOD);
                        recyclerViewReservations.setLayoutManager(new LinearLayoutManager(this));
                        getODEventListFromFirebase();
                    } else {
                        Toast.makeText(getApplicationContext(), "User data not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void setupSpinner() {
        // Get the enum values
        ReservationStatus[] statuses = ReservationStatus.values();
        String[] statusStrings = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusStrings[i] = statuses[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReservationStatus.setAdapter(adapter);

        spinnerReservationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = ReservationStatus.valueOf(statusStrings[position]);
                filterEventsByStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStatus = null;
                reservationAdapter.updateList(reservationList);
            }
        });
    }

    private void filterEventsByStatus() {
        if (selectedStatus == null) {
            reservationAdapter.updateList(reservationList);
            return;
        }

        List<ODEvent> filteredList = new ArrayList<>();
        for (ODEvent event : reservationList) {
            if (event.ReservationStatus == selectedStatus) {
                filteredList.add(event);
            }
        }
        reservationAdapter.updateList(filteredList);
    }

    void getODEventListFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("odEvents");
        reservationList = new ArrayList<>();

        productsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ODEvent event = document.toObject(ODEvent.class);
                    reservationList.add(event);
                }

                reservationAdapter = new ODReservationAdapter(this, reservationList, this);
                recyclerViewReservations.setAdapter(reservationAdapter);
                setupSpinner();
            } else {

            }
        });
    }

    @Override
    public void onSubmitRatingComment(ODEvent event, float rating, String comment) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("odEvents").document(event.Id)
                .update("Rating", rating, "Comment", comment)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Rating and Comment Submitted", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Submission Failed", Toast.LENGTH_LONG).show());
    }
}