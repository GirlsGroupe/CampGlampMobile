package com.camp.gui.front.commantaire;


import com.camp.entities.Commantaire;
import com.camp.entities.Reclamation;
import com.camp.services.CommantaireService;
import com.camp.services.ReclamationService;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;

public class Manage extends Form {


    Commantaire currentCommantaire;

    TextField descriptionTF;
    Label descriptionLabel;


    ArrayList<Reclamation> listReclamations;
    PickerComponent reclamationPC;
    Reclamation selectedReclamation = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentCommantaire == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentCommantaire = ShowAll.currentCommantaire;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] reclamationStrings;
        int reclamationIndex;
        reclamationPC = PickerComponent.createStrings("").label("Reclamation");
        listReclamations = ReclamationService.getInstance().getAll();
        reclamationStrings = new String[listReclamations.size()];
        reclamationIndex = 0;
        for (Reclamation reclamation : listReclamations) {
            reclamationStrings[reclamationIndex] = reclamation.getTitre();
            reclamationIndex++;
        }
        if (listReclamations.size() > 0) {
            reclamationPC.getPicker().setStrings(reclamationStrings);
            reclamationPC.getPicker().addActionListener(l -> selectedReclamation = listReclamations.get(reclamationPC.getPicker().getSelectedStringIndex()));
        } else {
            reclamationPC.getPicker().setStrings("");
        }


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        if (currentCommantaire == null) {


            manageButton = new Button("Ajouter");
        } else {
            descriptionTF.setText(currentCommantaire.getDescription());


            reclamationPC.getPicker().setSelectedString(currentCommantaire.getReclamation().getTitre());
            selectedReclamation = currentCommantaire.getReclamation();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                descriptionLabel, descriptionTF,

                reclamationPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentCommantaire == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommantaireService.getInstance().add(
                            new Commantaire(


                                    descriptionTF.getText(),
                                    selectedReclamation
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Commantaire ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de commantaire. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = CommantaireService.getInstance().edit(
                            new Commantaire(
                                    currentCommantaire.getId(),


                                    descriptionTF.getText(),
                                    selectedReclamation

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Commantaire modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de commantaire. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAll) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (selectedReclamation == null) {
            Dialog.show("Avertissement", "Veuillez choisir un reclamation", new Command("Ok"));
            return false;
        }


        return true;
    }
}