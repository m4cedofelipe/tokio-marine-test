package com.example.api.bridge.interfaces;

import com.example.api.domain.Address;

import java.util.Optional;

public interface IAddressBridge {

    Optional<Address> findByCep(String cep);
}
