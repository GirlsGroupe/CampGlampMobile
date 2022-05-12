/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.centredecamping;
import com.mycompany.services.servicecentre;

/**
 *
 * @author Mocha
 */
public class modifiercentre extends BaseForm {

    Form current;

    public modifiercentre(Resources res, centredecamping centre) {
        super("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("modifier centre");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);

        TextField nomcentre = new TextField(centre.getNomCentre(), " nom centre", 16, TextField.ANY);
        nomcentre.setUIID("HewsTopLine");
        nomcentre.setSingleLineTextArea(true);

        TextField adressecentre = new TextField(centre.getAdresseCentre(), " adresse centre", 16, TextField.ANY);
        adressecentre.setUIID("HewsTopLine");
        adressecentre.setSingleLineTextArea(true);

//        TextField prix = new TextField(centre.getPrixCentre(), " prix", 16, TextField.ANY);
  //      prix.setUIID("HewsTopLine");
//        prix.setSingleLineTextArea(true); 

        TextField type = new TextField(centre.getTypeCentre(), " type", 16, TextField.ANY);
        type.setUIID("NevaTopLine");
        type.setSingleLineTextArea(true);

        TextField ville = new TextField(centre.getVilleCentre(), " ville", 16, TextField.ANY);
        ville.setUIID("NewaTopLine");
        ville.setSingleLineTextArea(true);

        TextField gouvernorat = new TextField(centre.getGouvernorat(), " gouvernorat", 16, TextField.ANY);
        gouvernorat.setUIID("NewaTopLine");
        gouvernorat.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick brnModifer
        btnModifier.addActionListener((e) -> {
            
            centre.setNomCentre(nomcentre.getText());
            centre.setAdresseCentre(adressecentre.getText());
           // centre.setPrixCentre(prix.getText());
            centre.setTypeCentre(type.getText());
            centre.setVilleCentre(ville.getText());
            centre.setGouvernorat(gouvernorat.getText());
            
            if (servicecentre.getInstance().modifiercentre(centre, centre.getIdCentre())) {
                new Listecentre(res).show();
            }
        });
        Button btnannuler = new Button("Annuler");
        btnannuler.setUIID("Button");

        btnannuler.addActionListener((e) -> {

            new Listecentre(res).show();

        });
        Label M1 = new Label("");
        Label M2 = new Label("");
        Label M3 = new Label("");
        Label M4 = new Label("");
        Label M5 = new Label("");

        Container content = BoxLayout.encloseY(
                M1, M2,
                new FloatingHint(nomcentre),
                new FloatingHint(adressecentre),
//                new FloatingHint(prix),
                new FloatingHint(type),
                new FloatingHint(ville),
                new FloatingHint(gouvernorat),
                //   createLineSeparator(),
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
