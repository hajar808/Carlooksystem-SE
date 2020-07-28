package de.hbrs.se2.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.Kunde;
import de.hbrs.se2.dao.entities.Reservierung;
import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.services.*;
import de.hbrs.se2.windows.AutoWindow;

import java.util.List;
import java.util.Set;


public class KundeView extends VerticalLayout implements View, MenuBar.Command {

    private HorizontalLayout iconly;
    private MenuBar menu;
    private VerticalLayout contently;

    private AutoService autoService;
    private ReservierungService reservierungService;
    private AuthService authService;
    private Button logout;

    private Kunde kunde;
    private Set<Auto> autoSet;
    private Grid<Auto> grid;


    public KundeView(){
        authService = new AuthServiceImpl();
        reservierungService = new ReservierungServiceImpl();
        autoService = new AutoServiceImpl();
    }


    public void setUp(){
        logout = new Button("Logout");
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
        MenuBar.MenuItem myAuto = menu.addItem("All Autos", null, this);
        MenuBar.MenuItem  myReservation = menu.addItem("Reservieren", null, this);

        MenuBar.MenuItem reservation = menu.addItem("My Reservation", null , this);

        Label icon = new Label("<b>Carlooksystem</b>", ContentMode.HTML);
        icon.addStyleName("icon");
        iconly.addComponent(icon);
        iconly.addComponent(logout);
        iconly.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
        iconly.setComponentAlignment(logout, Alignment.TOP_RIGHT);

        menuly.addComponent(menu);
        menuly.setComponentAlignment(menu, Alignment.MIDDLE_CENTER);

        addComponent(iconly);
        addComponent(menuly);
        addComponent(contently);
        showAllAutos();



    }

    private void cleanContent(){
        contently.removeAllComponents();
    }

    private void showAllAutos(){
        cleanContent();
        List<Auto> autosListe = autoService.getAllAutos();
        if(autosListe.isEmpty()){
            Label label = new Label("Keine Autos vorhandeln");
            contently.addComponent(label);

        }else{

            grid = new Grid<>();

            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addSelectionListener(e -> {
                    autoSet = e.getAllSelectedItems();
            });

            grid.setItems(autosListe);
            grid.addColumn(Auto:: getId).setCaption("Id");
            grid.addColumn(Auto:: getBeschreibung).setCaption("Beschreibung");
            grid.addColumn(Auto:: getMarke).setCaption("Marke");
            grid.addColumn(Auto:: getBaujahr).setCaption("Baujahr");


            contently.addComponent(grid);


        }
    }

    private void showAllReservation() {
        cleanContent();
        List<Reservierung> reservierungsListe = reservierungService.getByEmail(kunde.getEmail());
        if (reservierungsListe.isEmpty()) {
            Label label = new Label("Keine Reservierung vorhandeln");
            contently.addComponent(label);

        } else {

            Grid<Reservierung> reservGrid = new Grid<>();
            reservGrid.setItems(reservierungsListe);
            reservGrid.addColumn(Reservierung:: getId).setCaption("Id");
            reservGrid.addColumn(Reservierung:: getAuto_id).setCaption("Auto_Id");


            contently.addComponent(reservGrid);

        }
    }

    private void reservieren(){
        if(autoSet != null && !autoSet.isEmpty()){
            for(Auto auto : autoSet){
                reservierungService.create(kunde.getEmail(), auto.getId());
            }
            autoSet = null;
            grid.deselectAll();

            Notification.show("Reservierung ist erfolgreich", Notification.Type.HUMANIZED_MESSAGE);
        }else{
            Notification.show("keine Auto selektieret", Notification.Type.HUMANIZED_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        kunde = (Kunde) UI.getCurrent().getSession().getAttribute("user");
        if(kunde!=null){
            setUp();
        }else{
            authService.logout();
        }

    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        String item = menuItem.getText();
        switch (item){

            case "All Autos":
                showAllAutos();
                break;

            case "Reservieren":
               reservieren();
               break;

            case "My Reservation":
                showAllReservation();
                break;

            default:
                break;



        }

    }






}
