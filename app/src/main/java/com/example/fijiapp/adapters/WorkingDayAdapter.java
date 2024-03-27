package com.example.fijiapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.WorkingDay;

import java.util.List;

public class WorkingDayAdapter extends RecyclerView.Adapter<WorkingDayAdapter.ViewHolder> {

    private List<WorkingDay> workingDays;

    public WorkingDayAdapter(List<WorkingDay> workingDays) {
        this.workingDays = workingDays;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_working_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkingDay workingDay = workingDays.get(position);
        holder.bind(workingDay);
    }

    @Override
    public int getItemCount() {
        return workingDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textDay;
        private TextView textStartTime;
        private TextView textEndTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textDay = itemView.findViewById(R.id.textDay);
            textStartTime = itemView.findViewById(R.id.textStartTime);
            textEndTime = itemView.findViewById(R.id.textEndTime);
        }

        public void bind(WorkingDay workingDay) {
            textDay.setText(workingDay.workDay.toString());
            if (workingDay.workHours != null) {
                textStartTime.setText(workingDay.workHours.StartTime.toString());
                textEndTime.setText(workingDay.workHours.EndTime.toString());
            } else {
                textStartTime.setText("Closed");
                textEndTime.setText("Closed");
            }
        }
    }
}
