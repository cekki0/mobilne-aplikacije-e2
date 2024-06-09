package com.example.fijiapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fijiapp.R;
import com.example.fijiapp.model.SubCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDialog extends Dialog {

    interface OnSaveListener {
        void onSave(List<String> services);
    }
    private final OnSaveListener onSaveListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, List<String>> service = new HashMap<>();
    private List<String> services = new ArrayList<>();
    private Map<String, String> selectedService = new HashMap<>();
    Spinner spinnerServiceSubtype;
    Spinner spinnerServiceType;
    String selectedServiceSubtype;
    String selectedServiceType;
    Integer maxPrice = 0;

    public ServiceDialog(@NonNull Context context, OnSaveListener onSaveListener) {
        super(context);
        this.onSaveListener = onSaveListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_service); // Set the layout for the dialog

        populateServiceSubtypeMap();
        populateSpinners();

        Button buttonAddSpinners = findViewById(R.id.buttonAddSpinners);
        ListView listView = findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, services);
        final TextView textViewMaxPrice = findViewById(R.id.textViewMaxPrice);
        // Set the adapter to the ListView
        listView.setAdapter(adapter);

        addService(buttonAddSpinners, adapter, textViewMaxPrice);
        deleteService(listView, adapter, textViewMaxPrice);
        editPrice(listView, adapter, textViewMaxPrice);

        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveListener.onSave(services);
                dismiss();
            }
        });
    }

    private void editPrice(ListView listView, ArrayAdapter<String> adapter, TextView textViewMaxPrice) {
        // Add click listener to ListView items for editing price
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Get the selected service string
                String serviceString = services.get(position);

                // Extract the price part from the service string
                String priceString = serviceString.substring(serviceString.lastIndexOf(":") + 1, serviceString.lastIndexOf("din")).trim();

                // Convert the price string to integer
                int oldPrice = Integer.parseInt(priceString);

                // Create a dialog to edit the price
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Price");
                final EditText editTextPrice = new EditText(getContext());
                editTextPrice.setText(String.valueOf(oldPrice));
                builder.setView(editTextPrice);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the new price entered by the user
                        String newPriceString = editTextPrice.getText().toString().trim();
                        int newPrice = Integer.parseInt(newPriceString);

                        // Calculate the difference between the old price and the new price
                        int priceDifference = newPrice - oldPrice;

                        // Update the maxPrice
                        maxPrice += priceDifference;

                        // Update the TextView displaying the maxPrice
                        textViewMaxPrice.setText("Max Price: " + maxPrice + "din");

                        // Update the price in the services list
                        String updatedService = serviceString.substring(0, serviceString.lastIndexOf(":") + 1) + " " + newPriceString + " din";
                        services.set(position, updatedService);

                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }

    private void deleteService(ListView listView, final ArrayAdapter<String> adapter, final TextView textViewMaxPrice){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Get the service string
                String serviceString = services.get(position);

                // Extract the price part from the service string
                String priceString = serviceString.substring(serviceString.lastIndexOf(":") + 1, serviceString.lastIndexOf("din")).trim();

                // Get the price of the item to be deleted
                int deletedPrice = Integer.parseInt(priceString);

                // Show a confirmation dialog
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Remove the item from the list and update the adapter
                                services.remove(position);
                                adapter.notifyDataSetChanged();

                                // Update the maxPrice by subtracting the price of the deleted item
                                maxPrice -= deletedPrice;
                                textViewMaxPrice.setText("Max Price: $" + maxPrice);

                                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                // Return true to consume the long click event
                return true;
            }
        });
    }

    private void addService(Button buttonAddSpinners, ArrayAdapter<String> adapter, TextView textViewMaxPrice){
        final EditText price = findViewById(R.id.editTextPrice);
        buttonAddSpinners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedServiceType = spinnerServiceType.getSelectedItem().toString();
                selectedServiceSubtype = spinnerServiceSubtype.getSelectedItem().toString();

                selectedService.put(selectedServiceType, selectedServiceSubtype);
                maxPrice += Integer.parseInt(price.getText().toString());
                String formattedService = "Service Type: " + selectedServiceType + "\nService Subtype: " + selectedServiceSubtype + "\nPrice: " + price.getText().toString() + "din";
                services.add(formattedService); // Add a copy of the selectedService
                selectedService.clear();

                textViewMaxPrice.setText("Max Price: " + maxPrice + "din");
                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void populateServiceSubtypeMap() {
        db.collection("category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String categoryName = document.getString("Name");
                                List<String> subtypeNames = new ArrayList<>();
                                // Assuming "SubCategories" is a field in your document containing a list of subcategories
                                List<DocumentReference> subcategoryReferences = (List<DocumentReference>) document.get("SubCategories");
                                if (subcategoryReferences != null) {
                                    for (DocumentReference subcategoryRef : subcategoryReferences) {
                                        subcategoryRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot subcategoryDoc) {
                                                if (subcategoryDoc.exists()) {
                                                    SubCategory subcategory = subcategoryDoc.toObject(SubCategory.class);
                                                    if (subcategory != null) {
                                                        String subcategoryName = subcategory.Name;
                                                        subtypeNames.add(subcategoryName);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                                service.put(categoryName, subtypeNames);
                            }
                            // Populate Spinners after data is fetched
                            populateSpinners();
                        } else {
                            // Handle errors
                        }
                    }
                });
    }

    private void populateSpinners() {
        // Get reference to the Spinners
        spinnerServiceType = findViewById(R.id.spinnerServiceType);
        spinnerServiceSubtype = findViewById(R.id.spinnerServiceSubtype);

        // Create an ArrayAdapter for service types and set it to the Spinner
        ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(service.keySet()));
        spinnerServiceType.setAdapter(serviceTypeAdapter);

        // Set a listener to the service type Spinner to update the subtype Spinner when a type is selected
        spinnerServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected service type
                String selectedType = (String) parent.getItemAtPosition(position);

                // Get the corresponding subtypes from the HashMap
                List<String> subtypes = service.get(selectedType);

                // Create an ArrayAdapter for subtypes and set it to the subtype Spinner
                ArrayAdapter<String> serviceSubtypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, subtypes);
                spinnerServiceSubtype.setAdapter(serviceSubtypeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
}
