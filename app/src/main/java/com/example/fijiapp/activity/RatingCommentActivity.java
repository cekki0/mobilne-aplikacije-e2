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
import com.example.fijiapp.adapters.RatingsListAdapter;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.Reservation;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RatingCommentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ODEvent> eventList;
    private RatingsListAdapter ratingsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_comment);

        recyclerView = findViewById(R.id.recycler_view_ratings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        ratingsListAdapter = new RatingsListAdapter(this, eventList);
        recyclerView.setAdapter(ratingsListAdapter);

        loadRatingsAndComments();
    }

    private void loadRatingsAndComments() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference eventsRef = db.collection("odEvents");

        eventsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ODEvent event = document.toObject(ODEvent.class);
                    if (event.Rating > 0) { // Check if event has a rating
                        eventList.add(event);
                    }
                }
                ratingsListAdapter.notifyDataSetChanged();
            } else {
                // Handle the error
            }
        });
    }
}