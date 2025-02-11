package com.example.Task_Manager.demo.CustomExceptions;

public class InvalidCredentialsException extends Exception{
    public InvalidCredentialsException(String message){
        super(message);
    }
}
