package com.example.CollectionProject.exceptions;

public class UserUpdateException extends CredentialsException {

    public UserUpdateException() {
    }

    public UserUpdateException(String message) {
        super(message);
    }

    public UserUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUpdateException(Throwable cause) {
        super(cause);
    }

    public UserUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
