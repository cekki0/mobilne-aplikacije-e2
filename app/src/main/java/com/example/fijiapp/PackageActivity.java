package com.example.fijiapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.PackageAdapter;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.view.View;

import android.view.View;
public class PackageActivity extends AppCompatActivity {
    List<Package> packageList = new ArrayList<>();
    PackageAdapter adapter;

    public PackageActivity() {
    }

    public PackageActivity(List<Package> packageList, PackageAdapter adapter) {
        this.packageList = packageList;
        this.adapter = adapter;
    }

    public PackageActivity(int contentLayoutId, List<Package> packageList, PackageAdapter adapter) {
        super(contentLayoutId);
        this.packageList = packageList;
        this.adapter = adapter;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);


        List<Product> products = new ArrayList<>();
        Product product1 = new Product(
                "Electronics",
                "Smartphones",
                "Alo X",
                "lololo igh-performance smartphone with great features",
                500,
                50,
                1450,
                new ArrayList<>(Arrays.asList("https://example.com/picture1.jpg", "https://example.com/picture2.jpg")),
                "SVADBA",
                "Yes",
                "Yes"
        );

        Product product2 = new Product(
                "Ojsa",
                "Baco",
                "Smartphone X",
                "likvid igh-performance smartphone with great features",
                5000,
                50,
                900,
                new ArrayList<>(Arrays.asList("https://example.com/picture1.jpg", "https://example.com/picture2.jpg")),
                "SVADBA",
                "Yes",
                "Yes"
        );

        products.add(product1);
        products.add(product2);
        List<Service> services = new ArrayList<>();
        Service service1 = new Service(
                "Foto i video",
                "Snimanje dronom",
                "Snimanje dronom",
                "Ovo je snimanje iz vazduha sa dronom",
                Arrays.asList("Slika 1", "Slika 2", "Slika 3"),
                "Ne radimo praznicima",
                3000,
                6000,
                2,
                "Okolina Novog Sada",
                0,
                Arrays.asList("LipFi", "Krle"),
                Arrays.asList("Vencanje", "Krstenje", "1 rodjendan"),
                "12 meseci pre termina",
                "2 dana pre termina",
                "Rucno",
                "Da",
                "Da"
        );

        Service service2 = new Service(
                "Foto i video",
                "Videografija",
                "Snimanje kamerom 4k",
                "Ovo je snimanje u 4k rezoluciji",
                Arrays.asList("Slika 1", "Slika 2", "Slika 3"),
                "",
                5000,
                0, //racun u odn na trajanje
                1,
                "Okolina Novog Sada",
                0,
                Arrays.asList("Dragan", "Ceki"),
                Arrays.asList("Vencanje", "Krstenje", "1 rodjendan"),
                "12 meseci pre termina",
                "2 dana pre termina",
                "Rucno",
                "Da",
                "Da"
        );


        services.add(service1);
        services.add(service2);


        Package package1 = new Package(
                "Dragisin paket",
                "kdowkwe",
                10,
                "Da",
                "Da",
                "Neka dragova",
                products,
                services,
                "SVADBA",
                2000,
                new ArrayList<>(Arrays.asList("ok", "aj")),
                "kad oces",
                "Do kad oces"
        );


        packageList.add(package1);
        RecyclerView recyclerView = findViewById(R.id.packageRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PackageAdapter(packageList, this);
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
        List<Package> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(packageList);
        } else {
            String queryLowerCase = query.toLowerCase().trim();
            try {
                int priceFilter = Integer.parseInt(queryLowerCase);
                for (Package packagee : packageList) {
                    if (packagee.getEventType().toLowerCase().contains(queryLowerCase) ||
                            packagee.getCategory().toLowerCase().contains(queryLowerCase) ||
                            (packagee.getPrice() <= priceFilter)) {
                        filteredList.add(packagee);
                    }
                }
            } catch (NumberFormatException e) {
                for (Package packagee : packageList) {
                    if (packagee.getEventType().toLowerCase().contains(queryLowerCase) ||
                            packagee.getCategory().toLowerCase().contains(queryLowerCase)) {
                        filteredList.add(packagee);
                    }
                }
            }
            adapter.filterList(filteredList);
        }


    }
    public void createPackagePage(View view){
        Intent intent = new Intent(this,CreatePackageActivity.class);
        startActivity(intent);
    }
}
