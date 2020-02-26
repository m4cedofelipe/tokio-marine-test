package com.example.api.bridge.impl;


import com.example.api.bridge.interfaces.IBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;


@Service
public class BridgeService implements IBridge {

    @Autowired
    private RestTemplate restTemplate;


    private static final Logger logger = LoggerFactory.getLogger(BridgeService.class);

    @Override
    public <T> Optional<T> get(String url, Class<T> responseType, Object... uriVariables) {
        ResponseEntity<T> forEntity;
        try {
            printUrl(url, uriVariables);
            forEntity = restTemplate.getForEntity(url, responseType, uriVariables);
        } catch (HttpClientErrorException e) {
            logger.error(e.getStatusCode().toString());
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return Optional.ofNullable(forEntity.getBody());
    }


    private void printUrl(String url, Object... uriVariables) {
        URI expanded = restTemplate.getUriTemplateHandler().expand(url, uriVariables);
        logger.info(expanded.toString());
    }

}
