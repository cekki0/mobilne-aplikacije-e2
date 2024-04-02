package com.example.fijiapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fijiapp.model.Product;

public class UpdateProductActivity extends AppCompatActivity {
    private EditText titleEditText;

    private EditText descriptionEditText;
    private EditText categorySpinner;
    private EditText priceEditText;
    private EditText discountEditText;
    private EditText newPriceEditText;
    private EditText pictureListEditText;
    private EditText eventEditText;
    private CheckBox availableEditText;
    private CheckBox visibleEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);


        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        discountEditText = findViewById(R.id.discountEditText);
        //newPriceEditText = findViewById(R.id.newPriceEditText);
        pictureListEditText = findViewById(R.id.pictureListEditText);
        eventEditText = findViewById(R.id.eventEditText);
        availableEditText = findViewById(R.id.availableEditText);
        visibleEditText = findViewById(R.id.visibleEditText);
        categorySpinner = findViewById(R.id.categorySpinner);



        Product product = getIntent().getParcelableExtra("product");
        if (product!=null) {
            // punjenje polja iz intent product
            titleEditText.setText(product.Title);
            descriptionEditText.setText(product.Description);
            priceEditText.setText(String.valueOf(product.Price));
            discountEditText.setText(String.valueOf(product.Discount));
            //newPriceEditText.setText(String.valueOf(product.NewPrice));

            eventEditText.setText(product.Event);
            pictureListEditText.setText(convertPictureListToString(product.PictureList));
            availableEditText.setChecked(product.Available.equals("Yes"));
            visibleEditText.setChecked(product.Visible.equals("Yes"));
            Log.d("DEBUG", "Available: " + product.Available);
            Log.d("DEBUG", "Visible: " + product.Visible);
            categorySpinner.setText(String.valueOf(product.Category));

        }




    }

    private String convertPictureListToString(ArrayList<String> pictureList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String picture : pictureList) {
            stringBuilder.append(picture).append("\n");
        }
        return stringBuilder.toString();
    }

}
