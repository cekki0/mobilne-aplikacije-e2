package com.example.fijiapp.service;

import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.repository.EventTypeRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventTypeService {
    private EventTypeRepository eventTypeRepository;
    private SubCategoryService subCategoryService;

    public EventTypeService() {
        eventTypeRepository = new EventTypeRepository();
        subCategoryService = new SubCategoryService();
    }

    public void addEventType(EventType eventType) {
        eventTypeRepository.addEventType(eventType);
    }

    public void updateEventType(EventType eventType) {
        eventTypeRepository.updateEventType(eventType);
    }

    public void deleteEventType(EventType eventType) {
        eventTypeRepository.deleteEventType(eventType);
    }

    public Task<List<EventType>> getAllCategories() {
        return eventTypeRepository.getAllEventTypes().continueWithTask(task -> {
            if (task.isSuccessful()) {
                List<Task<Void>> subCategoryTasks = new ArrayList<>();
                List<EventType> eventTypes = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    EventType eventType = document.toObject(EventType.class);
                    if (eventType != null && !eventType.SuggestedSubCategoryIds.isEmpty()) {
                        eventType.Id = document.getId();
                        List<SubCategory> subCategories = new ArrayList<>();
                        for (String subCategoryId : eventType.SuggestedSubCategoryIds) {
                            Task<SubCategory> subCategoryTask = subCategoryService.getSubCategoryById(subCategoryId);
                            subCategoryTasks.add(subCategoryTask.onSuccessTask(subCategory -> {
                                if (subCategory != null) {
                                    subCategories.add(subCategory);
                                }
                                return null;
                            }));
                        }
                        eventType.SuggestedSubCategories = subCategories;
                    }
                    eventTypes.add(eventType);
                }
                return Tasks.whenAll(subCategoryTasks).continueWith(ignored -> eventTypes);
            } else {
                throw task.getException();
            }
        });
    }
}
