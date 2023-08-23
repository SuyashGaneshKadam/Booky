package com.SK.Library.Management.System.CustomExceptions;

public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(String message) {
        super(message);
    }
}
