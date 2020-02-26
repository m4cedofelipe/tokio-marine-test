package com.example.api.util;

import com.example.api.exception.CustomGenericException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

public class UtilService {

    private static final String ERRO_CONVERTER_CLASSE = "Erro ao converter a classe: ";

    private UtilService() {}

    public static <T> T copyProperties(Object source, Class<T> target, String... ignoreProperties) {
        T instance;
        try {
            instance = target.newInstance();
            copyProperties(source, instance, ignoreProperties);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CustomGenericException(ERRO_CONVERTER_CLASSE + target.getName(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }
}
