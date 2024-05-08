package com.example.fijiapp.activity.register;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fijiapp.R;
import com.example.fijiapp.activity.category.CategoryManagementAdminActivity;
import com.example.fijiapp.adapters.CategoryAdapter;
import com.example.fijiapp.model.Category;
import com.example.fijiapp.model.SubCategory;
import com.example.fijiapp.model.SubCategoryProposal;
import com.example.fijiapp.model.SubCategoryType;
import com.example.fijiapp.service.CategoryService;
import com.example.fijiapp.service.ProductService;
import com.example.fijiapp.service.ServiceService;
import com.example.fijiapp.service.SubCategoryProposalService;
import com.example.fijiapp.service.SubCategoryService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProposalAdapter proposalAdapter;
    private List<SubCategoryProposal> proposalList= new ArrayList<>();
    private SubCategoryProposalService subCategoryProposalService = new SubCategoryProposalService();
    private SubCategoryService subCategoryService = new SubCategoryService();
    private ProductService productService = new ProductService();
    private ServiceService serviceService = new ServiceService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_service_management);
        fetchAndInitializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        proposalAdapter = new ProposalAdapter(proposalList);
        recyclerView.setAdapter(proposalAdapter);
    }

    private void fetchAndInitializeRecyclerView() {
        subCategoryProposalService.getAllSubCategoryProposals()
                .addOnCompleteListener(new OnCompleteListener<List<SubCategoryProposal>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<SubCategoryProposal>> task) {
                        if (task.isSuccessful()) {
                            proposalList = task.getResult();
                            initializeRecyclerView();
                        } else {
                            Toast.makeText(ProductServiceManagementActivity.this,
                                    "Failed to fetch proposals", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private class ProposalAdapter extends RecyclerView.Adapter<ProposalAdapter.ViewHolder> {
        private List<SubCategoryProposal> proposalList;

        ProposalAdapter(List<SubCategoryProposal> proposalList) {
            this.proposalList = proposalList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposal_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            SubCategoryProposal proposal = proposalList.get(position);
            if(proposal.SubCategoryType == SubCategoryType.PRODUCT)
                holder.productOrServiceName.setText(proposal.Product.Title);
            else
                holder.productOrServiceName.setText(proposal.Service.getName());
            holder.subCategoryName.setText(proposal.SubCategoryName);
            holder.subCategoryDescription.setText(proposal.SubCategoryDescription);
            holder.subCategoryType.setText(proposal.SubCategoryType.toString());

            holder.approveButton.setOnClickListener(v -> {
                proposal.IsResolved = true;
                subCategoryService.addSubCategory(new SubCategory(proposal.SubCategoryName,proposal.SubCategoryDescription,proposal.SubCategoryType));
                if(proposal.SubCategoryType == SubCategoryType.PRODUCT)
                {
                    proposal.Product.SubCategory= proposal.SubCategoryName;
                    subCategoryProposalService.updateSubCategoryProposal(proposal);
                    productService.updateProduct(proposal.Product,proposal.ProductOrServiceId);
                }
                else
                {
                    proposal.Service.setSubCategory(proposal.SubCategoryName);
                    subCategoryProposalService.updateSubCategoryProposal(proposal);
                    serviceService.updateService(proposal.Service,proposal.ProductOrServiceId);
                }

                Toast.makeText(ProductServiceManagementActivity.this, "Proposal Approved", Toast.LENGTH_SHORT).show();
            });

            holder.denyButton.setOnClickListener(v -> {
                proposal.IsResolved = true;
                subCategoryProposalService.updateSubCategoryProposal(proposal);
                Toast.makeText(ProductServiceManagementActivity.this, "Proposal Denied", Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            return proposalList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView productOrServiceName;
            TextView subCategoryName;
            TextView subCategoryDescription;
            TextView subCategoryType;
            Button approveButton;
            Button denyButton;

            ViewHolder(View itemView) {
                super(itemView);
                productOrServiceName = itemView.findViewById(R.id.productOrServiceName);
                subCategoryName = itemView.findViewById(R.id.subCategoryName);
                subCategoryDescription = itemView.findViewById(R.id.subCategoryDescription);
                subCategoryType = itemView.findViewById(R.id.subCategoryType);
                approveButton = itemView.findViewById(R.id.approveButton);
                denyButton = itemView.findViewById(R.id.denyButton);
            }
        }
    }
}
