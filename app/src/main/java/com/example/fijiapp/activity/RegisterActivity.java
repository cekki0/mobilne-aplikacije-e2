package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerAsOrganizer(View view) {
        Intent intent = new Intent(this, EventOrganizerRegistrationActivity.class);
        startActivity(intent);
    }

    public void registerAsServiceProvider(View view) {
        Intent intent = new Intent(this, ServiceProviderRegistrationActivity.class);
        startActivity(intent);
    }
}
