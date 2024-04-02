package com.example.fijiapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.adapters.PackageAdapter;
import com.example.fijiapp.adapters.ProductAdapter;
import com.example.fijiapp.adapters.ServiceAdapter;
import com.example.fijiapp.model.Package;
import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;

import java.util.ArrayList;
import java.util.List;

// LandingPageActivity.java
public class LandingPageActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton showProductsRadioButton;
    private RadioButton showServicesRadioButton;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ServiceAdapter serviceAdapter;
    private PackageAdapter packageAdapter;
    private List<Product> productList;
    private List<Service> serviceList;
    private List<Package> packageList;

    // Fields for filtering
    private EditText titleFilterEditText;
    private EditText categoryFilterEditText;
    private EditText subcategoryFilterEditText;
    private EditText minPriceFilterEditText;
    private EditText maxPriceFilterEditText;
    private EditText locationFilterEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewProducts);

        radioGroup = findViewById(R.id.radioGroup);
        showProductsRadioButton = findViewById(R.id.showProductsRadioButton);
        showServicesRadioButton = findViewById(R.id.showServicesRadioButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productFilterInit();
        serviceFilterInit();
        packageFilterInit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.showProductsRadioButton) {
                    // Show products
                    showProducts();
                } else if (checkedId == R.id.showServicesRadioButton) {
                    // Show services
                    showServices();
                } else if (checkedId == R.id.showPackagesRadioButton) {
                    // Show packages
                    showPackages();
                }
            }
        });
    }

    private void showPackages() {
        recyclerView.setAdapter(packageAdapter);
    }

    private void showProducts() {
        recyclerView.setAdapter(productAdapter);
    }

    // Method to show services
    private void showServices() {
        recyclerView.setAdapter(serviceAdapter);
    }

    private void productFilterInit(){
        // Dummy product list, replace with your actual data
        productList = getDummyProductList();

        // Initialize Adapter
        productAdapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(productAdapter);

        // Initialize filter fields
        titleFilterEditText = findViewById(R.id.titleFilterEditText);
        categoryFilterEditText = findViewById(R.id.categoryFilterEditText);
        subcategoryFilterEditText = findViewById(R.id.subcategoryFilterEditText);
        minPriceFilterEditText = findViewById(R.id.minPriceFilterEditText);
        maxPriceFilterEditText = findViewById(R.id.maxPriceFilterEditText);

        // Add text change listeners for filtering
        titleFilterEditText.addTextChangedListener(filterTextWatcher);
        categoryFilterEditText.addTextChangedListener(filterTextWatcher);
        subcategoryFilterEditText.addTextChangedListener(filterTextWatcher);
        minPriceFilterEditText.addTextChangedListener(filterTextWatcher);
        maxPriceFilterEditText.addTextChangedListener(filterTextWatcher);
    }

    private void serviceFilterInit() {
        // Dummy service list, replace with your actual data
        serviceList = getDummyServiceList();

        // Initialize Adapter
        serviceAdapter = new ServiceAdapter(serviceList, this);
        recyclerView.setAdapter(serviceAdapter);

        // Initialize filter fields for services
        titleFilterEditText = findViewById(R.id.titleFilterEditText);
        categoryFilterEditText = findViewById(R.id.categoryFilterEditText);
        subcategoryFilterEditText = findViewById(R.id.subcategoryFilterEditText);
        minPriceFilterEditText = findViewById(R.id.minPriceFilterEditText);
        maxPriceFilterEditText = findViewById(R.id.maxPriceFilterEditText);
        locationFilterEditText = findViewById(R.id.locationFilterEditText);

        // Add text change listeners for filtering
        titleFilterEditText.addTextChangedListener(filterTextWatcher);
        categoryFilterEditText.addTextChangedListener(filterTextWatcher);
        subcategoryFilterEditText.addTextChangedListener(filterTextWatcher);
        minPriceFilterEditText.addTextChangedListener(filterTextWatcher);
        maxPriceFilterEditText.addTextChangedListener(filterTextWatcher);
        locationFilterEditText.addTextChangedListener(filterTextWatcher); // Add location filter text watcher
    }

    private void packageFilterInit() {
        // Dummy package list, replace with your actual data
        packageList = getDummyPackageList();

        // Initialize Adapter
        packageAdapter = new PackageAdapter(packageList, this);
        recyclerView.setAdapter(packageAdapter);

        // Initialize filter fields for packages
        titleFilterEditText = findViewById(R.id.titleFilterEditText);
        categoryFilterEditText = findViewById(R.id.categoryFilterEditText);
        minPriceFilterEditText = findViewById(R.id.minPriceFilterEditText);
        maxPriceFilterEditText = findViewById(R.id.maxPriceFilterEditText);

        // Add text change listeners for filtering
        titleFilterEditText.addTextChangedListener(filterTextWatcher);
        categoryFilterEditText.addTextChangedListener(filterTextWatcher);
        minPriceFilterEditText.addTextChangedListener(filterTextWatcher);
        maxPriceFilterEditText.addTextChangedListener(filterTextWatcher);
    }


    // Method to get dummy product list (replace with your actual data source)
    private List<Product> getDummyProductList() {
        List<Product> productList = new ArrayList<>();

        // Add dummy products
        productList.add(new Product("Category1", "SubCategory1", "Product1", "Description1", 100, 10, 90, new ArrayList<>(), "Event1", "Available", "Visible"));
        productList.add(new Product("Category2", "SubCategory2", "Product2", "Description2", 200, 20, 180, new ArrayList<>(), "Event2", "Available", "Visible"));
        // Add more products...

        return productList;
    }

    private List<Service> getDummyServiceList() {
        List<Service> serviceList = new ArrayList<>();

        // Add dummy services
        serviceList.add(new Service("Category1", "SubCategory1", "Service1", "Description1", new ArrayList<>(), "Specifics1", 50, 100, 2, "Location1", 0, new ArrayList<>(), new ArrayList<>(), "BookingDeadline1", "CancellationDeadline1", "AcceptanceMode1", "Available", "Visible"));
        serviceList.add(new Service("Category2", "SubCategory2", "Service2", "Description2", new ArrayList<>(), "Specifics2", 80, 150, 3, "Location2", 10, new ArrayList<>(), new ArrayList<>(), "BookingDeadline2", "CancellationDeadline2", "AcceptanceMode2", "Available", "Visible"));
        // Add more services...

        return serviceList;
    }

    private List<Package> getDummyPackageList() {
        List<Package> packageList = new ArrayList<>();

        // Add dummy packages
        List<Product> products1 = new ArrayList<>();
        products1.add(new Product("Category1", "SubCategory1", "Product1", "Description1", 100, 10, 90, new ArrayList<>(), "Event1", "Available", "Visible"));
        products1.add(new Product("Category2", "SubCategory2", "Product2", "Description2", 200, 20, 180, new ArrayList<>(), "Event2", "Available", "Visible"));

        List<Service> services1 = new ArrayList<>();
        services1.add(new Service("Category1", "SubCategory1", "Service1", "Description1", new ArrayList<>(), "Specifics1", 50, 100, 2, "Location1", 0, new ArrayList<>(), new ArrayList<>(), "BookingDeadline1", "CancellationDeadline1", "AcceptanceMode1", "Available", "Visible"));
        services1.add(new Service("Category2", "SubCategory2", "Service2", "Description2", new ArrayList<>(), "Specifics2", 80, 150, 3, "Location2", 10, new ArrayList<>(), new ArrayList<>(), "BookingDeadline2", "CancellationDeadline2", "AcceptanceMode2", "Available", "Visible"));

        packageList.add(new Package("Package1", "Description1", 10, "Visible", "Available", "Category1", products1, services1, "Event1", 300, new ArrayList<>(), "BookingDeadline1", "CancellationDeadline1"));
        packageList.add(new Package("Package2", "Description2", 15, "Visible", "Available", "Category2", products1, services1, "Event2", 400, new ArrayList<>(), "BookingDeadline2", "CancellationDeadline2"));


        // Add more packages...

        return packageList;
    }

    // TextWatcher for filtering
    private TextWatcher filterTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void afterTextChanged(Editable editable) {
            filterProducts();
            filterServices();
            filterPackages();
        }
    };

    // Method to filter products based on filter fields
    private void filterProducts() {
        String titleFilter = titleFilterEditText.getText().toString().toLowerCase();
        String categoryFilter = categoryFilterEditText.getText().toString().toLowerCase();
        String subcategoryFilter = subcategoryFilterEditText.getText().toString().toLowerCase();
        int minPriceFilter = !TextUtils.isEmpty(minPriceFilterEditText.getText().toString()) ? Integer.parseInt(minPriceFilterEditText.getText().toString()) : Integer.MIN_VALUE;
        int maxPriceFilter = !TextUtils.isEmpty(maxPriceFilterEditText.getText().toString()) ? Integer.parseInt(maxPriceFilterEditText.getText().toString()) : Integer.MAX_VALUE;

        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            // Filter by title
            if (!product.getTitle().toLowerCase().contains(titleFilter))
                continue;

            // Filter by category
            if (!product.getCategory().toLowerCase().contains(categoryFilter))
                continue;

            // Filter by subcategory
            if (!product.getSubCategory().toLowerCase().contains(subcategoryFilter))
                continue;

            // Filter by price
            if (product.getPrice() < minPriceFilter || product.getPrice() > maxPriceFilter)
                continue;

            // If all filters pass, add product to filtered list
            filteredList.add(product);
        }

        // Update RecyclerView with filtered list
        productAdapter.filterList(filteredList);
    }

    private void filterServices() {
        String titleFilter = titleFilterEditText.getText().toString().toLowerCase();
        String categoryFilter = categoryFilterEditText.getText().toString().toLowerCase();
        String subcategoryFilter = subcategoryFilterEditText.getText().toString().toLowerCase();
        String locationFilter = locationFilterEditText.getText().toString().toLowerCase(); // Get location filter value
        int minPriceFilter = !TextUtils.isEmpty(minPriceFilterEditText.getText().toString()) ? Integer.parseInt(minPriceFilterEditText.getText().toString()) : Integer.MIN_VALUE;
        int maxPriceFilter = !TextUtils.isEmpty(maxPriceFilterEditText.getText().toString()) ? Integer.parseInt(maxPriceFilterEditText.getText().toString()) : Integer.MAX_VALUE;

        List<Service> filteredList = new ArrayList<>();

        for (Service service : serviceList) {
            // Filter by title
            if (!service.getName().toLowerCase().contains(titleFilter))
                continue;

            // Filter by category
            if (!service.getCategory().toLowerCase().contains(categoryFilter))
                continue;

            // Filter by subcategory
            if (!service.getSubCategory().toLowerCase().contains(subcategoryFilter))
                continue;

            // Filter by location
            if (!service.getLocation().toLowerCase().contains(locationFilter))
                continue;

            // Filter by price
            // Assuming the service has a pricePerHour attribute
            if (service.getPricePerHour() < minPriceFilter || service.getPricePerHour() > maxPriceFilter)
                continue;

            // If all filters pass, add service to filtered list
            filteredList.add(service);
        }

        // Update RecyclerView with filtered list
        serviceAdapter.filterList(filteredList);
    }

    private void filterPackages() {
        String titleFilter = titleFilterEditText.getText().toString().toLowerCase();
        String categoryFilter = categoryFilterEditText.getText().toString().toLowerCase();
        int minPriceFilter = !TextUtils.isEmpty(minPriceFilterEditText.getText().toString()) ? Integer.parseInt(minPriceFilterEditText.getText().toString()) : Integer.MIN_VALUE;
        int maxPriceFilter = !TextUtils.isEmpty(maxPriceFilterEditText.getText().toString()) ? Integer.parseInt(maxPriceFilterEditText.getText().toString()) : Integer.MAX_VALUE;

        List<Package> filteredList = new ArrayList<>();

        for (Package packageItem : packageList) {
            // Filter by title
            if (!packageItem.getName().toLowerCase().contains(titleFilter))
                continue;

            // Filter by category
            if (!packageItem.getCategory().toLowerCase().contains(categoryFilter))
                continue;

            // Filter by price
            if (packageItem.getPrice() < minPriceFilter || packageItem.getPrice() > maxPriceFilter)
                continue;

            // If all filters pass, add package to filtered list
            filteredList.add(packageItem);
        }

        // Update RecyclerView with filtered list
        packageAdapter.filterList(filteredList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove text change listeners to avoid memory leaks
        titleFilterEditText.removeTextChangedListener(filterTextWatcher);
        categoryFilterEditText.removeTextChangedListener(filterTextWatcher);
        subcategoryFilterEditText.removeTextChangedListener(filterTextWatcher);
        minPriceFilterEditText.removeTextChangedListener(filterTextWatcher);
        maxPriceFilterEditText.removeTextChangedListener(filterTextWatcher);
        locationFilterEditText.removeTextChangedListener(filterTextWatcher); // Remove location filter text watcher
    }
}