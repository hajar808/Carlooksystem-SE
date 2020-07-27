package de.hbrs.se2.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import de.hbrs.se2.exception.IncorrectEmailOrPasswordException;
import de.hbrs.se2.exception.UserNotExistException;
import de.hbrs.se2.services.AuthService;
import de.hbrs.se2.services.AuthServiceImpl;
import de.hbrs.se2.utils.Views;

public class LoginView extends VerticalLayout implements View , Button.ClickListener {



    private FormLayout form;
    private TextField email ;
    private PasswordField passwort;
    private Button login ;
    private AuthService service ;
    private Button registration;

    public LoginView(){
        service = new AuthServiceImpl();

    }

    public void setUp(){
        email =  new TextField("E-Mail");

        form = new FormLayout();

        passwort = new PasswordField("Passwort");

        login = new Button("Login");
        login.addClickListener(this);

        registration = new Button("Registrieren");
        registration.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.REGiSTRIERUNG);
            }
        });

        form.addComponent(email);
        form.addComponent(passwort);
        form.addComponent(login);
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
        try {
            service.login(emailInput, passwortInput);
        } catch (UserNotExistException e) {
            Notification.show("Error",e.getMessage(), Notification.Type.ERROR_MESSAGE);
            clean();

        } catch (IncorrectEmailOrPasswordException e) {
           Notification.show("Error","email or password is wrong" ,Notification.Type.ERROR_MESSAGE);
           clean();

        }
    }

    private void clean(){
        email.setValue("");

        passwort.setValue("");
    }
}

