package com.example.CollectionProject.exceptions;

public class CredentialsUpdateException extends CredentialsException {

    public CredentialsUpdateException() {
    }

    public CredentialsUpdateException(String message) {
        super(message);
    }

    public CredentialsUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialsUpdateException(Throwable cause) {
        super(cause);
    }

    public CredentialsUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
