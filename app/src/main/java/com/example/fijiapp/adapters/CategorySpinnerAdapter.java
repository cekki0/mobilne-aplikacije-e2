package com.example.fijiapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<String> {


    private List<String> dataList;
    private Context mContext;

    public CategorySpinnerAdapter(List<String> data, Context context) {
        super(context, android.R.layout.simple_spinner_item, data);
        this.dataList = data;
        this.mContext = context;
    }


}


