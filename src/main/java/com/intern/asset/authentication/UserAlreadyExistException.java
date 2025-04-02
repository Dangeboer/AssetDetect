package com.intern.asset.authentication;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("Username already exists");
    }
}
