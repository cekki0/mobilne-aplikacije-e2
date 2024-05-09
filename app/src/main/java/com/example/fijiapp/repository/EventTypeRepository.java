package com.example.fijiapp.repository;

import com.example.fijiapp.model.EventType;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class EventTypeRepository {
    private CollectionReference eventTypeRef;
    public EventTypeRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        eventTypeRef = database.collection("eventTypes");
    }

    public Task<DocumentReference> addEventType(EventType eventType) {
        return eventTypeRef.add(eventType);
    }

    public Task<Void> updateEventType(EventType eventType) {
        String eventTypeId = eventType.Id;
        if (eventTypeId == null) {
            return Tasks.forException(new IllegalArgumentException("EventType ID is null"));
        }

        return eventTypeRef.document(eventTypeId).set(eventType);
    }

    public Task<Void> deleteEventType(EventType eventType) {
        String eventTypeId = eventType.Id;
        if (eventTypeId == null) {
            return Tasks.forException(new IllegalArgumentException("EventType ID is null"));
        }
        if(eventType.isActive)
            eventType.isActive = false;
        else
            eventType.isActive = true;
        return eventTypeRef.document(eventTypeId).set(eventType);
    }

    public Task<QuerySnapshot> getAllEventTypes() {
        return eventTypeRef.get();
    }
}
