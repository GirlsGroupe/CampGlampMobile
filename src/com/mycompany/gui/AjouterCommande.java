/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Commande;
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceCommande;
import com.mycompany.services.ServiceProduit;


/**
 *
 * @author LENOVO
 */
public class AjouterCommande extends BaseForm {
     Form current;
    public AjouterCommande (Resources res) {
           super(" Ajouter commande ", BoxLayout.y());
           
           Toolbar tb=new Toolbar(true);
           current=this;
           setToolbar(tb);
           //  setTitle("Ajout Produit");
           getTitleArea().setUIID("container");

           getContentPane().setScrollVisible(false);
           
        TextField nomutilisateur=new TextField("", "Entrer nom");
           nomutilisateur.setUIID("TextFieldBlack");
           addStringValue("nom utilisateur", nomutilisateur);
           
            TextField prenomutilisateur=new TextField("", "Entrer prénom");
           prenomutilisateur.setUIID("TextFieldBlack");
           addStringValue("prénom utilisateur", prenomutilisateur); 
           
            TextField adresseutilisateur=new TextField("", "Entrer adresse");
           adresseutilisateur.setUIID("TextFieldBlack");
           addStringValue(" adresse utilisateur", adresseutilisateur); 
           
            TextField role=new TextField("", "Entrer role");
           role.setUIID("TextFieldBlack");
           addStringValue("role utilisateur", role); 
           
            TextField prixcommande=new TextField("", "Entrer prix commande");
           prixcommande.setUIID("TextFieldBlack");
           addStringValue("prix commande ", prixcommande); 
           
           
       
           
           Button btnAjouterCommande =new Button("Ajouter");
           addStringValue("",btnAjouterCommande);
           
           btnAjouterCommande.addActionListener((e)-> {
               
               
               try {
                   if(nomutilisateur.getText() =="" || nomutilisateur.getText() =="" || prenomutilisateur.getText() =="" || adresseutilisateur.getText() ==""  ||role.getText() =="" || prixcommande.getText() ==""  )
                {
               
               Dialog.show("Veuillez vérifier les données" , "" , "Annuler" , "Ok");
                 }
                   else {
                       InfiniteProgress ip=new InfiniteProgress();;
                       final Dialog iDialog =ip.showInfiniteBlocking();
                   Commande c=new Commande(
                       
                             String.valueOf(nomutilisateur.getText()).toString() ,
                             String.valueOf(prenomutilisateur.getText()).toString() , 
                             String.valueOf(adresseutilisateur.getText()).toString() ,
                             String.valueOf(role.getText()).toString() ,
                             String.valueOf(prixcommande.getText()).toString() 
                          
                           );
                   
                   
                   
                   System.out.println("informations du commande ==" +c);
                   
                   ServiceCommande.getInstance().ajouterCommande(c);
                      Dialog.show("Commande ajouté avec succées !!" , "" , "Annuler" , "Ok");
                   iDialog.dispose();
                      new ListProduits(res).show();

                   refreshTheme();
                   
                           
                   
                   }
               
           }catch(Exception ex) {
               ex.printStackTrace();
               
           }
               
           });
           
         
           
    
    
                }

    private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeeee));
        
        
        
    }
    
}
