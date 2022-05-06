/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;


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
import com.codename1.ui.util.Resources;
/**
 *
 * @author Mocha
 */
public class AjouterCentre extends Form  {
     Form current;
     
      public AjouterCentre(Resources res) {

        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter centre");
        getContentPane().setScrollVisible(false);

         Label labelNom = new Label("nom centre :");
        labelNom.setUIID("defaultLabel");
         TextField tfNom = new TextField();
        
         Label labelAdresse = new Label("adresse centre :");
        labelAdresse.setUIID("defaultLabel");
        TextField tfAdresse = new TextField();
       

        Label labeltype = new Label("type : ");
        labeltype.setUIID("defaultLabel");
        TextArea tftype = new TextArea();

         Label labelville = new Label("ville : ");
        labelville.setUIID("defaultLabel");
        TextArea tfville = new TextArea();
        
        Label labelgouvernorat = new Label("gouvernorat : ");
        labelgouvernorat.setUIID("defaultLabel");
        TextArea tfgouvernorat = new TextArea();
        
        Button btnAdd = new Button("Ajouter");
        addStringValue("",btnAdd);
        
        //Onclick event
        btnAdd.addActionListener((e) -> {
            try {
               
                    InfiniteProgress ip = new InfiniteProgress();
                    final Dialog Dialogs = ip.showInfiniteBlocking();
                    centredecamping u = new centredecamping(
                            String.valueOf(labelNom.getText().toString()).toString(),
                            String.valueOf(labelAdresse.getText().toString()).toString(),
                            String.valueOf(labeltype.getText().toString()).toString(),
                            String.valueOf(labelville.getText().toString()).toString(),
                            String.valueOf(labelgouvernorat.getText().toString()).toString());
                            

                            System.out.println("centre ajoutée est :"+ u);
                //appel fonction 
                servicecentre.getInstance().addcentre(u);
                Dialogs.dispose(); // nahi el loading
                refreshTheme();//actualisation
                
                
            
            } catch(Exception ex){
                ex.printStackTrace();
                
            }
        });

    }

    private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));

    }
}
