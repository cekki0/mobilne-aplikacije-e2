package com.example.fijiapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.GuestAdapter;
import com.example.fijiapp.model.Guest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GuestActivity extends AppCompatActivity implements GuestDialog.GuestDialogListener {

    private List<Guest> guests;
    private RecyclerView recyclerView;
    private GuestAdapter guestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guest);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getGuestListFromFirebase();
    }

    void getGuestListFromFirebase(){
        // Access the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference guestsRef = db.collection("guests");
        guests = new ArrayList<>();

        // Retrieve data asynchronously
        guestsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Guest guest = document.toObject(Guest.class);
                    guests.add(guest);
                }

                // Initialize the adapter and set it to the RecyclerView
                guestAdapter = new GuestAdapter(guests, this);
                recyclerView.setAdapter(guestAdapter);
            } else {
                // Handle errors here
                // For example, log the error or show a toast
            }
        });
    }

    public void onAddGuestClick(View view) {
        GuestDialog guestDialog = new GuestDialog(this);
        guestDialog.setGuestDialogListener(this); // Set the listener
        guestDialog.show();
    }

    @Override
    public void onGuestAdded(Guest guest) {
        // Add the new guest to the list and notify the adapter
        guests.add(guest);
        guestAdapter.notifyItemInserted(guests.size() - 1);
    }
}