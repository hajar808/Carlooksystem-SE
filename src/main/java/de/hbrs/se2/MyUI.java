package de.hbrs.se2;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.hbrs.se2.utils.Views;
import de.hbrs.se2.views.KundeView;
import de.hbrs.se2.views.LoginView;
import de.hbrs.se2.views.RegistrierungView;
import de.hbrs.se2.views.VertrieblerView;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Navigator navigator = new Navigator(this,this);
        navigator.addView(Views.LOGIN, LoginView.class);
         navigator.addView(Views.KUNDE, KundeView.class);
         navigator.addView(Views.VERTRIEBLER, VertrieblerView.class);
         navigator.addView(Views.REGiSTRIERUNG, RegistrierungView.class);
         addStyleName("ui");
        UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}

