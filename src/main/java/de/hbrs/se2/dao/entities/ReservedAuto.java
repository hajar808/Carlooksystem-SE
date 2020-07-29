package de.hbrs.se2.dao.entities;

public class ReservedAuto {

    private int reservierung_id;

    private int auto_id ;
    private String email;
    private String marke;
    private String beschreibung;

    public int getReservierung_id() {
        return reservierung_id;
    }

    public void setReservierung_id(int reservierung_id) {
        this.reservierung_id = reservierung_id;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
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

    private String baujahr;

    public ReservedAuto(int reservierung_id, Auto auto){
        this.reservierung_id = reservierung_id;
        auto_id = auto.getId();
        marke = auto.getMarke();
        beschreibung = auto.getBeschreibung();
        baujahr = auto.getBaujahr();

    }
}
