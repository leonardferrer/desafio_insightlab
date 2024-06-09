package com.example.desafio_insightlab_backend.supplier.services;

import com.example.desafio_insightlab_backend.supplier.model.SupplierModel;
import com.example.desafio_insightlab_backend.supplier.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierModel save(SupplierModel supplier) {
        return supplierRepository.save(supplier);
    }

    public List<SupplierModel> findAll() {
        return supplierRepository.findAll();
    }

    public Optional<SupplierModel> findById(UUID id) {
        return supplierRepository.findById(id);
    }

    public void deleteById(UUID id) {
        supplierRepository.deleteById(id);
    }

    public List<SupplierModel> searchByCorporateReason(String corporateReason) {
        return supplierRepository.findByCorporateReasonContainingIgnoreCase(corporateReason);
    }

}
