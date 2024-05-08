package com.example.fijiapp.repository;

import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.EventType;
import com.example.fijiapp.model.Product;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductRepository {

    private final FirebaseFirestore database;
    private final CollectionReference productRef;

    public ProductRepository() {
        database = FirebaseFirestore.getInstance();
        productRef = database.collection("products");
    }

    public Task<Void> updateProduct(Product product,String productId) {
        if (productId == null) {
            return Tasks.forException(new IllegalArgumentException("Product ID is null"));
        }

        return productRef.document(productId).set(product);
    }

    public Task<Product> getProductById(String productId) {
        return productRef.document(productId).get().continueWith(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    return document.toObject(Product.class);
                }
            }
            return null;
        });
    }
}
