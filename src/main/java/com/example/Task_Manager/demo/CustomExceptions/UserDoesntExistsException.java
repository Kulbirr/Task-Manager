package com.example.Task_Manager.demo.CustomExceptions;

public class UserDoesntExistsException extends RuntimeException{
    public UserDoesntExistsException(String message){
        super(message);
    }
}
