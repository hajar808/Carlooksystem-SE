package de.hbrs.se2.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import de.hbrs.se2.exception.IncorrectEmailOrPasswordException;
import de.hbrs.se2.exception.UserNotExistException;
import de.hbrs.se2.services.AuthService;
import de.hbrs.se2.services.AuthServiceImpl;
import de.hbrs.se2.utils.Views;

public class RegistrierungView  extends VerticalLayout implements View,Button.ClickListener {
  private FormLayout form;
  private TextField email;
  private PasswordField passwort;
  private TextField name ;
  private PasswordField passwortWdh;
  private Button registration;
  private AuthService service;

    public RegistrierungView(){
        service = new AuthServiceImpl();

    }
    public void setUp(){

        form = new FormLayout();

        email = new TextField("E-Mail");
        passwort = new PasswordField("Passwort");
        passwortWdh = new PasswordField("PasswortWdh");
        name = new TextField("Name");

        registration = new Button("Registrieren");
        registration.addClickListener(this);

        form.addComponent(email);
        form.addComponent(passwort);
        form.addComponent(passwortWdh);
        form.addComponent(name);
        form.addComponent(registration);
        addComponent(form);









    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setUp();

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String emailInput = email.getValue();
        String passwortInput = passwort.getValue();
        String passwortWdgInput = passwortWdh.getValue();
        String nameInput = name.getValue();
        try{
            service.register(emailInput, passwortInput, passwortWdgInput, nameInput);
            UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
        }catch (Exception e) {
            Notification.show("Error",e.getMessage(), Notification.Type.ERROR_MESSAGE);


        }
        }



}
