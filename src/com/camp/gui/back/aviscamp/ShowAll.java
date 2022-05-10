package com.camp.gui.back.aviscamp;

import com.camp.entities.Aviscamp;
import com.camp.services.AviscampService;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowAll extends Form {

    public static Aviscamp currentAviscamp = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label descriptionLabel, dateLabel, likeLabel, rateLabel, utilisateurLabel, eventLabel, reclamationLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Aviscamps", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Aviscamp> listAviscamps = AviscampService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher aviscamp par Description");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Aviscamp aviscamp : listAviscamps) {
                if (aviscamp.getDescription().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeAviscampModel(aviscamp);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listAviscamps.size() > 0) {
            for (Aviscamp aviscamp : listAviscamps) {
                Component model = makeAviscampModel(aviscamp);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentAviscamp = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Aviscamp aviscamp) {
        Container aviscampModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        aviscampModel.setUIID("containerRounded");


        descriptionLabel = new Label("Description : " + aviscamp.getDescription());
        descriptionLabel.setUIID("labelDefault");

        dateLabel = new Label("Date : " + new SimpleDateFormat("dd-MM-yyyy").format(aviscamp.getDate()));
        dateLabel.setUIID("labelDefault");

        likeLabel = new Label("Like : " + (aviscamp.getLike() == 1 ? "True" : "False"));
        likeLabel.setUIID("labelDefault");

        rateLabel = new Label("Rate : " + aviscamp.getRate());
        rateLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + aviscamp.getUtilisateur());
        utilisateurLabel.setUIID("labelDefault");

        eventLabel = new Label("Event : " + aviscamp.getEvent());
        eventLabel.setUIID("labelDefault");

        reclamationLabel = new Label("Reclamation : " + aviscamp.getReclamation());
        reclamationLabel.setUIID("labelDefault");

        utilisateurLabel = new Label("Utilisateur : " + aviscamp.getUtilisateur().getNom());
        utilisateurLabel.setUIID("labelDefault");

        eventLabel = new Label("Event : " + aviscamp.getEvent().getNom());
        eventLabel.setUIID("labelDefault");

        reclamationLabel = new Label("Reclamation : " + aviscamp.getReclamation().getTitre());
        reclamationLabel.setUIID("labelDefault");


        aviscampModel.addAll(

                descriptionLabel, dateLabel, likeLabel, rateLabel, utilisateurLabel, eventLabel, reclamationLabel
        );

        return aviscampModel;
    }

    private Component makeAviscampModel(Aviscamp aviscamp) {

        Container aviscampModel = makeModelWithoutButtons(aviscamp);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentAviscamp = aviscamp;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce aviscamp ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = AviscampService.getInstance().delete(aviscamp.getId());

                if (responseCode == 200) {
                    currentAviscamp = null;
                    dlg.dispose();
                    aviscampModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du aviscamp. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        aviscampModel.add(btnsContainer);

        return aviscampModel;
    }

}