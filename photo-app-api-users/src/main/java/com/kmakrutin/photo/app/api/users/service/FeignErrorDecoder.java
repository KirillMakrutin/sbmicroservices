package com.kmakrutin.photo.app.api.users.service;

import feign.Response;
import feign.codec.ErrorDecoder;
import javassist.tools.web.BadHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

// Centralized feign error handling
@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                return new BadHttpRequest();

            case 404:
                if (methodKey.contains("getAlbums")) {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, response.reason());
                }
        }

        return new Exception(response.reason());
    }
}
