package com.example.fijiapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.model.ODEvent;
import com.example.fijiapp.model.ReservationStatus;
import com.example.fijiapp.model.ReservationType;
import com.example.fijiapp.utils.OnRatingCommentListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ODReservationAdapter extends RecyclerView.Adapter<ODReservationAdapter.ReservationViewHolder> {

    private List<ODEvent> reservationList;
    private Context context;
    private OnRatingCommentListener ratingCommentListener;

    public ODReservationAdapter(Context context, List<ODEvent> reservationList, OnRatingCommentListener ratingCommentListener) {
        this.context = context;
        this.reservationList = reservationList;
        this.ratingCommentListener = ratingCommentListener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.od_reservation_card, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        ODEvent event = reservationList.get(position);
        holder.eventNameView.setText("Name: " + event.EventName);
        holder.descriptionView.setText("Description: " + event.Description);
        holder.maxParticipantsView.setText("Max participants: " + event.MaxParticipants);
        holder.locationView.setText("Location: " + event.Location);
        holder.dateView.setText("Date: " + event.Date);
        holder.typeView.setText("Type: " + event.Type);
        holder.reservationStatusView.setText("Status: " + event.ReservationStatus);
        holder.reservationTypeView.setText("Package: " + (event.ReservationType == ReservationType.PACKAGE ? "true" : "false"));

        // Handle cancel button visibility and click event
        if (event.ReservationStatus == event.ReservationStatus.NEW ||
                event.ReservationStatus == event.ReservationStatus.ACCEPTED) {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setOnClickListener(v -> {
                event.ReservationStatus = ReservationStatus.CANCELLED_OD;
                notifyItemChanged(position);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference productsRef = db.collection("odEvents");
                db.collection("odEvents").document(event.Id)
                        .update("ReservationStatus", ReservationStatus.CANCELLED_OD)
                        .addOnSuccessListener(aVoid -> Toast.makeText(context, "Reservation cancelled", Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "Submission Failed", Toast.LENGTH_LONG).show());
            });
        } else {
            holder.btnCancel.setVisibility(View.GONE);
        }

        if ((event.ReservationStatus == event.ReservationStatus.COMPLETED ||
                event.ReservationStatus == event.ReservationStatus.CANCELLED_PUP) && event.Rating == 0 && event.Comment == null) {
            holder.btnComment.setVisibility(View.VISIBLE);
            holder.btnComment.setOnClickListener(v -> {
                showRatingCommentDialog(event);
            });
        } else {
            holder.btnComment.setVisibility(View.GONE);
        }
    }

    private void showRatingCommentDialog(ODEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_rating_comment, null);
        builder.setView(dialogView);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        EditText commentEditText = dialogView.findViewById(R.id.commentEditText);

        builder.setTitle("Rate and Comment")
                .setPositiveButton("Submit", (dialog, id) -> {
                    float rating = ratingBar.getRating();
                    String comment = commentEditText.getText().toString();
                    event.Rating = (int) rating;
                    event.Comment = comment;
                    notifyDataSetChanged();
                    ratingCommentListener.onSubmitRatingComment(event, rating, comment);
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updateList(List<ODEvent> newList) {
        reservationList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameView;
        TextView descriptionView;
        TextView maxParticipantsView;
        TextView privacyView;
        TextView locationView;
        TextView dateView;
        TextView typeView;
        TextView reservationStatusView;
        TextView reservationTypeView;
        Button btnCancel;
        Button btnComment;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            eventNameView = itemView.findViewById(R.id.eventNameView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            maxParticipantsView = itemView.findViewById(R.id.maxParticipantsView);
            privacyView = itemView.findViewById(R.id.privacyView);
            locationView = itemView.findViewById(R.id.locationView);
            dateView = itemView.findViewById(R.id.dateView);
            typeView = itemView.findViewById(R.id.typeView);
            reservationStatusView = itemView.findViewById(R.id.reservationStatusView);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            reservationTypeView = itemView.findViewById(R.id.reservationTypeView);
            btnComment = itemView.findViewById(R.id.btnComment);
        }
    }
}
