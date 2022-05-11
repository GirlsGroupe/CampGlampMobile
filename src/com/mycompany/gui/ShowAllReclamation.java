package com.mycompany.gui;

import com.mycompany.entities.Reclamation;
import com.mycompany.services.ReclamationService;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAllReclamation extends Form {

    public static Reclamation currentReclamation = null;
    Form previous;
    Button addBtn;
    Label titreLabel, descriptionLabel, dateLabel, eventLabel, utilisateurLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAllReclamation(Form previous) {
        super("Reclamations", new BoxLayout(BoxLayout.Y_AXIS));
        this.previous = previous;

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {
        addBtn = new Button("Ajouter");
        addBtn.setUIID("buttonWhiteCenter");
        this.add(addBtn);


        ArrayList<Reclamation> listReclamations = ReclamationService.getInstance().getAll();


        if (listReclamations.size() > 0) {
            for (Reclamation reclamation : listReclamations) {
                Component model = makeReclamationModel(reclamation);
                this.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentReclamation = null;
            new ManageReclamation(this).show();
        });

    }

    private Container makeModelWithoutButtons(Reclamation reclamation) {
        Container reclamationModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        reclamationModel.setUIID("containerRounded");


        titreLabel = new Label("Titre : " + reclamation.getTitre());
        titreLabel.setUIID("labelDefault");

        descriptionLabel = new Label("Description : " + reclamation.getDescription());
        descriptionLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(reclamation.getDate()));
        dateLabel.setUIID("labelDefault");

       

        utilisateurLabel = new Label("Utilisateur : " + reclamation.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + reclamation.getUtilisateur().getNomUser());
        utilisateurLabel.setUIID("labelDefault");


        reclamationModel.addAll(

                titreLabel, descriptionLabel, dateLabel, eventLabel, utilisateurLabel
        );

        return reclamationModel;
    }

    private Component makeReclamationModel(Reclamation reclamation) {

        Container reclamationModel = makeModelWithoutButtons(reclamation);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentReclamation = reclamation;
            new ManageReclamation(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce reclamation ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = ReclamationService.getInstance().delete(reclamation.getId());

                if (responseCode == 200) {
                    currentReclamation = null;
                    dlg.dispose();
                    reclamationModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du reclamation. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        reclamationModel.add(btnsContainer);

        return reclamationModel;
    }

}