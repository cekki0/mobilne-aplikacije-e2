package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.R;
import com.example.fijiapp.fragment.CreateEventDialogFragment;
import com.example.fijiapp.fragment.WorkHoursDialogFragment;
import com.example.fijiapp.model.User;
import com.example.fijiapp.model.WorkDays;
import com.example.fijiapp.model.WorkHours;
import com.example.fijiapp.model.WorkingDay;
import com.example.fijiapp.service.OwnerService;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StaffDetailActivity extends AppCompatActivity implements WorkHoursDialogFragment.WorkHoursDialogListener {

    private ImageView imageViewProfile;
    private TextView textViewFullName;
    private TextView textViewEmail;
    private TextView textViewPhoneNumber;
    private TextView textViewAddress;
    private TextView textViewWorkHours;
    private MaterialButton blockBtn;
    public static final String EXTRA_USER = "extra_user";
    private OwnerService ownerService = new OwnerService();
    private boolean isBlocked = false;
    private List<WorkingDay> workHours = new ArrayList<>();
    private User user;

    @Override
    public void onWorkHoursEntered(LocalTime mondayStartTime, LocalTime mondayEndTime, LocalTime tuesdayStartTime, LocalTime tuesdayEndTime, LocalTime wednesdayStartTime, LocalTime wednesdayEndTime, LocalTime thursdayStartTime, LocalTime thursdayEndTime, LocalTime fridayStartTime, LocalTime fridayEndTime) {
        workHours = new ArrayList<>();
        workHours.add(new WorkingDay(WorkDays.MON, new WorkHours(mondayStartTime, mondayEndTime)));
        workHours.add(new WorkingDay(WorkDays.TUE, new WorkHours(tuesdayStartTime, tuesdayEndTime)));
        workHours.add(new WorkingDay(WorkDays.WED, new WorkHours(wednesdayStartTime, wednesdayEndTime)));
        workHours.add(new WorkingDay(WorkDays.THU, new WorkHours(thursdayStartTime, thursdayEndTime)));
        workHours.add(new WorkingDay(WorkDays.FRI, new WorkHours(fridayStartTime, fridayEndTime)));
        Toast.makeText(StaffDetailActivity.this, "Saved work hours successfully", Toast.LENGTH_SHORT).show();
        ownerService.updateWorkHours(user.Email, workHours);
    }

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

        user = getIntent().getParcelableExtra(EXTRA_USER);
        isBlocked = !user.IsActive;

        if (isBlocked) {
            blockBtn.setText("Activate Account");
            blockBtn.setIconResource(android.R.drawable.ic_input_add);
            blockBtn.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            blockBtn.setIconTintResource(android.R.color.holo_green_dark);
        }

        if (user != null) {

            Picasso.get().load(user.ProfileImage).into(imageViewProfile);

            textViewFullName.setText(String.format(Locale.getDefault(), "%s %s", user.FirstName, user.LastName));
            textViewEmail.setText(String.format(Locale.getDefault(), "Email: %s", user.Email));
            textViewPhoneNumber.setText(String.format(Locale.getDefault(), "Phone Number: %s", user.PhoneNumber));
            textViewAddress.setText(String.format(Locale.getDefault(), "Address: %s", user.Address));

            StringBuilder workHoursText = new StringBuilder();
            for (WorkingDay wd : user.WorkingDays) {
                if (wd.WorkHours != null) {
                    workHoursText.append(wd.WorkDay.name().toString()).append(": ")
                            .append(wd.WorkHours.StartTime).append(" - ")
                            .append(wd.WorkHours.EndTime).append("\n");
                }
            }
            textViewWorkHours.setText(workHoursText.toString());
        }
    }

    public void onBlockUserButtonClick(View view) {
        if (!isBlocked) {
            blockBtn.setText("Activate Account");
            blockBtn.setIconResource(android.R.drawable.ic_input_add);
            blockBtn.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            blockBtn.setIconTintResource(android.R.color.holo_green_dark);
            isBlocked = true;
            ownerService.deactivateStaffAccount(user.Email);
        } else {
            blockBtn.setText("Deactivate Account");
            blockBtn.setIconResource(android.R.drawable.ic_delete);
            blockBtn.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            blockBtn.setIconTintResource(android.R.color.holo_red_dark);
            isBlocked = false;
            ownerService.activateStaffAccount(user.Email);
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