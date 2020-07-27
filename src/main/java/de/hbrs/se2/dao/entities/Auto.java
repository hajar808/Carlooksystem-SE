package de.hbrs.se2.dao.entities;

public class Auto {

    private int id ;
    private String email;
    private String marke;
    private String beschreibung;
    private String baujahr;

    public Auto(){

    }

    public Auto(String email, String marke, String beschreibung, String baujahr) {

        this.email = email;
        this.marke = marke;
        this.beschreibung = beschreibung;
        this.baujahr = baujahr;
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

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }
}
