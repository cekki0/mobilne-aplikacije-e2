package com.example.fijiapp.model;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class SubCategoryProposal implements Serializable {
    @Exclude
    public String Id;
    public String ProductOrServiceId;
    public String SubCategoryName;
    public String SubCategoryDescription;
    public SubCategoryType SubCategoryType;
    public String ProposerEmail;
    public Boolean IsResolved = false;
    @Exclude
    public Product Product;
    @Exclude
    public Service Service;

    public SubCategoryProposal() {}

    public SubCategoryProposal(String productOrServiceId, String subCategoryName, String subCategoryDescription, SubCategoryType subCategoryType,String proposerEmail) {
        this.ProductOrServiceId = productOrServiceId;
        this.SubCategoryName = subCategoryName;
        this.SubCategoryDescription = subCategoryDescription;
        this.SubCategoryType = subCategoryType;
        this.ProposerEmail = proposerEmail;
    }
}