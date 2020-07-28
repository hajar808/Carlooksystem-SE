package de.hbrs.se2.windows;

import com.vaadin.ui.*;
import de.hbrs.se2.dao.entities.Auto;
import de.hbrs.se2.dao.entities.Vertriebler;
import de.hbrs.se2.services.AutoService;
import de.hbrs.se2.services.AutoServiceImpl;

public class AutoWindow extends Window implements Button.ClickListener {

    private FormLayout form;
    private TextField marke;
    private TextField beschreibung;
    private TextField baujahr;
    private Button create;
    private AutoService service;


    public AutoWindow(){
        service = new AutoServiceImpl();
        form = new FormLayout();
        marke = new TextField("marke");
        beschreibung = new TextField("beschreibung");
        baujahr = new TextField("baujahr");
        create = new Button("Create");
        create.addClickListener(this);

        form.addComponent(marke);
        form.addComponent(beschreibung);
        form.addComponent(baujahr);
        form.addComponent(create);
        setContent(form);
        center();
        setWidth("25%");



    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String markeInput = marke.getValue();
        String beschreibungInput = beschreibung.getValue();
        String baujahrInput = baujahr.getValue();
        Vertriebler vertriebler = (Vertriebler) UI.getCurrent().getSession().getAttribute("user");
        try{
            service.create(vertriebler.getEmail(), markeInput, beschreibungInput, baujahrInput);
            AutoWindow.this.close();
        }catch(Exception e){

        }

    }
}
