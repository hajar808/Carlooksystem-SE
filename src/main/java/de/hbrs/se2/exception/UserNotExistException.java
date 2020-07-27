package de.hbrs.se2.exception;

public class UserNotExistException extends Exception {

    public UserNotExistException(String email){
        super("user with email: "+ email +" not exist");
    }


}
