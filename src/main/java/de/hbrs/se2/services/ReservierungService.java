package de.hbrs.se2.services;

import de.hbrs.se2.dao.entities.Reservierung;

import java.util.List;

public interface ReservierungService {

    void create(String email, int auto_id);
   List<Reservierung> getByEmail(String email);
    List<Reservierung> readAll();
    List<Reservierung> readByVertriebler(String email);
    boolean delete(int id);

}
