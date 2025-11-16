package com.lab11.services;

import com.lab11.models.Sale;
import com.lab11.repositories.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final FilmRepository filmRepository;
    private final CustomerRepository customerRepository;

    public SaleService(SaleRepository saleRepository, FilmRepository filmRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.filmRepository = filmRepository;
        this.customerRepository = customerRepository;
    }

    public List<Sale> findAll() { return saleRepository.findAll(); }
    public Optional<Sale> findById(Long id) { return saleRepository.findById(id); }

    public Sale create(Sale sale) {
        sale.setSaleDate(LocalDate.now());
        return saleRepository.save(sale);
    }

    public Sale update(Long id, Sale updated) {
        return saleRepository.findById(id).map(existing -> {
            existing.setSaleDate(updated.getSaleDate());
            existing.setFilm(updated.getFilm());
            existing.setCustomer(updated.getCustomer());
            return saleRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Sale not found"));
    }

    public void delete(Long id) { saleRepository.deleteById(id); }

    public Sale createSaleByLink(Long filmId, Long customerId) {
        var film = filmRepository.findById(filmId)
                .orElseThrow(() -> new RuntimeException("Film not found"));
        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Sale sale = new Sale();
        sale.setFilm(film);
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDate.now());
        return saleRepository.save(sale);
    }
}
