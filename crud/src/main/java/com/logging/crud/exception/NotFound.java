package com.logging.crud.exception;

public class NotFound extends RuntimeException{
    public NotFound(String message){
        super(message);
    }
}
