package com.example.desafio_insightlab_backend.supplier.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SupplierRecordDto(
        @NotBlank(message = "O nome fantasia é obrigatório") String fantasyName,
        @NotBlank(message = "A razão social é obrigatória") String corporateReason,
        @NotBlank(message = "O CNPJ é obrigatório") @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter apenas números e ter 14 dígitos") String cnpj,
        String address
) {
}
