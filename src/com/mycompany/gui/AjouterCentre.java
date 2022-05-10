/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.mycompany.entities.centredecamping;
import com.mycompany.services.servicecentre;

import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Mocha
 */
public class AjouterCentre extends BaseForm {

    Form current;

    public AjouterCentre(Resources res) {
        super("", BoxLayout.y());

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter centre");
        getContentPane().setScrollVisible(false);

    
        TextField nomcentre = new TextField("", " nom centre", 16, TextField.ANY);
        nomcentre.setUIID("TextFieldBlack");
        addStringValue("NOM", nomcentre);

        TextField adressecentre = new TextField("", " adresse centre", 16, TextField.ANY);
        adressecentre.setUIID("TextFieldBlack");
        addStringValue("ADRESSE", adressecentre);

        TextField prix = new TextField("", " prix", 16, TextField.ANY);
        prix.setUIID("TextFieldBlack");
        addStringValue("PRIX", prix);

        TextField type = new TextField("", " type", 16, TextField.ANY);
        type.setUIID("TextFieldBlack");
        addStringValue("TYPE", type);

        TextField ville = new TextField("", " ville", 16, TextField.ANY);
        ville.setUIID("TextFieldBlack");
        addStringValue("VILLE", ville);

        TextField gouvernorat = new TextField("", " gouvernorat", 16, TextField.ANY);
        gouvernorat.setUIID("TextFieldBlack");
        addStringValue("GOUVERNORAT", gouvernorat);



        Button btnAdd = new Button("Ajouter");
        addStringValue("", btnAdd);

        //Onclick event
        btnAdd.addActionListener((e) -> {
            try {

                InfiniteProgress ip = new InfiniteProgress();
                final Dialog Dialogs = ip.showInfiniteBlocking();
                centredecamping u = new centredecamping(
                        String.valueOf(nomcentre.getText().toString()),
                        String.valueOf(adressecentre.getText().toString()),
                        Float.valueOf(prix.getText().toString()),
                        String.valueOf(type.getText().toString()),
                        String.valueOf(ville.getText().toString()),
                        String.valueOf(gouvernorat.getText().toString()));

                System.out.println("centre ajoutée est :" + u);
                //appel fonction 
                servicecentre.getInstance().addcentre(u);
                Dialogs.dispose(); // nahi el loading
                new Listecentre(res).show();

                refreshTheme();//actualisation

            } catch (Exception ex) {
                ex.printStackTrace();

            }
        });

    }

    private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));
        

    }
}
