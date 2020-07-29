package de.hbrs.se2.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import de.hbrs.se2.dao.entities.*;
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
    private TextField searchField;
    private Button search;

    private Kunde kunde;
    private Set<Auto> autoSet;
    private Grid<Auto> grid;
    private Set<ReservedAuto> cancelSet;


    public KundeView(){
        authService = new AuthServiceImpl();
        reservierungService = new ReservierungServiceImpl();
        autoService = new AutoServiceImpl();
    }


    public void setUp(){

        addStyleName("view");
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
        menuly.addStyleName("menuly");
        menuly.setSizeFull();

        menu = new MenuBar() ;
        MenuBar.MenuItem myAuto = menu.addItem("All Autos", null, this);
        menu.addStyleName("m");
        MenuBar.MenuItem  myReservation = menu.addItem("Reservieren", null, this);

        MenuBar.MenuItem reservation = menu.addItem("My Reservation", null , this);

        Label icon = new Label("<b>Carlooksystem</b>", ContentMode.HTML);
        icon.addStyleName("icon");
        Label email = new Label(kunde.getEmail());

        iconly.addComponent(email);
        iconly.addComponent(icon);
        iconly.addComponent(logout);

        iconly.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);
        iconly.setComponentAlignment(email, Alignment.TOP_LEFT);
        iconly.setComponentAlignment(logout, Alignment.TOP_RIGHT);

        menuly.addComponent(menu);
        menuly.setComponentAlignment(menu, Alignment.MIDDLE_CENTER);

        searchField = new TextField();
        search = new Button("Suche");
        search.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                search();

            }
        });


        addComponent(iconly);
        addComponent(menuly);

        addComponent(contently);
        showAllAutos();



    }

    private void cleanContent(){
        contently.removeAllComponents();
    }

    private void search(){
        cleanContent();
        String text = searchField.getValue();
        List<Auto> autosListe = autoService.search(text);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                searchField.setValue("");
                showAllAutos();
            }
        });
        if(autosListe.isEmpty()){
            Label label = new Label("Keine Sucheergebnisse vorhandeln");
            contently.addComponent(label);
            contently.addComponent(cancel);

        }else{
            Label label = new Label("Sucheergebnis: "+autosListe.size());
            contently.addComponent(label);
            contently.addComponent(cancel);

            grid = new Grid<>();
            grid.setSizeFull();

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

    private void showAllAutos(){
        cleanContent();
        List<Auto> autosListe = autoService.getAllAutos();
        if(autosListe.isEmpty()){
            Label label = new Label("Keine Autos vorhandeln");
            contently.addComponent(label);

        }else{
            HorizontalLayout searchly = new HorizontalLayout();
            searchly.addComponent(searchField);
            searchly.addComponent(search);
            searchly.setComponentAlignment(searchField, Alignment.MIDDLE_CENTER);
            searchly.setComponentAlignment(search, Alignment.MIDDLE_CENTER);
            contently.addComponent(searchly);
            grid = new Grid<>();
            grid.setSizeFull();
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
        Button cancel = new Button("Stornieren");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(cancelSet == null || cancelSet.isEmpty()){
                    Notification.show("Bitte wählen Auto um zu stornieren", Notification.Type.HUMANIZED_MESSAGE);
                }else{
                    for(ReservedAuto auto : cancelSet){
                        reservierungService.delete(auto.getReservierung_id());
                    }
                    cancelSet = null;
                    showAllReservation();
                }
            }
        });

        if (reservierungsListe.isEmpty()) {
            Label label = new Label("Keine Reservierung vorhandeln");
            contently.addComponent(label);

        } else {

            Label label = new Label("Ihre Reservierungen");
            contently.addComponent(label);
            contently.addComponent(cancel);
            List<ReservedAuto> autoList = autoService.getReservedAutos(reservierungsListe);
            Grid<ReservedAuto> reservGrid = new Grid<>();
            reservGrid.setSizeFull();
           reservGrid.setSelectionMode(Grid.SelectionMode.MULTI);
           reservGrid.addSelectionListener(e -> {
                cancelSet = e.getAllSelectedItems();
            });
            reservGrid.setItems(autoList);

            reservGrid.addColumn(ReservedAuto:: getReservierung_id).setCaption("ReservierungId");


            reservGrid.addColumn(ReservedAuto:: getBeschreibung).setCaption("Beschreibung");
            reservGrid.addColumn(ReservedAuto:: getMarke).setCaption("Marke");
            reservGrid.addColumn(ReservedAuto:: getBaujahr).setCaption("Baujahr");


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
