package com.SK.Library.Management.System.CustomExceptions;

public class TransactionTableEmptyException extends Exception{
    public TransactionTableEmptyException(String message) {
        super(message);
    }
}
