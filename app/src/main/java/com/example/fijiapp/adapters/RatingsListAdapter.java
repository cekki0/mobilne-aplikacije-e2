package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.fragment.ReportDialogFragment;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.Report;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RatingsListAdapter extends RecyclerView.Adapter<RatingsListAdapter.RatingCommentViewHolder> {

    private Context context;
    private List<ODEvent> eventList;

    public RatingsListAdapter(Context context, List<ODEvent> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public RatingCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rating_comment, parent, false);
        return new RatingCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingCommentViewHolder holder, int position) {
        ODEvent event = eventList.get(position);
        holder.ratingTextView.setText(String.valueOf(event.Rating));
        holder.commentTextView.setText(event.Comment);

        holder.btnReport.setOnClickListener(v -> {
            // Handle report button click
            reportRatingComment(event);
        });
    }

    private void reportRatingComment(ODEvent event) {
        ReportDialogFragment reportDialogFragment = new ReportDialogFragment();
        reportDialogFragment.setReportDialogListener(reason -> {
            if (!reason.isEmpty()) {
                // Save the report to Firebase (Replace this with your Firebase logic)
                saveReportToFirebase(event, reason);
            }
        });
        reportDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "ReportDialogFragment");
    }

    private void saveReportToFirebase(ODEvent event, String reason) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reportsRef = db.collection("reports");
        Report report = new Report(event.Id, reason);
        reportsRef.add(report);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class RatingCommentViewHolder extends RecyclerView.ViewHolder {
        TextView ratingTextView;
        TextView commentTextView;
        Button btnReport;

        public RatingCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingTextView = itemView.findViewById(R.id.text_view_rating);
            commentTextView = itemView.findViewById(R.id.text_view_comment);
            btnReport = itemView.findViewById(R.id.button_report);
        }
    }
}
