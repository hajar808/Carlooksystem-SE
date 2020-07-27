package de.hbrs.se2.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.services.AutoService;
import de.hbrs.se2.services.AutoServiceImpl;
import de.hbrs.se2.services.ReservierungService;
import de.hbrs.se2.services.ReservierungServiceImpl;
import de.hbrs.se2.windows.AutoWindow;

import java.util.List;

public class VertrieblerView extends VerticalLayout implements View, MenuBar.Command {

    private HorizontalLayout iconly;
    private MenuBar menu;
    private VerticalLayout contently;

    private AutoService autoService;
    private ReservierungService reservierungService;
    private Vertriebler vertriebler;


    public VertrieblerView(){
        autoService = new AutoServiceImpl();
        reservierungService = new ReservierungServiceImpl() ;


    }

    public void setUp(){
        contently = new VerticalLayout();
        iconly = new HorizontalLayout();
        iconly.setSizeFull();
        HorizontalLayout menuly = new HorizontalLayout();
        menuly.setSizeFull();

        menu = new MenuBar() ;
        MenuBar.MenuItem addAuto = menu.addItem("Add Auto", null, this);
        MenuBar.MenuItem myAuto = menu.addItem("My Auto", null, this);
        MenuBar.MenuItem reservation = menu.addItem("Reservation", null , this);

        Label icon = new Label("<b>Carlooksystem</b>", ContentMode.HTML);
        iconly.addComponent(icon);
        iconly.setComponentAlignment(icon, Alignment.MIDDLE_CENTER);

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
        List<Auto>  autosListe = autoService.getAutoByEmail(vertriebler.getEmail());
        if(autosListe.isEmpty()){
            Label label = new Label("Keine Autos vorhandeln");
            contently.addComponent(label);

        }else{
            HorizontalLayout ly = new HorizontalLayout();
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

            }
            contently.addComponent(ly);

        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        vertriebler = (Vertriebler) UI.getCurrent().getSession().getAttribute("user");
        if(vertriebler!=null){
            setUp();
        }else{
            // TODO login
        }

    }

    @Override
    public void menuSelected(MenuBar.MenuItem menuItem) {
        if(menuItem.getText().equals("Add Auto")){
            UI.getCurrent().addWindow(new AutoWindow());
        }
    }
}
