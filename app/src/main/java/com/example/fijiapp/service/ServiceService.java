package com.example.fijiapp.service;

import com.example.fijiapp.model.Product;
import com.example.fijiapp.model.Service;
import com.example.fijiapp.repository.ServiceRepository;
import com.google.android.gms.tasks.Task;

public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService() {
        serviceRepository = new ServiceRepository();
    }

    public void updateService(Service service, String serviceId) {
        serviceRepository.updateService(service,serviceId);
    }

    public Task<Service> getServiceById(String serviceId) {
        return serviceRepository.getServiceById(serviceId);
    }
}
