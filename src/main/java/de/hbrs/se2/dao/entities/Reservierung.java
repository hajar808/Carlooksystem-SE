package de.hbrs.se2.dao.entities;

public class Reservierung {

    private int id;
    private String email;
    private int auto_id;

    public Reservierung(){

    }

    public Reservierung( String email, int auto_id) {
        this.email = email;
        this.auto_id = auto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }
}

