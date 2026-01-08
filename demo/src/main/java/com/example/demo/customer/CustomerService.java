package com.example.demo.customer;

import com.example.demo.customer.dto.CustomerCreateRequest;
import com.example.demo.customer.dto.CustomerUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer no encontrado: " + id));
    }

    @Transactional
    public Customer create(CustomerCreateRequest req) {
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Ya existe un customer con email: " + req.getEmail());
        }

        Customer c = Customer.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .build();

        return repo.save(c);
    }

    @Transactional
    public Customer update(Long id, CustomerUpdateRequest req) {
        Customer current = findById(id);

        if (!current.getEmail().equalsIgnoreCase(req.getEmail()) && repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Ya existe un customer con email: " + req.getEmail());
        }

        current.setFirstName(req.getFirstName());
        current.setLastName(req.getLastName());
        current.setEmail(req.getEmail());

        return repo.save(current);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Customer no encontrado: " + id);
        }
        repo.deleteById(id);
    }
}
