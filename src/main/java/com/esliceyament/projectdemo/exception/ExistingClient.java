package com.esliceyament.projectdemo.exception;

public class ExistingClient extends RuntimeException {
    public ExistingClient(String message) {
        super(message);
    }
}
