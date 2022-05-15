/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        String urlImage = "http://127.0.0.1:8000/uploads/ProfileImage/" + SessionManager.getPhoto();
        int size = Display.getInstance().convertToPixels(1);

        Image placeHolder = Image.createImage(size, size,0);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, true);
        URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(15f);
        Button Image = new Button(urlim.fill(width, height));
        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        Image)
        ));
        if (SessionManager.getRole().equals("[ROLE_admin]") ) {
            tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
            tb.addMaterialCommandToSideMenu("Centre de camping", FontImage.MATERIAL_UPDATE, e -> new Listecentre(res).show());
            tb.addMaterialCommandToSideMenu("Voir la carte", FontImage.MATERIAL_UPDATE, e -> new MapForm(res));
            tb.addMaterialCommandToSideMenu("Produit", FontImage.MATERIAL_UPDATE, e -> new ListProduits(res).show());
            tb.addMaterialCommandToSideMenu("Evenement", FontImage.MATERIAL_UPDATE, e -> new ShowEvent(res).show());
            tb.addMaterialCommandToSideMenu("Reclamation", FontImage.MATERIAL_UPDATE, e -> new ShowAllReclamation(this).show());
            tb.addMaterialCommandToSideMenu("Commentaire", FontImage.MATERIAL_UPDATE, e -> new ShowAllComment(this).show());
            tb.addMaterialCommandToSideMenu("Avis", FontImage.MATERIAL_UPDATE, e -> new ShowAllAvis(this).show());
            tb.addMaterialCommandToSideMenu("Réservation", FontImage.MATERIAL_UPDATE, e -> new ListReservation(res).show());
            tb.addMaterialCommandToSideMenu("Utilisateurs", FontImage.MATERIAL_UPDATE, e -> new ListeUtilisateurs(res).show());

            tb.addMaterialCommandToSideMenu("Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                new SignInForm(res).show();
                SessionManager.pref.clearAll();
                Storage.getInstance().clearStorage();
                Storage.getInstance().clearCache();
            }
            );
            refreshTheme();
        } else if (SessionManager.getRole().equals("[ROLE_guide]") ) {
            tb.addMaterialCommandToSideMenu("Publications", FontImage.MATERIAL_UPDATE, e -> new ListePublications(res).show());
            tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
            tb.addMaterialCommandToSideMenu("Déconnexion", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                new SignInForm(res).show();
                SessionManager.pref.clearAll();
                Storage.getInstance().clearStorage();
                Storage.getInstance().clearCache();
            }
            );
            refreshTheme();
        }
        else {
            
        }

    }
}
