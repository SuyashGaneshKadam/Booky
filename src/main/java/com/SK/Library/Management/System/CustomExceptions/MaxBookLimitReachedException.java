package com.SK.Library.Management.System.CustomExceptions;

public class MaxBookLimitReachedException extends Exception{
    public MaxBookLimitReachedException(String message) {
        super(message);
    }
}
