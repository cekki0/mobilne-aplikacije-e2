package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.Company;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.User;
import com.example.fijiapp.repository.CategoryRepository;
import com.example.fijiapp.repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private CompanyService companyService;

    public UserService() {
        userRepository = new UserRepository();
        companyService = new CompanyService();
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public Task<User> getUserById(String userId) {
        return userRepository.getUserById(userId)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User user = document.toObject(User.class);
                            user.Id = document.getId();
                            return user;
                        } else {
                            return null;
                        }
                    } else {
                        throw task.getException();
                    }
                });
    }

    public Task<List<User>> getAllUsers() {
        return userRepository.getAllUsers().continueWithTask(task -> {
            if (task.isSuccessful()) {
                List<Task<User>> userTasks = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    User user = document.toObject(User.class);
                    user.Id = document.getId();

                    if (!user.IsActive) {
                        Task<Company> companyTask = companyService.getCompanyByOwnerEmail(user.Email);
                        Task<User> userTask = companyTask.continueWithTask(companyTaskResult -> {
                            if (companyTaskResult.isSuccessful()) {
                                user.Company = companyTaskResult.getResult();
                            }
                            return Tasks.forResult(user);
                        });
                        userTasks.add(userTask);
                    }
                }
                return Tasks.whenAllSuccess(userTasks);
            } else {
                throw task.getException();
            }
        });
    }
}
