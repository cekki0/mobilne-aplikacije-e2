package com.example.fijiapp.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.ODEventAdapter;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventsViewActivity extends AppCompatActivity {

    private List<ODEvent> eventList;
    private RecyclerView recyclerView;
    private ODEventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_events_view);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getODEventListFromFirebase();
    }

    void getODEventListFromFirebase(){

        // Access the Firestore database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference productsRef = db.collection("odEvents");
        eventList = new ArrayList<>();

        // Retrieve data asynchronously
        productsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ODEvent event = document.toObject(ODEvent.class);
                    eventList.add(event);
                }

                eventAdapter = new ODEventAdapter(eventList, this);
                recyclerView.setAdapter(eventAdapter);
            } else {

            }
        });
    }
}