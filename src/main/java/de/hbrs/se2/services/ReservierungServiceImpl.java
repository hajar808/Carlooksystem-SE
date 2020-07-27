package de.hbrs.se2.services;


import de.hbrs.se2.dao.ReservierungsDAO;
import de.hbrs.se2.dao.entities.Reservierung;

import java.util.List;

public class ReservierungServiceImpl implements ReservierungService {
    private ReservierungsDAO reservierungsDAO;

    public ReservierungServiceImpl(){
        this.reservierungsDAO = new ReservierungsDAO();

    }

    @Override
    public void create(String email, int auto_id) {
        Reservierung reservierung = new Reservierung(email,auto_id);
        reservierungsDAO.create(reservierung);

    }

    @Override
    public List<Reservierung> getByEmail(String email) {

        return reservierungsDAO.readByEmail(email);
    }

    @Override
    public List<Reservierung> readAll() {
        return reservierungsDAO.readAll();
    }

    @Override
    public boolean delete(int id) {
        reservierungsDAO.delete(id);
        return true;
    }
}
