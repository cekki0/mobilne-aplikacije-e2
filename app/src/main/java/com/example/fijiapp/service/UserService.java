package com.example.fijiapp.service;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.User;
import com.example.fijiapp.repository.CategoryRepository;
import com.example.fijiapp.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public void updateCategory(User user) {
        userRepository.updateUser(user);
    }

    public Task<List<User>> getAllUsers() {
        return userRepository.getAllUsers().continueWith(task -> {
            if (task.isSuccessful()) {
                List<User> users = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    user.Id = document.getId();
                    users.add(user);
                }
                return users;
            } else {
                throw task.getException();
            }
        });
    }
}
