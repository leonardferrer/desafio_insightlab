package com.example.desafio_insightlab_backend.supplier.controllers;

import com.example.desafio_insightlab_backend.supplier.dtos.SupplierRecordDto;
import com.example.desafio_insightlab_backend.supplier.model.SupplierModel;
import com.example.desafio_insightlab_backend.supplier.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.validation.FieldError;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SupplierRecordDto supplierRecordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
        }

        var supplierModel = new SupplierModel();
        BeanUtils.copyProperties(supplierRecordDto, supplierModel);
        return ResponseEntity.status(HttpStatus.CREATED). body(supplierService.save(supplierModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSupplier(@PathVariable(value="id") UUID id){
        Optional<SupplierModel> supplier = supplierService.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor inexistente.");
        }
        supplier.get().add(linkTo(methodOn(SupplierController.class).getSuppliers()).withRel("Suppliers List"));
        return ResponseEntity.status(HttpStatus.OK).body(supplier.get());
    }

    @GetMapping
    public ResponseEntity<List<SupplierModel>> getSuppliers(){
        List<SupplierModel> suppliers = supplierService.findAll();
        if(!suppliers.isEmpty()) {
            for(SupplierModel supplier : suppliers) {
                UUID id = supplier.getIdSupplier();
                supplier.add(linkTo(methodOn(SupplierController.class).getSupplier(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSupplier(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid SupplierRecordDto supplierRecordDto) {
        Optional<SupplierModel> supplier = supplierService.findById(id);
        if(supplier.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found.");
        }
        var supplierModel = supplier.get();
        BeanUtils.copyProperties(supplierRecordDto, supplierModel);
        return ResponseEntity.status(HttpStatus.OK).body(supplierService.save(supplierModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSupplier(@PathVariable(value="id") UUID id) {
        Optional<SupplierModel> supplierModel = supplierService.findById(id);
        if(supplierModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        supplierService.deleteById(supplierModel.get().getIdSupplier());
        return ResponseEntity.status(HttpStatus.OK).body("Supplier deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<SupplierModel>> searchSuppliers(@RequestParam String name) {
        List<SupplierModel> suppliers = supplierService.searchByCorporateReason(name);
        if(!suppliers.isEmpty()) {
            for(SupplierModel supplier : suppliers) {
                UUID id = supplier.getIdSupplier();
                supplier.add(linkTo(methodOn(SupplierController.class).getSupplier(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }

    // incorpora no response da request os logs de validação
    private Map<String, Object> getValidationErrors(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        List<String> errors = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        response.put("errors", errors);
        return response;
    }


}
