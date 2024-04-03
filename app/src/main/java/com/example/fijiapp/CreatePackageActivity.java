package com.example.fijiapp;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.adapters.ProductCheckBoxAdapter;
import com.example.fijiapp.adapters.ServiceCheckBoxAdapter;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatePackageActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText discountEditText;
    private CheckBox visibleEditText;
    private CheckBox availableEditText;
    private Spinner categorySpinner;
    private List<Product> products;
    private List<Service> services;
    private EditText eventTypeEditText;
    private EditText priceEditText;
    private EditText imagesEditText;
    private EditText bookingDeadlineEditText;
    private EditText cancellationDeadlineEditText;
    private Button createPackageButton;
    private RecyclerView recyclerViewProducts, recyclerViewServices, recyclerView,recyclerViewForProducts;
    private ServiceCheckBoxAdapter serviceCheckBoxAdapter;
    private ProductCheckBoxAdapter productCheckBoxAdapter;
    Map<String, List<String>> categorySubcategoryMap = new HashMap<>();

    public CreatePackageActivity(){}
    public CreatePackageActivity(EditText nameEditText, EditText descriptionEditText, EditText discountEditText, CheckBox visibleEditText, CheckBox availableEditText, Spinner categorySpinner, List<Product> products, List<Service> services, EditText eventTypeEditText, EditText priceEditText, EditText imagesEditText, EditText bookingDeadlineEditText, EditText cancellationDeadlineEditText, Button createPackageButton) {
        this.nameEditText = nameEditText;
        this.descriptionEditText = descriptionEditText;
        this.discountEditText = discountEditText;
        this.visibleEditText = visibleEditText;
        this.availableEditText = availableEditText;
        this.categorySpinner = categorySpinner;
        this.products = products;
        this.services = services;
        this.eventTypeEditText = eventTypeEditText;
        this.priceEditText = priceEditText;
        this.imagesEditText = imagesEditText;
        this.bookingDeadlineEditText = bookingDeadlineEditText;
        this.cancellationDeadlineEditText = cancellationDeadlineEditText;
        this.createPackageButton = createPackageButton;
    }

    public CreatePackageActivity(int contentLayoutId, EditText nameEditText, EditText descriptionEditText, EditText discountEditText, CheckBox visibleEditText, CheckBox availableEditText, Spinner categorySpinner, List<Product> products, List<Service> services, EditText eventTypeEditText, EditText priceEditText, EditText imagesEditText, EditText bookingDeadlineEditText, EditText cancellationDeadlineEditText, Button createPackageButton) {
        super(contentLayoutId);
        this.nameEditText = nameEditText;
        this.descriptionEditText = descriptionEditText;
        this.discountEditText = discountEditText;
        this.visibleEditText = visibleEditText;
        this.availableEditText = availableEditText;
        this.categorySpinner = categorySpinner;
        this.products = products;
        this.services = services;
        this.eventTypeEditText = eventTypeEditText;
        this.priceEditText = priceEditText;
        this.imagesEditText = imagesEditText;
        this.bookingDeadlineEditText = bookingDeadlineEditText;
        this.cancellationDeadlineEditText = cancellationDeadlineEditText;
        this.createPackageButton = createPackageButton;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);



        imagesEditText = findViewById(R.id.imagesEditText);






        List<Service> servicesNotPackage = new ArrayList<>();


        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        discountEditText = findViewById(R.id.discountEditText);
        visibleEditText = findViewById(R.id.visibleEditText);
        availableEditText = findViewById(R.id.availableEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        eventTypeEditText = findViewById(R.id.eventTypeEditText);
        priceEditText = findViewById(R.id.priceEditText);
        imagesEditText = findViewById(R.id.imagesEditText);
        bookingDeadlineEditText = findViewById(R.id.bookingDeadlineEditText);
        cancellationDeadlineEditText = findViewById(R.id.cancellationDeadlineEditText);
        createPackageButton = findViewById(R.id.createPackageButton);

        List<String> fotoSubcategories = Arrays.asList("Fotografije i Albumi", "Radosnice", "Maturanti","Custom");
        List<String> laptopSubcategories = Arrays.asList("Dell", "HP", "Lenovo","Custom");

        categorySubcategoryMap.put("Foto i Video", fotoSubcategories);
        categorySubcategoryMap.put("Laptops", laptopSubcategories);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>(categorySubcategoryMap.keySet()));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);


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
        Service service3 = new Service(
                "Foto i video",
                "Videografija",
                "Ajoj dragisha",
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
        Product product1 = new Product(
                "Electronics",
                "Smartphones",
                "Alo X",
                "lololo igh-performance smartphone with great features",
                500,
                50,
                1450,
                new ArrayList<>(Arrays.asList("https://example.com/picture1.jpg", "https://example.com/picture2.jpg")),
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
                new ArrayList<>(Arrays.asList("https://example.com/picture1.jpg", "https://example.com/picture2.jpg")),
                "SVADBA",
                "Yes",
                "Yes"
        );

        Product product3= new Product(
                "STA STA",
                "Smartphones",
                "Joj Zoro",
                "oooooo High-performance smartphone with great features",
                1500,
                50,
                450,
                new ArrayList<>(Arrays.asList("https://example.com/picture1.jpg", "https://example.com/picture2.jpg")),
                "SVADBA",
                "Yes",
                "Yes"
        );

        List<Product> products = new ArrayList<>();
        List<Product> products1 = new ArrayList<>();

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products1.add(product3);



        services.add(service1);
        services.add(service2);
        services.add(service3);
        List<Service> services2 = new ArrayList<>();
        services2.add(service1);
        services2.add(service2);

        recyclerViewForProducts = findViewById(R.id.recyclerViewForProducts);
        recyclerViewForProducts.setLayoutManager(new LinearLayoutManager(this));

        productCheckBoxAdapter = new ProductCheckBoxAdapter(products, this);
        recyclerViewForProducts.setAdapter(productCheckBoxAdapter);



        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        serviceCheckBoxAdapter = new ServiceCheckBoxAdapter(this, services);
        recyclerView.setAdapter(serviceCheckBoxAdapter);


        for(Service service : services2)
        {
            Log.d("tag","PEJOOOOO" + service.getName());

        }

        for(Service service : services)
        {
            Log.d("tag","PEJOOOOO" + service.getName());

        }


        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View view = recyclerView.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout layout = (LinearLayout) view;
                CheckBox checkBox = layout.findViewById(R.id.checkBox);

                for (Service service : services2) {
                    if (checkBox.getText().toString().equals(service.getName())) {
                        checkBox.setChecked(true);
                        break;
                    }
                }
            }
        }


        servicesNotPackage.add(service3);
    }
}
