package com.taskmanager.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email){
        super("Email '" + email + "' уже существует");
    }
}
