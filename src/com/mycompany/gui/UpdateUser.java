/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.services.servicesUtilisateurs;

/**
 *
 * @author MossMoss
 */
public class UpdateUser extends BaseForm {

    Form current;

    public UpdateUser(Resources res, Utilisateurs user) {
        super("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Utilisateur");
        getContentPane().setScrollVisible(false);
        super.addSideMenu(res);

        TextField cin = new TextField(user.getCinUser(), "CIN", 16, TextField.ANY);
        TextField Nom = new TextField(user.getNomUser(), "Nom!", 16, TextField.ANY);
        TextField Prenom = new TextField(user.getPrenomUser(), "Prenom!", 16, TextField.ANY);
        TextField Tel = new TextField(user.getTelUser(), "Numéro de téléphone!", 16, TextField.ANY);
        TextField Adresse = new TextField(user.getAdresseUser(), "Adresse!", 16, TextField.ANY);
        TextField Email = new TextField(user.getEmail(), "Email!", 16, TextField.ANY);

        cin.setUIID("HewsTopLine");
        Nom.setUIID("NevaTopLine");
        Prenom.setUIID("NewaTopLine");
        Tel.setUIID("NewaTopLine");
        Adresse.setUIID("NewaTopLine");
        Email.setUIID("NewaTopLine");

        cin.setSingleLineTextArea(true);
        Nom.setSingleLineTextArea(true);
        Prenom.setSingleLineTextArea(true);
        Tel.setSingleLineTextArea(true);
        Adresse.setSingleLineTextArea(true);
        Email.setSingleLineTextArea(true);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        //Event onclick brnModifer
        btnModifier.addActionListener((e) -> {

            user.setCinUser(cin.getText());
            user.setNomUser(Nom.getText());
            user.setPrenomUser(Prenom.getText());
            user.setTelUser(Tel.getText());
            user.setAdresseUser(Adresse.getText());
            user.setEmail(Email.getText());

  
        if (servicesUtilisateurs.getInstance().updateUser(user, user.getIdUser())) {
            System.out.println("hhh");
            new ListeUtilisateurs(res).show();
        }
      });
        Button btnannuler = new Button("Annuler");
        btnannuler.addActionListener((e) -> {

            new ListeUtilisateurs(res).show();

        });
        Label M1 = new Label("");
        Label M2 = new Label("");
        Label M3 = new Label("");
        Label M4 = new Label("");
        Label M5 = new Label("");

        Container content = BoxLayout.encloseY(
                M1, M2,
                new FloatingHint(cin),
                createLineSeparator(),
                new FloatingHint(Nom),
                createLineSeparator(),
                new FloatingHint(Prenom),
                createLineSeparator(),
                new FloatingHint(Tel),
                createLineSeparator(),
                new FloatingHint(Adresse),
                createLineSeparator(),
                new FloatingHint(Email),
                createLineSeparator(),
                btnModifier,
                btnannuler);
        add(content);
        show();

    }
}
