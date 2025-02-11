package com.example.Task_Manager.demo.CustomExceptions;

public class RoleDoesntExistsException extends RuntimeException{
    public RoleDoesntExistsException (String message){
        super(message);
    }
}
