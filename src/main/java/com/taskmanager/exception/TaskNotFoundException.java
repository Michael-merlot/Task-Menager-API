package com.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id){
        super("Задача с ID " + id + " не найдена");
    }
}
