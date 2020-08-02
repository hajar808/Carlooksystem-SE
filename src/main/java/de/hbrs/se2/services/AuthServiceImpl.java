package de.hbrs.se2.services;


import com.vaadin.ui.UI;
import de.hbrs.se2.dao.KundenDAO;
import de.hbrs.se2.dao.VertrieblerDAO;
import de.hbrs.se2.dao.entities.Kunde;
import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.exception.DataBaseException;
import de.hbrs.se2.exception.IncorrectEmailOrPasswordException;
import de.hbrs.se2.exception.NoValidDataException;
import de.hbrs.se2.exception.UserNotExistException;
import de.hbrs.se2.utils.Views;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private KundenDAO kundeDAO;
    private VertrieblerDAO vertrieblerDAO;

    private static final String EMAILVALIDE = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public AuthServiceImpl() throws DataBaseException {
        this.kundeDAO = new KundenDAO();
        this.vertrieblerDAO = new VertrieblerDAO();


    }

    private boolean validate(String email, String passwort, String passwortWdh, String name) throws NoValidDataException {

        if (email.isEmpty() || passwort.isEmpty() || name.isEmpty()) {
            throw new NoValidDataException("Bitte alle Felder ausfühllen");
        }
        if (!email.matches(EMAILVALIDE)) {
            throw new NoValidDataException("Bitte eine gültige E-Mail eingeben");

        }if(!passwort.equals(passwortWdh)){
            throw new NoValidDataException("Passwörter stimmen nicht überein");

        }



        return true;

        // TODO Passwort check


    }

    private boolean isVertrierbler(String email) {
        if (email.endsWith("carlook.de")) {
            return true;
        }
        return false;
    }


    @Override
    public boolean register(String email, String passwort, String passwortWdh, String name) throws NoValidDataException {
        validate(email, passwort,passwortWdh, name);
        if (isVertrierbler(email)) {
            Vertriebler existVertriebler = vertrieblerDAO.readById(email);
            if(existVertriebler!=null){
                throw new NoValidDataException("E-Mail bereits vorhanden");
            }
            Vertriebler vertriebler = new Vertriebler(email, passwort, name);
            //speichere vertriebler in DB
            vertrieblerDAO.create(vertriebler);

        } else {
            Kunde existKunde = kundeDAO.readByEmail(email);
            if(existKunde!= null){
                throw new NoValidDataException("E-Mail bereits vorhanden");
            }
            Kunde kunde = new Kunde(email, passwort, name);
            kundeDAO.create(kunde);
        }

        return true;
    }

    @Override
    public void login(String email, String passwort) throws UserNotExistException, IncorrectEmailOrPasswordException {
        if (isVertrierbler(email)) {
            Vertriebler vertriebler = vertrieblerDAO.readById(email);
            if(vertriebler == null){
                throw new UserNotExistException(email);
            }
            if (!check(passwort, vertriebler.getPasswort())) {
                throw new IncorrectEmailOrPasswordException();

            }

            UI.getCurrent().getSession().setAttribute("user", vertriebler);
            UI.getCurrent().getNavigator().navigateTo(Views.VERTRIEBLER);
        } else {
            Kunde kunde = kundeDAO.readByEmail(email);
            if (kunde == null  ||!check(passwort, kunde.getPasswort())) {
                throw new IncorrectEmailOrPasswordException();
            }

            UI.getCurrent().getSession().setAttribute("user", kunde);
            UI.getCurrent().getNavigator().navigateTo(Views.KUNDE);
        }

    }

    private boolean check(String existPasswort, String actualPasswort) {

        return existPasswort.equals(actualPasswort);
    }


    @Override
    public void logout() {
        UI.getCurrent().getSession().setAttribute("user", null);
        UI.getCurrent().getSession().close();
        UI.getCurrent().getPage().setLocation("/login");
       // UI.getCurrent().getPage().open("/");

    }
}
