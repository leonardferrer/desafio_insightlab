package com.example.desafio_insightlab_backend.supplier.repositories;

import com.example.desafio_insightlab_backend.supplier.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<SupplierModel, UUID> {
}
