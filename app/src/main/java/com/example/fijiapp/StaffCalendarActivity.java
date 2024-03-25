package com.example.fijiapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.EventAdapter;
import com.example.fijiapp.model.Event;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.utils.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StaffCalendarActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private Button buttonMarkBusy;
    private List<CalendarDay> busyDates;
    private List<Event> events = new ArrayList<>();
    private EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_calendar);

        events.add(new Event("Meeting", new Date(124, 0, 1), LocalTime.of(10, 0), LocalTime.of(12, 0), EventType.OCCUPIED));
        events.add(new Event("Birthday Bash", new Date(124, 3, 15), LocalTime.of(19, 0), LocalTime.of(22, 0), EventType.RESERVED));

        RecyclerView recyclerView = findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventAdapter(events, this);
        recyclerView.setAdapter(adapter);

        calendarView = findViewById(R.id.calendarView);
        buttonMarkBusy = findViewById(R.id.buttonMarkBusy);

        busyDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        CalendarDay today = CalendarDay.today();

        calendar.set(today.getYear(), today.getMonth(), today.getDay());

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int yearTomorrow = calendar.get(Calendar.YEAR);
        int monthTomorrow = calendar.get(Calendar.MONTH);
        int dayOfMonthTomorrow = calendar.get(Calendar.DAY_OF_MONTH);

        CalendarDay tomorrow = CalendarDay.from(yearTomorrow, monthTomorrow, dayOfMonthTomorrow);

        busyDates.add(today);
        busyDates.add(tomorrow);
        calendarView.addDecorator(new EventDecorator(this, com.google.android.material.R.color.design_default_color_error, busyDates));

        Context context = this;
        buttonMarkBusy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay selectedDate = calendarView.getSelectedDate();
                if (selectedDate != null && !busyDates.contains(selectedDate)) {
                    busyDates.add(selectedDate);
                    calendarView.addDecorator(new EventDecorator(context,com.google.android.material.R.color.design_default_color_error, busyDates));
                }
            }
        });
    }
}