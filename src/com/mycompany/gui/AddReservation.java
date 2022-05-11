/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
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
public class AddReservation extends Form {

    //private String urlAddReservation = "http://127.0.0.1:8000/addRes";
    //private ConnectionRequest cnxAddReservation;
    Form current;

    public AddReservation(Resources res) {
        super ("", BoxLayout.y());
        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter reservation");
        getContentPane().setScrollVisible(false);

          TextField etat = new TextField("", " etat", 16, TextField.ANY);
        etat.setUIID("TextFieldBlack");
        addStringValue("Etat", etat);
        
         TextField datereservation = new TextField("", " datereservation", 16, TextField.ANY);
        datereservation.setUIID("TextFieldBlack");
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        addStringValue("datePicker", datePicker);       
        TextField id = new TextField("", "id", 16, TextField.ANY);
        id.setUIID("TextFieldBlack");
        addStringValue("Evenement", id);
       
        TextField iduser = new TextField("", " iduser", 16, TextField.ANY);
        iduser.setUIID("TextFieldBlack");
        addStringValue("Utilisateur", iduser);

        Button btnAdd = new Button("Ajouter");
        addStringValue("",btnAdd);
        
        //Onclick event
        btnAdd.addActionListener((ActionEvent e) -> {
            try {
               
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog Dialogs = ip.showInfiniteBlocking();
                    SimpleDateFormat format = new SimpleDateFormat ("yyyy-mm-dd");
                    Resevation r = new Resevation(
                            String.valueOf(etat.getText()).toString(),
                            format.format (datePicker.getDate()),
                            Integer.valueOf(id.getText()),
                            Integer.valueOf(iduser.getText()));
                            System.out.println("reservation ajoutée est :"+ r);
                //appel fonction 
                ServiceReservation.getInstance().addReservation(r);
                 Dialogs.dispose(); // nahi el loading
                new ListReservation(res).show();
                refreshTheme();//actualisation//actualisation
            } catch(Exception ex){
                ex.printStackTrace();
                    }
                });
            }

    private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));
        //add(createLineSeparator(0xeeeeee));
        

    }

    }