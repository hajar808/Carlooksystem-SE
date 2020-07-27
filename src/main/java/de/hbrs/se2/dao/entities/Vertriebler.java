package de.hbrs.se2.dao.entities;

public class Vertriebler {

    private String email;
    private int role_id;
    private String passwort;
    private String name;

    public Vertriebler(String email,  String passwort, String name) {
        this.email = email;
        this.passwort = passwort;
        this.name = name;
    }

    public Vertriebler(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
