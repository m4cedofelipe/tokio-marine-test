package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@Api(value = "CUSTOMER")
public class CustomerController {

    private CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Find all Customers")
    @GetMapping
    public Page<Customer> findAll(@PageableDefault(size = Integer.MAX_VALUE,
            sort = {"name"}) Pageable pageable) {
        return service.findAll(pageable);
    }

    @ApiOperation(value = "Find by Id")
    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Save Customer")
    @PostMapping("/save")
    public Customer save(@RequestBody @Valid Customer customer) {
        return service.save(customer);
    }

    @ApiOperation(value = "Update Customer")
    @PutMapping("/update/{id}}")
    public Customer update(@PathVariable long id, @RequestBody @Valid Customer customer) {
        return service.update(id, customer).get();
    }

    @ApiOperation(value = "Delete Customer")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
