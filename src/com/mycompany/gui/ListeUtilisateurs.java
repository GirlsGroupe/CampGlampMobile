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
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.services.servicesUtilisateurs;
import java.util.ArrayList;

/**
 *
 * @author MossMoss
 */
public class ListeUtilisateurs extends BaseForm {

    Form current;

    public ListeUtilisateurs(Resources res) {
        super("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical
        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Liste des Utilisateurs ");
        getContentPane().setScrollVisible(false);

        ArrayList<Utilisateurs> list = servicesUtilisateurs.getInstance().afficherUtilisateurs();

        for (Utilisateurs user : list) {
            addButton(user.getImage(), user.getNomUser(), user, res);

        }

    }

    private void addButton(String image, String nomUser, Utilisateurs user, Resources res) {
        String urlImage = "http://127.0.0.1:8000/uploads/ProfileImage/" + image;
        System.out.println(urlImage);

        Image placeHolder = Image.createImage(90, 90);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, true);
        URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(15f);
        Button Image = new Button(urlim.fill(width, height));
        Image.setUIID("Label");

        Container cnt = BorderLayout.west(Image);
        Label Name = new Label(nomUser);

        Name.setUIID("NewaTopLine");
        Label DeletU = new Label(" ");
        DeletU.setUIID("delete");
        Style supprimerStyle = new Style(DeletU.getUnselectedStyle());
        supprimerStyle.setFgColor(0xf21f1f);
        FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
        DeletU.setIcon(supprimerImage);
        DeletU.setTextPosition(RIGHT);
        //click
        DeletU.addPointerPressedListener(l -> {
            Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression", "Voulez-vous vraiment supprimer cet utilisateur?", "Annuler", "Confirmer")) {
            System.out.println("-----------------------------");

                dig.dispose();

            } else {
                dig.dispose();
                            System.out.println("-----------------------------");

                if (servicesUtilisateurs.getInstance().deleteUser(user.getIdUser())) {
                                                System.out.println("-----------------------------");

                    System.out.println(user.getIdUser());
                    new ListeUtilisateurs(res).show();
                    refreshTheme();
                }
            }
        });

        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(
                BoxLayout.encloseX(Name),
                BoxLayout.encloseX(DeletU)
        ));
        add(cnt);
    }

}
