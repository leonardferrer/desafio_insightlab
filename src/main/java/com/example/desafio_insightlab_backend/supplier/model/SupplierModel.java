package com.example.desafio_insightlab_backend.supplier.model;


import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="TB_SUPPLIERS")

public class SupplierModel extends RepresentationModel<SupplierModel> implements Serializable  {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idSupplier;
    private String fantasyName;
    private String corporateReason;
    private String cnpj;
    private String address;

    public UUID getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(UUID idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCorporateReason() {
        return corporateReason;
    }

    public void setCorporateReason(String corporateReason) {
        this.corporateReason = corporateReason;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
