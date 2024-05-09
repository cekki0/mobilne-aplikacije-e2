package com.example.fijiapp.repository;

import com.example.fijiapp.model.SubCategoryProposal;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SubCategoryProposalRepository {

    private CollectionReference subCategoryProposalRef;

    public SubCategoryProposalRepository() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        subCategoryProposalRef = database.collection("proposals");
    }

    public Task<DocumentReference> addCategory(SubCategoryProposal subCategoryProposal) {
        return subCategoryProposalRef.add(subCategoryProposal);
    }

    public Task<Void> updateCategory(SubCategoryProposal subCategoryProposal) {
        String categoryId = subCategoryProposal.Id;
        if (categoryId == null) {
            return Tasks.forException(new IllegalArgumentException("SubCategoryProposal ID is null"));
        }

        return subCategoryProposalRef.document(categoryId).set(subCategoryProposal);
    }

    public Task<Void> deleteCategory(SubCategoryProposal subCategoryProposal) {
        String categoryId = subCategoryProposal.Id;
        return subCategoryProposalRef.document(categoryId).delete();
    }

    public Task<QuerySnapshot> getAllSubCategoryProposals() {
        return subCategoryProposalRef.get();
    }
    
}
