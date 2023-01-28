package com.stock.app.stockapplication.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Setter
@Getter
public class RequestValidationException extends RuntimeException {

    private Map<String, String> errorMap;
    private HttpStatus request;

    public RequestValidationException(String message, Map<String, String> errorMap, HttpStatus request) {
        super(message);
        this.errorMap = errorMap;
        this.request = request;
    }


}
