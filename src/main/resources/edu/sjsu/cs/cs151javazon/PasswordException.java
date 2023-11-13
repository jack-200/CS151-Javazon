package edu.sjsu.cs.cs151javazon.PasswordExceptions;

public class PasswordException extends Exception{
    public PasswordException(){
        super("PasswordException thrown");
    }
    public PasswordException(String message){
        super(message);
    }
}
