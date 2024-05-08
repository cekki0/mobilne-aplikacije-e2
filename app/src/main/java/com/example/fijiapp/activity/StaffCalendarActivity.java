package com.example.fijiapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.EventAdapter;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.utils.EventDecorator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class StaffCalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Button buttonMarkBusy;
    private List<CalendarDay> busyDates;
    private List<Event> events = new ArrayList<>();
    private EventAdapter adapter;
    private int filterMonth; // Month to filter
    // Access the Firestore database
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_calendar);

        RecyclerView recyclerView = findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calendarView = findViewById(R.id.calendarView);
        buttonMarkBusy = findViewById(R.id.buttonMarkBusy);

        loadEvents(recyclerView);
    }

    private void loadEvents(RecyclerView recyclerView) {
        db.collection("events").get().continueWith(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot qs = task.getResult();
                if (qs != null && !qs.isEmpty()) {

                    busyDates = new ArrayList<>();
                    for (DocumentSnapshot d : qs.getDocuments()) {
                        Event e = d.toObject(Event.class);
                        if (e != null) {
                            events.add(e);
                            Calendar calendar = new GregorianCalendar();
                            calendar.setTime(e.Date);
                            busyDates.add(CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
                        }
                    }
                    filterMonth = Calendar.getInstance().get(Calendar.MONTH);

                    List<Event> filteredEvents = filterEventsByMonth(events, filterMonth);

                    adapter = new EventAdapter(filteredEvents, this, filterMonth);
                    calendarView.addDecorator(new EventDecorator(this, com.google.android.material.R.color.design_default_color_error, busyDates));
                    recyclerView.setAdapter(adapter);

                }
            }
            throw new Exception("Failed to fetch");
        });
    }

    private List<Event> filterEventsByMonth(List<Event> events, int month) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : events) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(event.Date);
            if (calendar.get(Calendar.MONTH) == month) {
                filteredEvents.add(event);
            }
        }
        return filteredEvents;
    }
}