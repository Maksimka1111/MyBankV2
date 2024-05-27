package com.example.SprignReactOauth2.Excpetion;

public class PasswordNotMatchesException extends Exception{
    public PasswordNotMatchesException(String message) {
        super(message);
    }
}
