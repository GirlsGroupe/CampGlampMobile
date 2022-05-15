/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Resevation;
import com.mycompany.services.ServiceReservation;

/**
 *
 * @author Khach
 */
public class UpdateReservation extends Form {

    //Form current;

    public UpdateReservation(Resources res, Resevation reservation) {
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Modifier la reservation", "Title")
                )
        );
        setLayout(BoxLayout.y());

        setLayout(BoxLayout.y());
TextField etat = new TextField("", " etat", 16, TextField.ANY);
        etat.setUIID("TextFieldBlack");
        addStringValue("Etat", etat);
        
         TextField datereservation = new TextField("", " datereservation", 16, TextField.ANY);
        datereservation.setUIID("TextFieldBlack");
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("datePicker", datePicker);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick brnModifer
        btnModifier.addActionListener((e) -> {
            try {
                if (etat.getText().isEmpty()) {
                    Dialog.show("Verify your data", "", "cancel", "ok");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog iDialog = ip.showInfiniteBlocking();
                    reservation.setEtat(etat.getText());
                    reservation.setDatereservation(datereservation.getText());
                    ServiceReservation.getInstance().updateReservation(reservation.getIdreservation(), etat, datereservation);
                    iDialog.dispose();
                    new ListReservation(res).show();
                    refreshTheme();
                }

    } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
                addAll(btnModifier);

    }
   

    private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));

    }

}

