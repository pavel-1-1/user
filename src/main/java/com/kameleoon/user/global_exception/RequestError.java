package com.kameleoon.user.global_exception;

public class RequestError extends RuntimeException {
    public RequestError(String message) {
        super(message);
    }
}
