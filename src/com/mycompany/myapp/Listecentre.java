/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.RIGHT;
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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.centredecamping;
import com.mycompany.services.servicecentre;
import java.util.ArrayList;

/**
 *
 * @author Mocha
 */
public class Listecentre extends BaseForm {

    Form current;
     Container cnt= new Container(BoxLayout.x());
  
    public Listecentre(Resources res) {
        getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Centre List", "Title")
                )
        );
        setLayout(BoxLayout.y());

             ArrayList<centredecamping> list = servicecentre.getInstance().affichagecentres();

        for (centredecamping centre : list) {
            addButton(centre.getIdCentre(),
                    centre.getNomCentre(),
                    centre.getAdresseCentre(),
                    centre.getPrixCentre(),
                    centre.getTypeCentre(),
                    centre.getVilleCentre(),
                    centre.getGouvernorat(),
                    centre,res);

        }


    }

    
    private void addButton(int idcentre,String nomcentre, String adresse,float prixcentre,String typecentre,String ville,String gouvernorat, centredecamping centre, Resources res) {
   
        
        Label Name = new Label(nomcentre);

        Name.setUIID("NewaTopLine");
        Label Deletc = new Label(" ");
        Deletc.setUIID("delete");
        Style supprimerStyle = new Style(Deletc.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        Deletc.setIcon(supprimerImage);
        Deletc.setTextPosition(RIGHT);
        //click
        Deletc.addPointerPressedListener(l -> {
            Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression", "Voulez-vous vraiment supprimer ce centre?", "Annuler", "Confirmer")) {
                System.out.println("-----------------------------");

                dig.dispose();

            } else {
                dig.dispose();
                System.out.println("-----------------------------");

                if (servicecentre.getInstance().deletecentre(centre.getIdCentre())) {
                    System.out.println("-----------------------------");
                    System.out.println(centre.getIdCentre());
                    new Listecentre(res).show();
                    refreshTheme();
                }
            }
        });
        
        
         Label UpdateU = new Label(" ");
        UpdateU.setUIID("NewsTopLine");
        Style modifierStyle = new Style(UpdateU.getUnselectedStyle());
        modifierStyle.setFgColor(0xf7ad02);
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        UpdateU.setIcon(mFontImage);
        UpdateU.setTextPosition(LEFT);
        UpdateU.addPointerPressedListener(l -> {
            new modifiercentre(res, centre).show();
            
        });
            Container cnt = new Container(new BorderLayout()).add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(Name, Deletc,UpdateU)));
      add(cnt);
}
}

       