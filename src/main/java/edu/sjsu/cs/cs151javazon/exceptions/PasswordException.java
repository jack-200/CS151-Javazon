package edu.sjsu.cs.cs151javazon.exceptions;

public class PasswordException extends Exception {
    public PasswordException() {
        super("PasswordException thrown");
    }
    public PasswordException(String message) {
        super(message);
    }
}
