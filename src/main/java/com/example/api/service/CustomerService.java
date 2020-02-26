package com.example.api.service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> update(long id, Customer customer) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(customer.getName());
                    record.setEmail(customer.getEmail());
                    return repository.save(record);
                });
    }

    public void delete(long id) {
        Customer customer = this.findById(id);
        repository.delete(customer);
    }

}
