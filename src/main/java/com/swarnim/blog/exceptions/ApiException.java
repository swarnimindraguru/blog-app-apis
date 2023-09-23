package com.swarnim.blog.exceptions;

public class ApiException extends RuntimeException{
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }
}
