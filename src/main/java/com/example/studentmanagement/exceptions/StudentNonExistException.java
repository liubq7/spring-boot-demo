package com.example.studentmanegement.exceptions;

public class StudentNonExistException extends RuntimeException {
    public StudentNonExistException(String message) {
        super(message);
    }
}
