package com.nagiel.tpspring.payload.response;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
