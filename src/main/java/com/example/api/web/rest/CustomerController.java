package com.example.api.web.rest;

import com.example.api.domain.Customer;
import com.example.api.dto.CustomerResponseDTO;
import com.example.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
    public Page<Customer> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return service.findAll(page, size);
    }

    @ApiOperation(value = "Find by Id")
    @GetMapping("/{id}")
    public CustomerResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ApiOperation(value = "Save Customer")
    @PostMapping("/save")
    public CustomerResponseDTO save(@RequestBody @Valid Customer customer, @RequestParam List<String> ceps) {
        return service.save(customer, ceps);
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
