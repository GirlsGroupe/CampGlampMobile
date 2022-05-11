/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Resevation;
import com.mycompany.services.ServiceReservation;
import java.util.ArrayList;

/**
 *
 * @author Khach
 */
public class ListReservation extends Form {
    
    Form current;
    Container cnt= new Container(BoxLayout.x());
    
    public ListReservation(Resources res) {
   //super ("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical
    getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Liste des reservations", "Title")
                )
        );
        setLayout(BoxLayout.y());

             ArrayList<Resevation> list = ServiceReservation.getInstance().getReservation();

        for (Resevation reservation : list) {
            addButton(reservation.getIdreservation(),
                    reservation.getEtat(),
                    reservation.getDatereservation(),
                    reservation,res);

        }
        }

 
    private void addButton(int idreservation,String etat, String datereservation, Resevation reservation, Resources res) {
       Label Name = new Label(etat);

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
            if (dig.show("Suppression", "Voulez-vous vraiment supprimer cette reservation?", "Annuler", "Confirmer")) {
            System.out.println("-----------------------------");

                dig.dispose();
            } else {
                dig.dispose();
                            System.out.println("-----------------------------");

                if (ServiceReservation.getInstance().deleteReservation(reservation.getIdreservation())) {

                    System.out.println(reservation.getIdreservation());
                    new ListReservation(res).show();
                    refreshTheme();
                }
            }
        });
        Label UpdateR = new Label("");
        UpdateR.setUIID("NewsTopline");
        Style modifierStyle = new Style(UpdateR.getUnselectedStyle());
        modifierStyle.setFgColor(0x63e6be);
        FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
        UpdateR.setIcon(mFontImage);
        UpdateR.setTextPosition(LEFT);
        UpdateR.addPointerPressedListener(l -> {
            new UpdateReservation(res, reservation).show();
        });
      Container cnt = new Container(new BorderLayout()).add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(Name, Deletc, UpdateR)));
        add(cnt);
}
}