package net.kopuz.ApI.exception;

public class BadQueryRequestException extends RuntimeException{

    public BadQueryRequestException(String message) {
        super(message);
    }
}
