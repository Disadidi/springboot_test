package com.hdconsulting.test.springboot.app.exceptions;

public class DineroInsuficienteException extends RuntimeException{
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
