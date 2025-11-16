package com.lab11.services;

import com.lab11.models.Customer;
import com.lab11.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository) { this.repository = repository; }

    public List<Customer> findAll() { return repository.findAll(); }
    public Optional<Customer> findById(Long id) { return repository.findById(id); }
    public Customer create(Customer customer) { return repository.save(customer); }
    public Customer update(Long id, Customer updated) {
        return repository.findById(id).map(existing -> {
            existing.setAge(updated.getAge());
            existing.setGender(updated.getGender());
            existing.setSocialStatus(updated.getSocialStatus());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
