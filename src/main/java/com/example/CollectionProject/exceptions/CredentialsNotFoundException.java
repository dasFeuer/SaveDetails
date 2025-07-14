package com.example.CollectionProject.exceptions;

public class CredentialsNotFoundException extends CredentialsException {

    public CredentialsNotFoundException() {

    }

    public CredentialsNotFoundException(String message) {
        super(message);
    }


    public CredentialsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CredentialsNotFoundException(Throwable cause) {
        super(cause);
    }

    public CredentialsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
