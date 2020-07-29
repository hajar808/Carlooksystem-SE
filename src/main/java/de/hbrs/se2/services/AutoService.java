package de.hbrs.se2.services;


import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.ReservedAuto;
import de.hbrs.se2.dao.entities.Reservierung;

import java.util.List;

public interface AutoService {

    void create(String email, String marke, String beschreibung, String baujahr);
    Auto getAutoById(int id);
    List<Auto> getAutoByEmail(String email);
    List<Auto> getAllAutos();
    List<Auto> search(String text);
    List<ReservedAuto> getReservedAutos(List<Reservierung> list);
    boolean delete(int id);




}
