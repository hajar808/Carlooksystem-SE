package de.hbrs.se2.services;


import de.hbrs.se2.exception.IncorrectEmailOrPasswordException;
import de.hbrs.se2.exception.UserNotExistException;

public interface AuthService {

    boolean register(String email, String passwort,String passwortWdh, String name);

    void login(String email, String passwort) throws UserNotExistException, IncorrectEmailOrPasswordException;

    void logout();



}
