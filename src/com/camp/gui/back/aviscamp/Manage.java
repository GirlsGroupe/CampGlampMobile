package com.camp.gui.back.aviscamp;


import com.camp.entities.Aviscamp;
import com.camp.entities.Event;
import com.camp.entities.Reclamation;
import com.camp.entities.Utilisateur;
import com.camp.services.AviscampService;
import com.camp.services.EventService;
import com.camp.services.ReclamationService;
import com.camp.services.UtilisateurService;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;

public class Manage extends Form {


    Aviscamp currentAviscamp;

    TextField descriptionTF;
    TextField rateTF;
    Label descriptionLabel;
    Label rateLabel;
    PickerComponent dateTF;
    CheckBox likeCB;
    ArrayList<Utilisateur> listUtilisateurs;
    PickerComponent utilisateurPC;
    Utilisateur selectedUtilisateur = null;
    ArrayList<Event> listEvents;
    PickerComponent eventPC;
    Event selectedEvent = null;
    ArrayList<Reclamation> listReclamations;
    PickerComponent reclamationPC;
    Reclamation selectedReclamation = null;


    Button manageButton;

    Form previous;

    public Manage(Form previous) {
        super(ShowAll.currentAviscamp == null ? "Ajouter" : "Modifier", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        currentAviscamp = ShowAll.currentAviscamp;

        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        String[] utilisateurStrings;
        int utilisateurIndex;
        utilisateurPC = PickerComponent.createStrings("").label("Utilisateur");
        listUtilisateurs = UtilisateurService.getInstance().getAll();
        utilisateurStrings = new String[listUtilisateurs.size()];
        utilisateurIndex = 0;
        for (Utilisateur utilisateur : listUtilisateurs) {
            utilisateurStrings[utilisateurIndex] = utilisateur.getNom();
            utilisateurIndex++;
        }
        if (listUtilisateurs.size() > 0) {
            utilisateurPC.getPicker().setStrings(utilisateurStrings);
            utilisateurPC.getPicker().addActionListener(l -> selectedUtilisateur = listUtilisateurs.get(utilisateurPC.getPicker().getSelectedStringIndex()));
        } else {
            utilisateurPC.getPicker().setStrings("");
        }

        String[] eventStrings;
        int eventIndex;
        eventPC = PickerComponent.createStrings("").label("Event");
        listEvents = EventService.getInstance().getAll();
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


        dateTF = PickerComponent.createDate(null).label("Date");


        likeCB = new CheckBox("Like : ");


        rateLabel = new Label("Rate : ");
        rateLabel.setUIID("labelDefault");
        rateTF = new TextField();
        rateTF.setHint("Tapez le rate");


        if (currentAviscamp == null) {


            manageButton = new Button("Ajouter");
        } else {
            descriptionTF.setText(currentAviscamp.getDescription());
            dateTF.getPicker().setDate(currentAviscamp.getDate());
            if (currentAviscamp.getLike() == 1) {
                likeCB.setSelected(true);
            }
            rateTF.setText(String.valueOf(currentAviscamp.getRate()));


            utilisateurPC.getPicker().setSelectedString(currentAviscamp.getUtilisateur().getNom());
            selectedUtilisateur = currentAviscamp.getUtilisateur();
            eventPC.getPicker().setSelectedString(currentAviscamp.getEvent().getNom());
            selectedEvent = currentAviscamp.getEvent();
            reclamationPC.getPicker().setSelectedString(currentAviscamp.getReclamation().getTitre());
            selectedReclamation = currentAviscamp.getReclamation();


            manageButton = new Button("Modifier");
        }
        manageButton.setUIID("buttonWhiteCenter");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(

                descriptionLabel, descriptionTF,
                dateTF,
                likeCB,
                rateLabel, rateTF,


                utilisateurPC, eventPC, reclamationPC,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        if (currentAviscamp == null) {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = AviscampService.getInstance().add(
                            new Aviscamp(


                                    descriptionTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    likeCB.isSelected() ? 1 : 0,
                                    Float.parseFloat(rateTF.getText()),
                                    selectedUtilisateur,
                                    selectedEvent,
                                    selectedReclamation
                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Aviscamp ajouté avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur d'ajout de aviscamp. Code d'erreur : " + responseCode, new Command("Ok"));
                    }
                }
            });
        } else {
            manageButton.addActionListener(action -> {
                if (controleDeSaisie()) {
                    int responseCode = AviscampService.getInstance().edit(
                            new Aviscamp(
                                    currentAviscamp.getId(),


                                    descriptionTF.getText(),
                                    dateTF.getPicker().getDate(),
                                    likeCB.isSelected() ? 1 : 0,
                                    Float.parseFloat(rateTF.getText()),
                                    selectedUtilisateur,
                                    selectedEvent,
                                    selectedReclamation

                            )
                    );
                    if (responseCode == 200) {
                        Dialog.show("Succés", "Aviscamp modifié avec succes", new Command("Ok"));
                        showBackAndRefresh();
                    } else {
                        Dialog.show("Erreur", "Erreur de modification de aviscamp. Code d'erreur : " + responseCode, new Command("Ok"));
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


        if (dateTF.getPicker().getDate() == null) {
            Dialog.show("Avertissement", "Veuillez saisir la date", new Command("Ok"));
            return false;
        }


        if (rateTF.getText().equals("")) {
            Dialog.show("Avertissement", "Rate vide", new Command("Ok"));
            return false;
        }
        try {
            Float.parseFloat(rateTF.getText());
        } catch (NumberFormatException e) {
            Dialog.show("Avertissement", rateTF.getText() + " n'est pas un nombre valide (rate)", new Command("Ok"));
            return false;
        }


        if (selectedUtilisateur == null) {
            Dialog.show("Avertissement", "Veuillez choisir un utilisateur", new Command("Ok"));
            return false;
        }

        if (selectedEvent == null) {
            Dialog.show("Avertissement", "Veuillez choisir un event", new Command("Ok"));
            return false;
        }

        if (selectedReclamation == null) {
            Dialog.show("Avertissement", "Veuillez choisir un reclamation", new Command("Ok"));
            return false;
        }


        return true;
    }
}