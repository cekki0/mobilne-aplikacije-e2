package com.example.fijiapp.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.fijiapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PriceListActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricelist);

        db = FirebaseFirestore.getInstance();

        Button downloadPdfButton = findViewById(R.id.downloadPdfButton);
        downloadPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAllDataAndExportToPdf();
            }
        });

        Button productButton = findViewById(R.id.productButton);
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PriceListActivity.this, ProductsActivity.class));
            }
        });

        Button serviceButton = findViewById(R.id.serviceButton);
        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PriceListActivity.this, ServiceActivity.class));
            }
        });

        Button packageButton = findViewById(R.id.packageButton);
        packageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PriceListActivity.this, PackageActivity.class));
            }
        });
    }

    private void fetchAllDataAndExportToPdf() {
        List<String> collections = new ArrayList<>();
        collections.add("products");
        collections.add("services");
        collections.add("packages");

        List<QuerySnapshot> snapshots = new ArrayList<>();

        for (String collection : collections) {
            db.collection(collection)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        snapshots.add(queryDocumentSnapshots);
                        if (snapshots.size() == collections.size()) {
                            createPdf(snapshots, collections);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PriceListActivity.this, "Failed to fetch data from Firestore", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void createPdf(List<QuerySnapshot> snapshots, List<String> collections) {
        PdfDocument document = new PdfDocument();
        int pageNumber = 1;
        int yPosition = 50;
        int pageWidth = 300;
        int pageHeight = 600;

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12f);
        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextSize(16f);
        titlePaint.setFakeBoldText(true);

        for (int i = 0; i < snapshots.size(); i++) {
            QuerySnapshot snapshot = snapshots.get(i);
            String collectionName = collections.get(i);
            String title = collectionName.substring(0, 1).toUpperCase() + collectionName.substring(1).toLowerCase();

            if (yPosition > pageHeight - 50) {
                document.finishPage(page);
                pageNumber++;
                pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create();
                page = document.startPage(pageInfo);
                yPosition = 50;
            }

            // Draw the title
            Canvas canvas = page.getCanvas();
            canvas.drawText(title, 10, yPosition, titlePaint);
            yPosition += titlePaint.descent() - titlePaint.ascent() + 20;

            for (QueryDocumentSnapshot documentSnapshot : snapshot) {
                if (yPosition > pageHeight - 50) {
                    document.finishPage(page);
                    pageNumber++;
                    pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create();
                    page = document.startPage(pageInfo);
                    yPosition = 50;
                }

                String name = documentSnapshot.getString("Name");
                if (name == null || name=="null" || name.isEmpty()) {
                    name = documentSnapshot.getString("name");
                    if (name == null || name.isEmpty()) {
                        name = documentSnapshot.getString("Title");
                    }
                }

                String description = documentSnapshot.getString("description");
                if (description == null || description.isEmpty()) {
                    description = documentSnapshot.getString("Description");
                }

                Double price = documentSnapshot.getDouble("price");
                if (price == null) {
                    price = documentSnapshot.getDouble("totalPrice");
                }
                if (price == null) {
                    price = documentSnapshot.getDouble("Price");
                }

                StringBuilder dataBuilder = new StringBuilder();
                dataBuilder.append("Name: ").append(name).append("\n");
                dataBuilder.append("Description: ").append(description).append("\n");
                if (price != null) {
                    dataBuilder.append("Price: $").append(price).append("\n");
                }
                dataBuilder.append("\n");

                String data = dataBuilder.toString();
                canvas.drawText(data, 10, yPosition, paint);

                yPosition += paint.descent() - paint.ascent() + 20; 
            }
        }

        document.finishPage(page);
        saveAndSharePdf(document, "all_collections_price_list.pdf");
    }

    private void saveAndSharePdf(PdfDocument document, String fileName) {
        File pdfFile = new File(getExternalFilesDir(null), fileName);
        try {
            document.writeTo(new FileOutputStream(pdfFile));
            document.close();
            openPdf(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdf(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
