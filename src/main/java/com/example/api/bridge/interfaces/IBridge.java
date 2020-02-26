package com.example.api.bridge.interfaces;

import java.util.Optional;

public interface IBridge {

    <T> Optional<T> get(String url, Class<T> responseType, Object... uriVariables);

}
