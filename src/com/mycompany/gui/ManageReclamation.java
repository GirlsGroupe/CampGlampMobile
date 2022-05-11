package com.mycompany.gui;


import com.mycompany.entities.Event;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.services.ServiceEvent;
import com.mycompany.services.ReclamationService;
import com.mycompany.services.servicesUtilisateurs;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;

public class ManageReclamation extends Form {


    Reclamation currentReclamation;

    TextField titreTF;
    TextField descriptionTF;
    Label titreLabel;
    Label descriptionLabel;
    PickerComponent dateTF;

    ArrayList<Event> listEvents;
    PickerComponent eventPC;
    Event selectedEvent = null;
    ArrayList<Utilisateurs> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateurs selectedUtilisateur = null;


    Button manageButton;

    Form previous;

    public ManageReclamation(Form previous) {
        super(ShowAllReclamation.currentReclamation == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentReclamation = ShowAllReclamation.currentReclamation;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] eventStrings;
        int eventIndex;
        eventPC = PickerComponent.createStrings("").label("Event");
        listEvents = ServiceEvent.getInstance().showEvents();
        eventStrings = new String[listEvents.size()];
        eventIndex = 0;
        for (Event event : listEvents) {
            eventStrings[eventIndex] = event.getNom();
            eventIndex++;
        }
        if (listEvents.size() > 0) {
            eventPC.getPicker().setStrings(eventStrings);
            eventPC.getPicker().addActionListener(l -> selectedEvent = listEvents.get(eventPC.getPicker().getSelectedStringIndex()));
        } else {
            eventPC.getPicker().setStrings("");
        }

        String[] utilisateurStrings;
        int utilisateurIndex;
        utilisateurPC = PickerComponent.createStrings("").label("Utilisateur");
        listUtilisateurs = servicesUtilisateurs.getInstance().afficherUtilisateurs();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateurs utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getNomUser();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
            utilisateurPC.getPicker().setStrings(utilisateurStrings);
            utilisateurPC.getPicker().addActionListener(l -> selectedUtilisateur = listUtilisateurs.get(utilisateurPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateurPC.getPicker().setStrings("");
        }


        titreLabel = new Label("Titre : ");
        titreLabel.setUIID("labelDefault");
        titreTF = new TextField();
        titreTF.setHint("Tapez le titre");


        descriptionLabel = new Label("Description : ");
        descriptionLabel.setUIID("labelDefault");
        descriptionTF = new TextField();
        descriptionTF.setHint("Tapez le description");


        dateTF = PickerComponent.createDate(null).label("Date");


        if (currentReclamation == null) {


            manageButton = new Button("Ajouter");
        } else {
            titreTF.setText(currentReclamation.getTitre());
            descriptionTF.setText(currentReclamation.getDescription());
            dateTF.getPicker().setDate(currentReclamation.getDate());


            eventPC.getPicker().setSelectedString(currentReclamation.getEvent().getNom());
            selectedEvent = currentReclamation.getEvent();
            utilisateurPC.getPicker().setSelectedString(currentReclamation.getUtilisateur().getNomUser());
            selectedUtilisateur = currentReclamation.getUtilisateur();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                titreLabel, titreTF,
                descriptionLabel, descriptionTF,
                dateTF,


                eventPC, utilisateurPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentReclamation == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReclamationService.getInstance().add(
                            new Reclamation(


                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    selectedEvent,
                                    selectedUtilisateur
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Reclamation ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = ReclamationService.getInstance().edit(
                            new Reclamation(
                                    currentReclamation.getId(),


                                    titreTF.getText(),
                                    descriptionTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    selectedEvent,
                                    selectedUtilisateur

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Reclamation modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        }
    }

    private void showBackAndRefresh() {
        ((ShowAllReclamation) previous).refresh();
        previous.showBack();
    }

    private boolean controleDeSaisie() {


        if (titreTF.getText().equals("")) {
            Dialog.show("Avertissement", "Titre vide", new Command("Ok"));
            return false;
        }


        if (descriptionTF.getText().equals("")) {
            Dialog.show("Avertissement", "Description vide", new Command("Ok"));
            return false;
        }


        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }


        if (selectedEvent == null) {
            Dialog.show("Avertissement", "Veuillez choisir un event", new Command("Ok"));
            return false;
        }

        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }


        return true;
    }
}