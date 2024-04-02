package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.model.Product;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    private List<Product> products = new ArrayList<>();
    private ProductAdapter adapter;

public ProductsActivity()
{}
    public ProductsActivity(List<Product> products, ProductAdapter adapter) {
        this.products = products;
        this.adapter = adapter;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Product product1 = new Product(
                "Electronics",
                "Smartphones",
                "Alo X",
                "lololo igh-performance smartphone with great features",
                500,
                50,
                1450,
                new ArrayList<>(Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "https://static.toiimg.com/thumb/msid-53891743,width-748,height-499,resizemode=4,imgsize-152022/.jpg","https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/2/h/0/medium-beautifull-nature-wall-picture-photographic-paper-14x20-original-imag6jtayz9vphgx.jpeg?q=90&crop=false")),
                "SVADBA,KRSTENJE",
                "Yes",
                "No"
        );

        Product product2 = new Product(
                "Ojsa",
                "Baco",
                "Smartphone X",
                "likvid igh-performance smartphone with great features",
                5000,
                50,
                900,
                new ArrayList<>(Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "https://static.toiimg.com/thumb/msid-53891743,width-748,height-499,resizemode=4,imgsize-152022/.jpg","https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/2/h/0/medium-beautifull-nature-wall-picture-photographic-paper-14x20-original-imag6jtayz9vphgx.jpeg?q=90&crop=false")),
                "SVADBA",
                "Yes",
                "Yes"
        );

        Product product3= new Product(
                "STA STA",
                "Smartphones",
                "Smartphone X",
                "oooooo High-performance smartphone with great features",
                1500,
                50,
                450,
                new ArrayList<>(Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "https://static.toiimg.com/thumb/msid-53891743,width-748,height-499,resizemode=4,imgsize-152022/.jpg","https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/2/h/0/medium-beautifull-nature-wall-picture-photographic-paper-14x20-original-imag6jtayz9vphgx.jpeg?q=90&crop=false")),
                "SVADBA",
                "Yes",
                "Yes"
        );


        products.add(product1);
        products.add(product2);
        products.add(product3);


        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductAdapter(products, this);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


    }

    private void filter(String query) {
        List<Product> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(products);
        } else {
            String queryLowerCase = query.toLowerCase().trim();
            try {
                int priceFilter = Integer.parseInt(queryLowerCase);
                for (Product product : products) {
                    if (product.Event.toLowerCase().contains(queryLowerCase) ||
                            product.Category.toLowerCase().contains(queryLowerCase) ||
                            product.SubCategory.toLowerCase().contains(queryLowerCase) ||
                            product.Available.toLowerCase().contains(queryLowerCase) ||
                            (product.Price <= priceFilter) ||
                            product.Description.toLowerCase().contains(queryLowerCase)) {
                        filteredList.add(product);
                    }
                }
            } catch (NumberFormatException e) {
                for (Product product : products) {
                    if (product.Event.toLowerCase().contains(queryLowerCase) ||
                            product.Category.toLowerCase().contains(queryLowerCase) ||
                            product.SubCategory.toLowerCase().contains(queryLowerCase) ||
                            product.Available.toLowerCase().contains(queryLowerCase) ||
                            product.Description.toLowerCase().contains(queryLowerCase)) {
                        filteredList.add(product);
                    }
                }
            }
        }
        adapter.filterList(filteredList);
    }

    public void createProductPage(View view){
    Intent intent = new Intent(this,CreateProductActivity.class);
    startActivity(intent);
    }




}
