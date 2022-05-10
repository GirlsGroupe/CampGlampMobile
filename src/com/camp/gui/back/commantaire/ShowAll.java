package com.camp.gui.back.commantaire;

import com.camp.entities.Commantaire;
import com.camp.services.CommantaireService;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;

public class ShowAll extends Form {

    public static Commantaire currentCommantaire = null;
    Form previous;
    Button addBtn;

    TextField searchTF;
    ArrayList<Component> componentModels;
    Label descriptionLabel, reclamationLabel;
    Button editBtn, deleteBtn;
    Container btnsContainer;

    public ShowAll(Form previous) {
        super("Commantaires", new BoxLayout(BoxLayout.Y_AXIS));
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


        ArrayList<Commantaire> listCommantaires = CommantaireService.getInstance().getAll();
        componentModels = new ArrayList<>();

        searchTF = new TextField("", "Chercher commantaire par Nom");
        searchTF.addDataChangedListener((d, t) -> {
            if (componentModels.size() > 0) {
                for (Component componentModel : componentModels) {
                    this.removeComponent(componentModel);
                }
            }
            componentModels = new ArrayList<>();
            for (Commantaire commantaire : listCommantaires) {
                if (commantaire.getDescription().toLowerCase().startsWith(searchTF.getText().toLowerCase())) {
                    Component model = makeCommantaireModel(commantaire);
                    this.add(model);
                    componentModels.add(model);
                }
            }
            this.revalidate();
        });
        this.add(searchTF);


        if (listCommantaires.size() > 0) {
            for (Commantaire commantaire : listCommantaires) {
                Component model = makeCommantaireModel(commantaire);
                this.add(model);
                componentModels.add(model);
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            currentCommantaire = null;
            new Manage(this).show();
        });

    }

    private Container makeModelWithoutButtons(Commantaire commantaire) {
        Container commantaireModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commantaireModel.setUIID("containerRounded");


        descriptionLabel = new Label("Description : " + commantaire.getDescription());
        descriptionLabel.setUIID("labelDefault");

        reclamationLabel = new Label("Reclamation : " + commantaire.getReclamation());
        reclamationLabel.setUIID("labelDefault");

        reclamationLabel = new Label("Reclamation : " + commantaire.getReclamation().getTitre());
        reclamationLabel.setUIID("labelDefault");


        commantaireModel.addAll(

                descriptionLabel, reclamationLabel
        );

        return commantaireModel;
    }

    private Component makeCommantaireModel(Commantaire commantaire) {

        Container commantaireModel = makeModelWithoutButtons(commantaire);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        editBtn = new Button("Modifier");
        editBtn.setUIID("buttonWhiteCenter");
        editBtn.addActionListener(action -> {
            currentCommantaire = commantaire;
            new Manage(this).show();
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonWhiteCenter");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commantaire ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = CommantaireService.getInstance().delete(commantaire.getId());

                if (responseCode == 200) {
                    currentCommantaire = null;
                    dlg.dispose();
                    commantaireModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commantaire. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        btnsContainer.add(BorderLayout.WEST, editBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);


        commantaireModel.add(btnsContainer);

        return commantaireModel;
    }

}