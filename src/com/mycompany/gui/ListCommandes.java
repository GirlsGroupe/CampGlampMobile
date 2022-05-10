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
import com.mycompany.entities.Commande;
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceCommande;
import com.mycompany.services.ServiceProduit;
import java.util.ArrayList;



/**
 *
 * @author LENOVO
 */
public class ListCommandes extends BaseForm{
      Form current;
   
       public  ListCommandes(Resources res) {
                   super("", BoxLayout.y());
           
           Toolbar tbar=new Toolbar(true);
           current=this;
           setToolbar(tbar);
       //     setTitle("Liste des produits");
           getTitleArea().setUIID("container");
           
           getContentPane().setScrollVisible(false);
        //Appel du méthode affichage 
               
               ArrayList<Commande> list= ServiceCommande.getInstance().afficherCommande();
                for (Commande c : list) {
                   
                   addButton( c.getImageCommande() , c.getPrenomutilisateur() , c.getNomutilisateur() , c.getPrixcommande() , c.getAdresseutilisateur(),  c  , res );
              
                
                }
                   
                    System.out.println("commande testing");         
                   
              }

    private void addButton(String imageCommande ,  String prenomutilisateur, String nomutilisateur , String prixproduit, String adresseutilisateur, Commande c, Resources res) {
        
    String urlImage2 = "http://localhost/CampGlampConsommation/public/index.php/images/ "+ imageCommande;
        System.out.println(urlImage2);

        Image placeHolder = Image.createImage(90, 90);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, true);
        URLImage urlim = URLImage.createToStorage(enc, urlImage2, urlImage2, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(15f);
        Button Image = new Button(urlim.fill(width, height));
        Image.setUIID("Label");
    
        
         Container cnt = BorderLayout.west(Image);
      //  Label nompr = new Label(nomproduit);
        Label prixpr = new Label(prixproduit);
         Label prenomu = new Label(prenomutilisateur);
        Label nomu = new Label(nomutilisateur);
           Label adresseu = new Label(adresseutilisateur);
           
           
           
                
     cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
         //       BoxLayout.encloseX(nompr), 
        //     BoxLayout.encloseX(prixpr) , 
                BoxLayout.encloseX(prenomu), 
             BoxLayout.encloseX(nomu) , 
                BoxLayout.encloseX(prixpr)
        
             //   BoxLayout.encloseX(UpdateP, DeletP)
        ));
        add(cnt);
    }
      
      


    
}
