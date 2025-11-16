package com.lab11.controllers;

import com.lab11.models.Sale;
import com.lab11.services.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
    private final SaleService service;
    public SaleController(SaleService service) { this.service = service; }

    @GetMapping
    public List<Sale> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Sale> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody Sale sale) {
        return ResponseEntity.ok(service.create(sale));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sale> update(@PathVariable Long id, @RequestBody Sale updated) {
        try {
            return ResponseEntity.ok(service.update(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Дополнительный эндпоинт для связывания Film и Customer
    @PostMapping("/link")
    public ResponseEntity<Sale> link(@RequestParam Long filmId, @RequestParam Long customerId) {
        try {
            return ResponseEntity.ok(service.createSaleByLink(filmId, customerId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
