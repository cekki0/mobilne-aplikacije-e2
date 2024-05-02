package com.example.fijiapp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.PackageAdapter;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.adapters.ProductCheckBoxAdapter;
import com.example.fijiapp.adapters.ServiceAdapter;
import com.example.fijiapp.adapters.ServiceCheckBoxAdapter;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdatePackageActivity  extends AppCompatActivity  {
    private EditText editTextName, editTextDescription, editTextDiscount, editTextEventType, editTextPrice,
            editTextBookingDeadline, editTextCancellationDeadline, imagesEditText, servicesEditText;
    private CheckBox checkBoxVisible, checkBoxAvailable;
    private RecyclerView recyclerViewProducts, recyclerViewServices, recyclerView,recyclerViewForProducts;
    private Button buttonAddProduct, buttonAddService, buttonSaveChanges;

    private ProductAdapter productAdapter;
    private ProductCheckBoxAdapter productCheckBoxAdapter;
    private ServiceAdapter serviceAdapter;;
    private ServiceCheckBoxAdapter serviceCheckBoxAdapter;

    private List<Product> productList = new ArrayList<>();
    private List<Service> serviceList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_package);

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));


        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDiscount = findViewById(R.id.editTextDiscount);
        editTextEventType = findViewById(R.id.editTextEventType);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextBookingDeadline = findViewById(R.id.editTextBookingDeadline);
        //servicesEditText=findViewById(R.id.servicesTextName);
        editTextCancellationDeadline = findViewById(R.id.editTextCancellationDeadline);
        imagesEditText = findViewById(R.id.imagesEditText);


        checkBoxVisible = findViewById(R.id.checkBoxVisible);
        checkBoxAvailable = findViewById(R.id.checkBoxAvailable);


        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewServices = findViewById(R.id.recyclerViewServices);


        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddService = findViewById(R.id.buttonAddService);

        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);


        productAdapter = new ProductAdapter(productList, this);
        List<Service> servicesNotPackage = new ArrayList<>();
        recyclerViewProducts.setAdapter(productAdapter);

        Package package1 = getIntent().getParcelableExtra("package");
        if (package1 != null) {

            editTextName.setText(package1.getName());
            editTextDescription.setText(package1.getDescription());
            editTextDiscount.setText(String.valueOf(package1.getDiscount()));
            editTextEventType.setText(package1.getEventType());
            editTextPrice.setText(String.valueOf(package1.getPrice()));
            editTextBookingDeadline.setText(package1.getBookingDeadline());
            editTextCancellationDeadline.setText(package1.getCancellationDeadline());


            if (package1.getImages() != null && !package1.getImages().isEmpty()) {
                StringBuilder imagesText = new StringBuilder();
                for (String image : package1.getImages()) {
                    imagesText.append(image).append("\n");
                }
                imagesEditText.setText(imagesText.toString());
            }


            checkBoxVisible.setChecked(package1.getVisible().equals("Da"));
            checkBoxAvailable.setChecked(package1.getAvailable().equals("Da"));


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
}
