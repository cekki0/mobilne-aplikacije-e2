package com.example.fijiapp.service;

import android.util.Log;

import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.example.fijiapp.model.SubCategoryProposal;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.repository.CategoryRepository;
import com.example.fijiapp.repository.SubCategoryProposalRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryProposalService {
    private SubCategoryProposalRepository subCategoryProposalRepository;
    private ProductService productService;
    private ServiceService serviceService;

    public SubCategoryProposalService() {
        subCategoryProposalRepository = new SubCategoryProposalRepository();
        productService = new ProductService();
        serviceService = new ServiceService();
    }

    public void addSubCategoryProposal(SubCategoryProposal subCategoryProposal) {
        subCategoryProposalRepository.addCategory(subCategoryProposal);
    }

    public void updateSubCategoryProposal(SubCategoryProposal subCategoryProposal) {
        subCategoryProposalRepository.updateCategory(subCategoryProposal);
    }

    public void deleteSubCategoryProposal(SubCategoryProposal subCategoryProposal) {
        subCategoryProposalRepository.deleteCategory(subCategoryProposal);
    }

    public Task<List<SubCategoryProposal>> getAllSubCategoryProposals() {
        return subCategoryProposalRepository.getAllSubCategoryProposals().continueWithTask(task -> {
            if (task.isSuccessful()) {
                List<Task<Void>> subCategoryTasks = new ArrayList<>();
                List<SubCategoryProposal> proposals = new ArrayList<>();
                for (DocumentSnapshot document : task.getResult()) {
                    SubCategoryProposal proposal = document.toObject(SubCategoryProposal.class);
                    if (proposal != null && !proposal.IsResolved) {
                        proposal.Id = document.getId();
                        if (proposal.SubCategoryType == SubCategoryType.PRODUCT) {
                            Task<Product> productTask = productService.getProductById(proposal.ProductOrServiceId);
                            subCategoryTasks.add(productTask.onSuccessTask(product -> {
                                if (product != null) {
                                    proposal.Product = product;
                                }
                                return null;
                            }));
                        } else if (proposal.SubCategoryType == SubCategoryType.SERVICE) {
                            Task<Service> serviceTask = serviceService.getServiceById(proposal.ProductOrServiceId);
                            subCategoryTasks.add(serviceTask.onSuccessTask(service -> {
                                if (service != null) {
                                    proposal.Service = service;
                                }
                                return null;
                            }));
                        }
                        proposals.add(proposal);
                    }
                }
                return Tasks.whenAll(subCategoryTasks).continueWith(ignored -> proposals);
            } else {
                throw task.getException();
            }
        });
    }
}
