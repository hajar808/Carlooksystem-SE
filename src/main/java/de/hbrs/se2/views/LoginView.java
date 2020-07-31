package de.hbrs.se2.views;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
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
        addStyleName("login");
        email =  new TextField("E-Mail");

        form = new FormLayout();
        form.addStyleName("form");

        passwort = new PasswordField("Passwort");

        email.setRequiredIndicatorVisible(true);
        passwort.setRequiredIndicatorVisible(true);

        login = new Button("Anmelden");
        login.addStyleName("anmelden");
        login.addClickListener(this);

        registration = new Button("Registrieren");
        registration.addStyleName("registrieren");
        registration.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(Views.REGiSTRIERUNG);
            }
        });

        Label icon = new Label("<b>Carlooksystem</b>", ContentMode.HTML);
        icon.addStyleName("ic");



        form.addComponent(email);
        form.addComponent(passwort);
        form.addComponent(login);
        form.addComponent(registration);
        addComponent(icon);
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

