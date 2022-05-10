/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui.reservation;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Resevation;
import com.mycompany.services.ServiceReservation;

/**
 *
 * @author Khach
 */
public class UpdateReservation extends BaseForm {

    Form current;

    public UpdateReservation(Resources res, Resevation reservation) {
        super("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Utilisateur");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);

        TextField Etat = new TextField(reservation.getEtat(), "Etat", 16, TextField.ANY);
        Etat.setUIID("HewsTopLine");
        Etat.setSingleLineTextArea(true);

       //TextField datereservation = new TextField("", " datereservation", 16, TextField.ANY);
        //datereservation.setUIID("TextFieldBlack");
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("datePicker", datePicker); 
        //TextField id = new TextField((reservation.getId()), "Evenement", 16, TextField.ANY);
        //TextField iduser = new TextField((reservation.getId()), "Utilisateur", 16, TextField.ANY);
        datePicker.setUIID("NevaTopLine");
        //id.setUIID("NewaTopLine");
        //iduser.setUIID("NewaTopLine");
        //datePicker.setDate();
       //id.setSingleLineTextArea(true);
        //iduser.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick brnModifer
        btnModifier.addActionListener((e) -> {

            reservation.setEtat(Etat.getText());
            reservation.setDatereservation(datePicker.getText());
            //reservation.setId(Integer.valueOf(id.getText()));
            //reservation.setIduser(Integer.valueOf(iduser.getText()));

  
        if (ServiceReservation.getInstance().UpdateReservation(reservation, reservation.getIdreservation())) {
            System.out.println("hhh");
            new ListReservation(res).show();
        }
      });
        Button btnannuler = new Button("Annuler");
        btnannuler.addActionListener((e) -> {

            new ListReservation(res).show();

        });
        Label M1 = new Label("");
        Label M2 = new Label("");
        Label M3 = new Label("");
        Label M4 = new Label("");
        Label M5 = new Label("");

        Container content = BoxLayout.encloseY(
                M1, M2,
                new FloatingHint(Etat),
                createLineSeparator(),
               //new FloatingHint(datePicker),
                createLineSeparator(),
               /* new FloatingHint(id),
                createLineSeparator(),
                new FloatingHint(iduser),
                createLineSeparator(),*/
                btnModifier,
                btnannuler);
        add(content);
        show();

    }

   private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));
        
   }
    
}
