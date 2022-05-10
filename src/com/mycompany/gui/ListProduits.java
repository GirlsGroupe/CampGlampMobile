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
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;
import java.util.ArrayList;

/**
 *
 * @author LENOVO
 */
public class ListProduits extends BaseForm {
     Form current;
 Container cnt= new Container(BoxLayout.x());
  
    public  ListProduits(Resources res) {
            getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                    new Label("Centre List", "Title")
                )
        );
        setLayout(BoxLayout.y());
   
             ArrayList<Produit> list = ServiceProduit.getInstance().afficherProduits();

        for (Produit pr : list) {
            addButton(pr.getReferenceproduit(),
                    pr.getNomproduit(),
                    pr.getPrixproduit(),
                    pr.getMarqueproduit(),
                    pr.getDescriptionproduit(),
                    pr.getTypeproduit(),
                    pr.getCouleur(),
                    pr.getTaille(),
                    pr.getImageproduit(),
                    pr,res);
            System.out.println(                    pr.getNomproduit()
);
        }
    }

 
       

    /*private void addStringValue(String s, Component v) {
        
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
                .add(BorderLayout.CENTER,v));
        add(createLineSeparator(0xeeeeeee));
        
        
        
    } */

  
   
    private void addButton(String reference , String nomproduit, String type, String prixproduit ,String image, String taille,String couleur, String description, String marque, Produit p ,  Resources res) {

  Button Ajouter = new Button("Ajouter");
Ajouter.addActionListener(e -> {
          new AjouterProduitForm(res).show();
            refreshTheme();
        });

       
        Label nompr = new Label(nomproduit);
        Label prixpr = new Label(prixproduit);
                nompr.setUIID("NewaTopLine");

        //Supprimer 
         Label DeletP = new Label(" ");
        DeletP.setUIID("delete");
        Style supprimerStyle = new Style(DeletP.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        DeletP.setIcon(supprimerImage);
        DeletP.setTextPosition(RIGHT);
        
        
         DeletP.addPointerPressedListener(l -> {
             Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression", "Voulez-vous vraiment supprimer ce produit?", "Annuler", "Confirmer")) {
            System.out.println("-----------------------------");

                dig.dispose();

            } else {
                dig.dispose();
                            System.out.println("-----------------------------");

                if (ServiceProduit.getInstance().SupprimerProduit(p.getIdproduit())) {
                                                System.out.println("-----------------------------");

                    System.out.println(p.getIdproduit());
                    new ListProduits(res).show();
                    refreshTheme();
                }
            }
        });
    
         
         //modifier 
          Label UpdateP = new Label(" ");
        UpdateP.setUIID("NewsTopLine");
        Style modifierStyle = new Style(UpdateP.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        UpdateP.setIcon(mFontImage);
        UpdateP.setTextPosition(LEFT);
        
        
        UpdateP.addPointerPressedListener(l -> {
            System.out.println("heeeey");
            new ModifierProduit(res, p).show();
            System.out.println("holaaa");
           
        });
        
   Container cnt = new Container(new BorderLayout()).add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(nompr, DeletP,UpdateP,Ajouter)));
        add(cnt);
        
}
        

                }


   

