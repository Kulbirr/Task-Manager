package com.example.Task_Manager.demo.CustomExceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message){
        super(message);
    }
}
