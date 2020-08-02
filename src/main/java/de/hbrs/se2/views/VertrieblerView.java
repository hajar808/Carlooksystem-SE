package de.hbrs.se2.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.ReservedAuto;
import de.hbrs.se2.dao.entities.Reservierung;
import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.exception.DataBaseException;
import de.hbrs.se2.services.*;
import de.hbrs.se2.windows.AutoWindow;

import java.util.List;

public class VertrieblerView extends VerticalLayout implements View, MenuBar.Command {

    private HorizontalLayout iconly;
    private MenuBar menu;
    private VerticalLayout contently;
    private Button logout;

    private AutoService autoService;
    private ReservierungService reservierungService;
    private AuthService authService;
    private Vertriebler vertriebler;


    public VertrieblerView(){
        try {
            autoService = new AutoServiceImpl();
            reservierungService = new ReservierungServiceImpl() ;
            authService = new AuthServiceImpl();
        } catch (DataBaseException e) {
            Notification.show("Error", e.getMessage(),Notification.Type.ERROR_MESSAGE);
        }



    }

    public void setUp(){
        addStyleName("vertriebler");
        logout = new Button("Logout");
        logout.setId("logout");
        logout.addStyleName("m");
        logout.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                authService.logout();

            }
        });
        contently = new VerticalLayout();
        iconly = new HorizontalLayout();
        iconly.setSizeFull();
        HorizontalLayout menuly = new HorizontalLayout();
        menuly.setSizeFull();

        menu = new MenuBar() ;
        MenuBar.MenuItem addAuto = menu.addItem("Add Auto", null, this);
        menu.addStyleName("m");
        MenuBar.MenuItem myAuto = menu.addItem("My Auto", null, this);
        MenuBar.MenuItem reservation = menu.addItem("Reservation", null , this);

        Label icon = new Label("<b>Carlooksystem</b>", ContentMode.HTML);
        icon.addStyleName("ic");
        Label email = new Label(vertriebler.getEmail());
        email.setId("email_lbl");
        email.addStyleName("em");

        iconly.addComponent(email);
        iconly.addComponent(icon);
        iconly.addComponent(logout);
        iconly.setComponentAlignment(icon, Alignment.TOP_CENTER);
        iconly.setComponentAlignment(logout, Alignment.TOP_RIGHT);

        menuly.addComponent(menu);
        menuly.setComponentAlignment(menu, Alignment.MIDDLE_CENTER);

        addComponent(iconly);
        addComponent(menuly);
        addComponent(contently);
        setComponentAlignment(contently,Alignment.MIDDLE_CENTER);
        showAllAutos();



    }

    private void cleanContent(){
        contently.removeAllComponents();
    }

    private void showAllAutos(){
        cleanContent();
        List<Auto>  autosListe = autoService.getAutoByEmail(vertriebler.getEmail());
        if(autosListe.isEmpty()){
            Label label = new Label("Keine Autos vorhandeln");
            contently.addComponent(label);

        }else{
           // HorizontalLayout ly = new HorizontalLayout();

            Grid<Auto> grid = new Grid<>();
            grid.setSizeFull();
            grid.setHeightUndefined();
            grid.setItems(autosListe);
            grid.addColumn(Auto:: getId).setCaption("Id");
            grid.addColumn(Auto:: getBeschreibung).setCaption("Beschreibung");
            grid.addColumn(Auto:: getMarke).setCaption("Marke");
            grid.addColumn(Auto:: getBaujahr).setCaption("Baujahr");


            contently.addComponent(grid);






            /*
            for(Auto auto: autosListe){
                VerticalLayout autoly = new VerticalLayout();
                Label id = new Label("" + auto.getId());
                Label marke = new Label(auto.getMarke());
                Label beschreibung = new Label(auto.getBeschreibung());
                Label baujahr = new Label(auto.getBaujahr());
                autoly.addComponent(id);
                autoly.addComponent(marke);
                autoly.addComponent(beschreibung);
                autoly.addComponent(baujahr);
                ly.addComponent(autoly);

            }*/
            //contently.addComponent(ly);

        }
    }

    private void showAllReservation() {
        cleanContent();
        List<Reservierung> reservierungsListe = reservierungService.readByVertriebler(vertriebler.getEmail());
        if (reservierungsListe.isEmpty()) {
            Label label = new Label("Keine Reservierung vorhandeln");
            contently.addComponent(label);

        } else {


            List<ReservedAuto> autoList = autoService.getReservedAutos(reservierungsListe);
            Grid<ReservedAuto> reservGrid = new Grid<>();
            reservGrid.setSizeFull();

            reservGrid.setItems(autoList);

            reservGrid.addColumn(ReservedAuto:: getReservierung_id).setCaption("ReservierungId");


            reservGrid.addColumn(ReservedAuto:: getBeschreibung).setCaption("Beschreibung");
            reservGrid.addColumn(ReservedAuto:: getMarke).setCaption("Marke");
            reservGrid.addColumn(ReservedAuto:: getBaujahr).setCaption("Baujahr");


            contently.addComponent(reservGrid);
           /* HorizontalLayout ly = new HorizontalLayout();
            for (Reservierung reservierung : reservierungsListe) {
                VerticalLayout reservierungly = new VerticalLayout();
                Label id = new Label("" + reservierung.getId());
                Label auto_id = new Label("" +reservierung.getAuto_id());

                reservierungly.addComponent(id);
                reservierungly.addComponent(auto_id);

                ly.addComponent(reservierungly);

            }
            contently.addComponent(ly);*/
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        vertriebler = (Vertriebler) UI.getCurrent().getSession().getAttribute("user");
        if(vertriebler!=null){
            setUp();
        }else{
            authService.logout();
        }

    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        String item = menuItem.getText();
        switch (item){
            case "Add Auto":
                UI.getCurrent().addWindow(new AutoWindow());
                break;

            case "My Auto":
                showAllAutos();
                break;

            case "Reservation":
                showAllReservation();
                break;

                default:
                    break;



        }
        //if(menuItem.getText().equals("Add Auto")){
           // UI.getCurrent().addWindow(new AutoWindow());
        //}
    }
}
