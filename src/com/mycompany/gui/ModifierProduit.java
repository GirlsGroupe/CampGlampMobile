
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
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
 * @author MossMoss
 */
public class ModifierProduit extends BaseForm {

    Form current;
    Produit p;

    public ModifierProduit(Resources res, Produit p) {
         super("", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Mofifier Produit");
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
        RadioButton mesListes = RadioButton.createToggle("Produit", barGroup);
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
    

        TextField referenceproduit = new TextField(p.getReferenceproduit(), "Reference produit"  ,16, TextField.ANY);
        TextField nomproduit = new TextField(p.getNomproduit(), "Nom produit");
        TextField prixproduit = new TextField(p.getPrixproduit(), "Prix produit");
        TextField quantiteproduit = new TextField(p.getQuantiteproduit(), "Quantité produit");
        TextField marqueproduit = new TextField(p.getMarqueproduit(), "Marque produit");
        TextField descriptionproduit = new TextField(p.getDescriptionproduit(), "Description produit");
         TextField typeproduit = new TextField(p.getTypeproduit(), "Type produit");
        TextField couleur = new TextField(p.getCouleur(), "Couleur produit");
        TextField taille = new TextField(p.getTaille(), "Taille produit");
          TextField imageproduit = new TextField(p.getImageproduit(), "Image produit");


        referenceproduit.setUIID("HewsTopLine");
        nomproduit.setUIID("NevaTopLine");
        prixproduit.setUIID("NevaTopLine");
        quantiteproduit.setUIID("NevaTopLine");
        marqueproduit.setUIID("NevaTopLine");
        descriptionproduit.setUIID("NevaTopLine");
          typeproduit.setUIID("NevaTopLine");
        couleur.setUIID("NevaTopLine");
        taille.setUIID("NevaTopLine");
        imageproduit.setUIID("NevaTopLine");

        referenceproduit.setSingleLineTextArea(true);
        nomproduit.setSingleLineTextArea(true);
        prixproduit.setSingleLineTextArea(true);
        quantiteproduit.setSingleLineTextArea(true);
        marqueproduit.setSingleLineTextArea(true);
        descriptionproduit.setSingleLineTextArea(true);
         typeproduit.setSingleLineTextArea(true);
        couleur.setSingleLineTextArea(true);
        taille.setSingleLineTextArea(true);
        imageproduit.setSingleLineTextArea(true);
        
        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");
        
          btnModifier.addPointerPressedListener(l ->   { 
           
    
           
         p.setReferenceproduit(referenceproduit.getText());
         p.setNomproduit(nomproduit.getText());
         p.setPrixproduit(prixproduit.getText());
         p.setMarqueproduit(marqueproduit.getText());
         p.setDescriptionproduit(descriptionproduit.getText());
         p.setTypeproduit(typeproduit.getText());
         p.setCouleur(couleur.getText());
         p.setTaille(taille.getText());
         p.setImageproduit(imageproduit.getText());
         
  
       
       if(ServiceProduit.getInstance().ModifierProduit(p,  p.getIdproduit())) { 
           new ListProduits(res).show();
       }
        });
       Button btnAnnuler = new Button("Annuler");
       btnAnnuler.addActionListener(e -> {
           new ListProduits(res).show();
       });

      
        Label M1 = new Label("");
        Label M2 = new Label("");
        Label M3 = new Label("");
        Label M4 = new Label("");
        Label M5 = new Label("");

        Container content = BoxLayout.encloseY(
                M1, M2,M3,
                new FloatingHint(referenceproduit),
                createLineSeparator(),
                new FloatingHint(nomproduit),
                createLineSeparator(),
                new FloatingHint(prixproduit),
                createLineSeparator(),
                new FloatingHint(quantiteproduit),
                createLineSeparator(),
                new FloatingHint(marqueproduit),
                createLineSeparator(),
                new FloatingHint(descriptionproduit),
                createLineSeparator(),
                new FloatingHint(typeproduit),
                createLineSeparator(),
                new FloatingHint(couleur),
                createLineSeparator(),
                new FloatingHint(taille),
                createLineSeparator(),
                 new FloatingHint(imageproduit),
                createLineSeparator(),
                btnModifier
               ,btnAnnuler
                );
        add(content);
        show();

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