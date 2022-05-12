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
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Produit;
import com.mycompany.services.ServiceProduit;

/**
 *
 * @author LENOVO
 */
public class AjouterProduitForm extends BaseForm {
    
    Form current;
    public AjouterProduitForm (Resources res) {
            super("", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Produits");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {

        });

        Tabs swipe = new Tabs();

        Label s1 = new Label();
        Label s2 = new Label();

        addTab(swipe, s1, res.getImage("Wall.jpg"), "", "", res);

        //
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

            new ListProduits(res).show();
            refreshTheme();
        });
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new AjouterProduitForm(res).show();
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
            updateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
    
        
           
        
           
           TextField referenceproduit=new TextField("", "Entrer la référence ");
           referenceproduit.setUIID("TextFieldBlack");
           addStringValue("Référence Produit", referenceproduit); 
           
              TextField nomproduit=new TextField("", "Entrer nom");
           nomproduit.setUIID("TextFieldBlack");
           addStringValue("nom produit", nomproduit);
           
            TextField prixproduit=new TextField("", "Entrer prix");
           prixproduit.setUIID("TextFieldBlack");
           addStringValue("prix produit", prixproduit); 
           
            TextField quantiteproduit=new TextField("", "Entrer quantite");
           quantiteproduit.setUIID("TextFieldBlack");
           addStringValue("quantite produit", quantiteproduit); 
           
            TextField marqueproduit=new TextField("", "Entrer marque");
           marqueproduit.setUIID("TextFieldBlack");
           addStringValue("marque produit", marqueproduit); 
           
            TextField descriptionproduit=new TextField("", "Entrer descriptionp");
           descriptionproduit.setUIID("TextFieldBlack");
           addStringValue("description produit", descriptionproduit); 
           
           
           TextField typeproduit=new TextField("", "Entrer type ");
           typeproduit.setUIID("TextFieldBlack");
           addStringValue("type produit", typeproduit); 
            
           TextField couleur=new TextField("", "Entrer couleur ");
           couleur.setUIID("TextFieldBlack");
           addStringValue("couleur produit", couleur); 
           
           TextField taille=new TextField("", "Entrer taille ");
           taille.setUIID("TextFieldBlack");
           addStringValue("taille produit", taille); 
           
              TextField imageproduit=new TextField("", "Entrer une image  ");
           imageproduit.setUIID("TextFieldBlack");
           addStringValue("image produit", imageproduit); 
           
           
           Button btnAjouter =new Button("Ajouter");
           addStringValue("",btnAjouter);
           
           btnAjouter.addActionListener((e)-> {
               
               
               try {
                   if(referenceproduit.getText() =="" || nomproduit.getText() =="" )
                {
               
               Dialog.show("Veuillez vérifier les données" , "" , "Annuler" , "Ok");
                 }
                   else {
                       InfiniteProgress ip=new InfiniteProgress();;
                       final Dialog iDialog =ip.showInfiniteBlocking();
                   Produit p=new Produit(
                             String.valueOf(referenceproduit.getText()).toString() ,
                             String.valueOf(nomproduit.getText()).toString() , 
                             String.valueOf(prixproduit.getText()).toString() ,
                             String.valueOf(quantiteproduit.getText()).toString() ,
                             String.valueOf(marqueproduit.getText()).toString() ,
                             String.valueOf(descriptionproduit.getText()).toString() ,
                             String.valueOf(typeproduit.getText()).toString() ,
                             String.valueOf(couleur.getText()).toString() ,
                             String.valueOf(taille.getText()).toString() ,
                             String.valueOf(imageproduit.getText()).toString()
                           );
                   
                   
                   
                   System.out.println("informations du produit ==" +p);
                   
                   ServiceProduit.getInstance().ajouterProduit(p);
                      Dialog.show("Produit ajouté avec succées !!" , "" , "Annuler" , "Ok");
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

 /*   private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
int  size=Math.min(Display.getInstance().getDisplayWidth() , Display.getInstance().getDisplayHeight());
 if(image.getHeight() <size) {
     
     image=image.scaledHeight(size); }
 
 
 if (image.getHeight() > Display.getInstance().getDisplayHeight() /2) {
     image =image.scaledHeight(Display.getInstance().getDisplayHeight() /2);
     
 }
 
 ScaleImageLabel imageScale=new ScaleImageLabel(image);
 imageScale.setUIID("container");
 imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
 Label overLay =new Label("", "ImageOverlay");
 
 Container page1=
         LayeredLayout.encloseIn(
         imageScale , 
                  overLay,
                  BorderLayout.south(
                  
                  BoxLayout.encloseY(
                  new SpanLabel(text , "LargeWhiteText"),
                          FlowLayout.encloseIn(null),
                           spacer))
                 );
 swipe.addTab("", res.getImage("back-logo.png"), page1);
         
 
 
 }
    public void bindButtonSelection (Button btn , Label l) {
        btn.addActionListener(e->
        
        {   if(btn.isSelected()){
            
            updateArrowPosition(btn,l); }
        });
    }

    private void updateArrowPosition(Button btn, Label l) {
l.getUnselectedStyle().setMargin(LEFT , btn.getX() + btn.getWidth() / 2 - l.getWidth() /2 );    

l.getParent().repaint(); } */
    
}
