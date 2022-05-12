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
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.centredecamping;
import static com.mycompany.gui.ForgetPwd.emailUser;
import com.mycompany.services.servicecentre;
import com.mycompany.services.servicesUtilisateurs;
import java.util.ArrayList;

/**
 *
 * @author Mocha
 */
public class Listecentre extends BaseForm {

    Form current;
     Container cnt= new Container(BoxLayout.x());
  
    public Listecentre(Resources res) {
          super("", BoxLayout.y()); //hesioste nen Newafeed w1 formulaire vertical
        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
        setTitle("Liste des Utilisateurs ");
        getContentPane().setScrollVisible(false);
 super.addSideMenu(res);
   Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("Wall.jpg"), "", "", res);

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Centres", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new ListeUtilisateurs(res).show();
            refreshTheme();
        });
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new AjouterCentre(res).show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(2, mesListes, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });

        
   
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
    
      private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());

        if (image.getHeight() < size) {
            image = image.scaledHeight(size);
        }

        if (image.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }

        ScaleImageLabel imageScale = new ScaleImageLabel(image);
        imageScale.setUIID("Container");
        imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label overLay = new Label("", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        imageScale,
                        overLay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", res.getImage("back-logo.jpeg"), page1);

    }

    public void bindButtonSelection(Button btn, Label l) {

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                updateArrowPosition(btn, l);
            }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {

        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }
}

       