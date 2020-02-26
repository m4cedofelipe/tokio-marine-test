package com.example.api.bridge.impl;

import com.example.api.bridge.interfaces.IAddressBridge;
import com.example.api.bridge.interfaces.IBridge;
import com.example.api.domain.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressBridgeImpl implements IAddressBridge {

    @Autowired
    private IBridge bridge;

    @Override
    public Optional<Address> findByCep(String cep) {
        return bridge.get("https://viacep.com.br/ws/" + cep + "/json/",
                Address.class, "");
    }
}
