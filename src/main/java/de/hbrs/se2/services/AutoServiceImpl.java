package de.hbrs.se2.services;


import de.hbrs.se2.dao.AutosDAO;
import de.hbrs.se2.dao.entities.Auto;

import java.util.List;

public class AutoServiceImpl implements AutoService {


    private AutosDAO autosDAO;

    public AutoServiceImpl(){
        this.autosDAO = new AutosDAO();

    }

    private boolean validate(String email, String marke, String beschreibung, String baujahr){
        if(email.isEmpty() || marke.isEmpty() || beschreibung.isEmpty() || baujahr.isEmpty()){
            return false;
        }

        return true;
    }

    @Override
    public void create(String email, String marke, String beschreibung, String baujahr) {
        validate(email,marke,beschreibung,baujahr);
        Auto auto = new Auto(email,marke,beschreibung,baujahr);
        autosDAO.create(auto);


    }

    @Override
    public Auto getAutoById(int id) {

        Auto auto = autosDAO.readById(id);
        if(auto == null){
            // TODO AutoNotExistException
        }
        return auto;
    }

    @Override
    public List<Auto> getAutoByEmail(String email) {
        return autosDAO.readByEmail(email);
    }

    @Override
    public List<Auto> getAllAutos() {

        return autosDAO.readAll();
    }

    @Override
    public boolean delete(int id) {
        autosDAO.delete(id);
        return true;
    }
}
