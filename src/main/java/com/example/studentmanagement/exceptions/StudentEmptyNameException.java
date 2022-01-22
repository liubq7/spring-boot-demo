package com.example.studentmanegement.exceptions;

public class StudentEmptyNameException extends RuntimeException {
    public StudentEmptyNameException(String message) {
        super(message);
    }
}
