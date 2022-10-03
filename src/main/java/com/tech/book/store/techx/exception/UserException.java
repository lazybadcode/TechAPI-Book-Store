package com.tech.book.store.techx.exception;

public class UserException extends RuntimeException {

    public UserException(String name) {
        super("user." + name + " is already exists");
    }
    
    public UserException(String name, String msg) {
        super("user." + name + " " + msg);
    }
    
    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }
    
    // CREATE

    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }

    public static UserException createUserNameNull() {
        return new UserException("create.user.name.null");
    }

}
