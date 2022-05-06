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
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.services.servicesUtilisateurs;

/**
 *
 * @author MossMoss
 */
public class AjouterUtilisateur extends BaseForm {

    Form current;

    public AjouterUtilisateur(Resources res) {
    super ("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Utilisateur");
        getContentPane().setScrollVisible(false);

       
        
        TextField Cin = new TextField("", "Numéro de cin",16,TextField.ANY);
        Cin.setUIID("TextFieldBlack");
        addStringValue("Cin", Cin);

        TextField Nom = new TextField("", "Nom!",16,TextField.ANY);
        Nom.setUIID("TextFieldBlack");
        addStringValue("Nom", Nom);

        TextField Prenom = new TextField("", " Prenom!",16,TextField.ANY);
        Prenom.setUIID("TextFieldBlack");
        addStringValue("Prenom", Prenom);

        TextField Tel = new TextField("", "Numéro de téléphone!",16,TextField.ANY);
        Tel.setUIID("TextFieldBlack");
        addStringValue("Telephone", Tel);

        TextField Adresse = new TextField("", "Adresse!",16,TextField.ANY);
        Adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse", Adresse);

        TextField Email = new TextField("", " Email!",16, TextField.ANY);
        Email.setUIID("TextFieldBlack");
        addStringValue("Email", Email);

        TextField Motdepasse = new TextField("", "Mot de passe ", 16, TextField.PASSWORD);
        Motdepasse.setUIID("TextFieldBlack");
        addStringValue("Mot de passe", Motdepasse);
        
        Button btnAdd = new Button("Ajouter");
        addStringValue("",btnAdd);
        
        //Onclick event
        btnAdd.addActionListener((e) -> {
            try {
                if(Cin.getText()=="" || Nom.getText()=="" ||  Prenom.getText()=="" ||  Tel.getText()=="" ||  Adresse.getText()=="" ||  Email.getText()=="" ||  Motdepasse.getText() == ""){
                    Dialog.show("Veuillez saisir les champs manquants","" ,"Annuler", "OK");
                } else {
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog Dialogs = ip.showInfiniteBlocking();
                    Utilisateurs u = new Utilisateurs(String.valueOf(Cin.getText().toString()).toString(),String.valueOf(Nom.getText().toString()).toString(),
                            String.valueOf(Prenom.getText().toString()).toString(), String.valueOf(Tel.getText().toString()).toString(),
                            String.valueOf(Adresse.getText().toString()).toString(),String.valueOf(Email.getText().toString()).toString(),
                            String.valueOf(Motdepasse.getText().toString()).toString());

                            System.out.println("L'utilisateur ajoutée est :"+ u);
                //appel fonction 
                servicesUtilisateurs.getInstance().ajoutUtilisateur(u);
                Dialogs.dispose(); // nahi el loading
                new ListeUtilisateurs(res).show();
                refreshTheme();//actualisation
                }
                
                
            } catch(Exception ex){
                ex.printStackTrace();
               
            }
        });

    }

    private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));
        add(createLineSeparator(0xeeeeee));

    }
}
