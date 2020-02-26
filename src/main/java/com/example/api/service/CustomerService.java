package com.example.api.service;

import com.example.api.bridge.interfaces.IAddressBridge;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.dto.CustomerResponseDTO;
import com.example.api.repository.CustomerRepository;
import com.example.api.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {

    private CustomerRepository repository;

    private IAddressBridge iAddressBridge;

    @Autowired
    public CustomerService(CustomerRepository repository, IAddressBridge iAddressBridge) {
        this.repository = repository;
        this.iAddressBridge = iAddressBridge;
    }

    public Page<Customer> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return repository.findAll(pageRequest);
    }

    public CustomerResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(this::convertCustomerToCustomerResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    public CustomerResponseDTO save(Customer customer, List<String> ceps) {
        List<Optional<Address>> optionalAddresses = ceps.stream()
                .map(cep -> this.iAddressBridge.findByCep(cep)
                        .map(this::setAddress))
                .collect(Collectors.toList());

        List<Address> addresses = prepareAddressList(optionalAddresses);

        customer.setAddresses(addresses);
        Customer save = repository.save(customer);

        return convertCustomerToCustomerResponseDTO(save);
    }

    private CustomerResponseDTO convertCustomerToCustomerResponseDTO(Customer save) {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        UtilService.copyProperties(save, customerResponseDTO);
        return customerResponseDTO;
    }

    private List<Address> prepareAddressList(List<Optional<Address>> optionalAddresses) {
        return optionalAddresses.stream()
                .flatMap(address -> address.map(Stream::of).orElseGet(Stream::empty))
                .collect(Collectors.toList());
    }

    private Address setAddress(Address address) {
        Address address1 = new Address();
        address1.setLogradouro(address.getLogradouro());
        address1.setBairro(address.getBairro());
        address1.setCep(address.getCep());
        address1.setComplemento(address.getComplemento());
        address1.setUnidade(address.getUnidade());
        address1.setIbge(address.getIbge());
        address1.setUf(address.getUf());
        address1.setGia(address.getGia());
        address1.setLocalidade(address.getLocalidade());
        return address1;
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
        Optional.of(repository.getOne(id))
                .ifPresent(customer -> repository.delete(customer));
    }

}
