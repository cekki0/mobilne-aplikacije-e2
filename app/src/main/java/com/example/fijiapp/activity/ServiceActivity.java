package com.example.fijiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class ServiceActivity extends AppCompatActivity implements ServiceAdapter.OnItemClickListener{
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
                new ArrayList<>(Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "https://static.toiimg.com/thumb/msid-53891743,width-748,height-499,resizemode=4,imgsize-152022/.jpg","https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/2/h/0/medium-beautifull-nature-wall-picture-photographic-paper-14x20-original-imag6jtayz9vphgx.jpeg?q=90&crop=false")),
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
                new ArrayList<>(Arrays.asList("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg/800px-Mona_Lisa%2C_by_Leonardo_da_Vinci%2C_from_C2RMF_retouched.jpg", "https://static.toiimg.com/thumb/msid-53891743,width-748,height-499,resizemode=4,imgsize-152022/.jpg","https://rukminim2.flixcart.com/image/850/1000/xif0q/poster/2/h/0/medium-beautifull-nature-wall-picture-photographic-paper-14x20-original-imag6jtayz9vphgx.jpeg?q=90&crop=false")),
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

        adapter = new ServiceAdapter(services, this,this);
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


    public void createServicePage(View view){
        Intent intent =  new Intent(this,CreateServiceActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClick(Service service) {

    }
}
