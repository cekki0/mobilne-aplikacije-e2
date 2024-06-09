package com.example.fijiapp.utils;

import com.example.fijiapp.model.ODEvent;

public interface OnRatingCommentListener {
    void onSubmitRatingComment(ODEvent event, float rating, String comment);
}
