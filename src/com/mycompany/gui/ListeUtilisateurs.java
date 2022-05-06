/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
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
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.services.servicesUtilisateurs;
import java.util.ArrayList;

/**
 *
 * @author MossMoss
 */
public class ListeUtilisateurs  extends BaseForm {
    Form current;

    public ListeUtilisateurs(Resources res) {
   super ("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical
    Toolbar tbar = new Toolbar(true);
    current = this;
    setToolbar(tbar);
     getTitleArea().setUIID("Container");
     setTitle("Liste des Utilisateurs ");
     getContentPane().setScrollVisible(false);

     ArrayList<Utilisateurs> list= servicesUtilisateurs.getInstance().afficherUtilisateurs();
     
    for (Utilisateurs user:list){
        addButton( user.getImage(),user.getNomUser(), user);
   
    
}

    
    }
    
    
    private void addButton( String image ,String nomUser, Utilisateurs user) {
        String urlImage = "http://127.0.0.1:8000/uploads/ProfileImage/"+image;
                System.out.println(urlImage);

            Image placeHolder = Image.createImage(90, 90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder, true);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
            int height = Display.getInstance().convertToPixels(11.5f);
            int width = Display.getInstance().convertToPixels(15f);
            Button Image = new Button(urlim.fill(width, height));
            Image.setUIID("Label");
        Container cnt = BorderLayout.west(Image);
        TextArea ta=new TextArea (nomUser);
        ta.setUIID("NewaTopLine");
        ta.setEditable (false);
        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(ta));
          add (cnt);
    }
    
   
}
    
