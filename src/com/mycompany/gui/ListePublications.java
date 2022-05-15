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
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Publications;
import com.mycompany.services.ServicePublications;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 * @author MossMoss
 */
public class ListePublications extends BaseForm  {
        Form current;

    
        public ListePublications(Resources res) {
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
        Container Cpost = new Container(BoxLayout.y());

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
        RadioButton mesListes = RadioButton.createToggle("Publications", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new ListePublications(res).show();
            refreshTheme();
        });
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new AjouterPublication(res).show();
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
        
        
        
          ArrayList<Publications> list = ServicePublications.getInstance().getAllPublications();

        for (Publications pub : list) {
            String urlImage = "http://127.0.0.1:8000/uploads/PublicationImage/"+pub.getSourcePub();
                        System.out.println(urlImage);

             EncodedImage enc3 = EncodedImage.createFromImage(res.getImage("loading.png"), true);
             Image image = URLImage.createToStorage(enc3, urlImage, urlImage);
              
                Container single = addButton(pub, image, pub.getIdUser(), pub.getDescriptionPub(),pub.getNbrlikes(),pub.getDateCreation());
                Cpost.add(single);    
        }
                add(Cpost);
        }
        
        
        
        private Container addButton(Publications publication, Image img, LinkedHashMap<Object, Object> user, String title, int nbr, String DateC) {
        int height = Display.getInstance().convertToPixels(61.5f);
        int width = Display.getInstance().convertToPixels(60f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        Container cnt = BorderLayout.center(image);
        TextArea ta = new TextArea(title);
        ta.setUIID("NewsTopLine2");
        ta.setEditable(false);

        TextArea ua = new TextArea("@"+(String) user.get("nomuser") + " " + (String) user.get("prenomuser"));
        ua.setUIID("NewsTopLine2");
        ua.setEditable(false);
        Label likes = new Label(" Aimé par "+ nbr, "NewsBottomLine2");
        Label datee = new Label(DateC);
        datee.getAllStyles().setFgColor(0xC12222);


        likes.getAllStyles().setFgColor(0x5f5f5f);
        likes.setTextPosition(RIGHT);

        Style s = new Style(likes.getUnselectedStyle());
        s.setFgColor(0x5f5f5f);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, s);
        likes.setIcon(heartImage);

       
        //FontImage.setMaterialIcon(likes, FontImage.MATERIAL_FAVORITE);

        Container cua = new Container(new BorderLayout());

        cua.add(BorderLayout.WEST, ua);
        cua.add(BorderLayout.EAST, datee);

        Label spacer1 = new Label("aa");
        spacer1.getAllStyles().setFgColor(0xFFFFFF);
        cnt.add(BorderLayout.NORTH, cua);
        cnt.add(BorderLayout.SOUTH,
                BoxLayout.encloseY(
                        BoxLayout.encloseX(likes),
                        ta,
                        spacer1
                ));

        return cnt;

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
