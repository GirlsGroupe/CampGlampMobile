
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
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
        super("  ", BoxLayout.y()); 
      
        Toolbar tbar = new Toolbar(true);
        current = this;
        setToolbar(tbar);
        getTitleArea().setUIID("Container");
    //    setTitle("Modifier Produit");
        getContentPane().setScrollVisible(false);
   //     super.addSideMenu(res);

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

  
}