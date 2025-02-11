package com.example.Task_Manager.demo.CustomExceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String message){
        super(message);
    }
}