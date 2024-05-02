package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.fragment.CreateEventDialogFragment;
import com.example.fijiapp.fragment.WorkHoursDialogFragment;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.Map;

public class StaffDetailActivity extends AppCompatActivity {

    private ImageView imageViewProfile;
    private TextView textViewFullName;
    private TextView textViewEmail;
    private TextView textViewPhoneNumber;
    private TextView textViewAddress;
    private TextView textViewWorkHours;
    private MaterialButton blockBtn;
    public static final String EXTRA_USER = "extra_user";

    private boolean isBlocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);

        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewFullName = findViewById(R.id.textViewFullName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewWorkHours = findViewById(R.id.textViewWorkHours);
        blockBtn = findViewById(R.id.buttonBlockUser);

        User user = getIntent().getParcelableExtra(EXTRA_USER);

        if (user != null) {

            Picasso.get().load(user.ProfileImage).into(imageViewProfile);

            textViewFullName.setText(String.format(Locale.getDefault(), "%s %s", user.FirstName, user.LastName));
            textViewEmail.setText(String.format(Locale.getDefault(), "Email: %s", user.Email));
            textViewPhoneNumber.setText(String.format(Locale.getDefault(), "Phone Number: %s", user.PhoneNumber));
            textViewAddress.setText(String.format(Locale.getDefault(), "Address: %s", user.Address));

            StringBuilder workHoursText = new StringBuilder();
            for (Map.Entry<WorkDays, WorkHours> entry : user.WorkHours.entrySet()) {
                workHoursText.append(entry.getKey()).append(": ")
                        .append(entry.getValue().StartTime).append(" - ")
                        .append(entry.getValue().EndTime).append("\n");
            }
            textViewWorkHours.setText(workHoursText.toString());
        }
    }

    public void onBlockUserButtonClick(View view) {
        if(!isBlocked){
            blockBtn.setText("Deactivate Account");
            blockBtn.setIconResource(android.R.drawable.ic_input_add);
            blockBtn.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            blockBtn.setIconTintResource(android.R.color.holo_green_dark);
            isBlocked = true;
        }else{
            blockBtn.setText("Activate Account");
            blockBtn.setIconResource(android.R.drawable.ic_delete);
            blockBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            blockBtn.setIconTintResource(android.R.color.holo_red_dark);
            isBlocked = false;
        }
    }

    public void onEditWorkHoursButtonClicked(View view) {
        WorkHoursDialogFragment dialogFragment = new WorkHoursDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "WorkHoursDialogFragment");
    }

    public void onCalendarButtonClick(View view) {
        Intent intent = new Intent(StaffDetailActivity.this, StaffCalendarActivity.class);
        startActivity(intent);
    }

    public void onAddEventButtonClick(View view) {
        CreateEventDialogFragment dialogFragment = new CreateEventDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "CreateEventDialogFragment");
    }
}