package com.example.fijiapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.ServiceAdapter;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ServiceActivity extends AppCompatActivity {
    private List<Service> services = new ArrayList<>();
    private ServiceAdapter adapter;


    public ServiceActivity() {
    }

    public ServiceActivity(List<Service> services, ServiceAdapter adapter) {
        this.services = services;
        this.adapter = adapter;
    }

    public ServiceActivity(int contentLayoutId, List<Service> services, ServiceAdapter adapter) {
        super(contentLayoutId);
        this.services = services;
        this.adapter = adapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);


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


        RecyclerView recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ServiceAdapter(services, this);
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
        List<Service> filteredList = new ArrayList<>();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(services);
        } else {
            String queryLowerCase = query.toLowerCase().trim();
            try {
                int priceFilter = Integer.parseInt(queryLowerCase);
                for (Service service : services) {
                    if (service.getCategory().toLowerCase().contains(queryLowerCase) ||
                            service.getSubCategory().toLowerCase().contains(queryLowerCase) ||
                            service.getAvailable().toLowerCase().contains(queryLowerCase) ||
                            (service.getPricePerHour() <= priceFilter)) {
                        filteredList.add(service);
                    } else {
                        for (String eventType : service.getEventTypes()) {
                            if (eventType.toLowerCase().contains(queryLowerCase)) {
                                filteredList.add(service);
                                break;
                            }
                        }
                        for (String serviceProvider : service.getServiceProviders()) {
                            if (serviceProvider.toLowerCase().contains(queryLowerCase)) {
                                filteredList.add(service);
                                break;
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                for (Service service : services) {
                    if (service.getCategory().toLowerCase().contains(queryLowerCase) ||
                            service.getSubCategory().toLowerCase().contains(queryLowerCase) ||
                            service.getAvailable().toLowerCase().contains(queryLowerCase)) {
                        filteredList.add(service);
                    } else {
                        for (String eventType : service.getEventTypes()) {
                            if (eventType.toLowerCase().contains(queryLowerCase)) {
                                filteredList.add(service);
                                break;
                            }
                        }
                        for (String serviceProvider : service.getServiceProviders()) {
                            if (serviceProvider.toLowerCase().contains(queryLowerCase)) {
                                filteredList.add(service);
                                break;
                            }
                        }

                    }

                }
            }
        }
        adapter.filterList(filteredList);
    }





}
