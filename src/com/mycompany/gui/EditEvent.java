/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Event;
import com.mycompany.services.ServiceEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ACER
 */
public class EditEvent extends BaseForm {

    Form current;

    protected String saveFileToDevice(String hi, String ext) throws IOException {
        URI uri;
        try {
            uri = new URI(hi);
            String path = uri.getPath();
            int index = hi.lastIndexOf("/");
            hi = hi.substring(index + 1);
            return hi;
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        return "error";
    }

    public EditEvent(Resources res, Event r) {
        super("Newsfeed", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Evenement");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        TextField name = new TextField(r.getNom(), "Nom");
        name.setUIID("NewsTopLine");
        addStringValue("Nom", name);

        TextField description = new TextField(r.getDescription(), "Description");
        description.setUIID("NewsTopLine");
        addStringValue("Description", description);

        TextField lieu = new TextField(r.getLieu(), "Lieu");
        lieu.setUIID("NewsTopLine");
        addStringValue("Lieu", lieu);

        ComboBox type = new ComboBox();
        type.setUIID("TextFieldBlack");
        type.addItem("RANDONNEE");
        type.addItem("CAMPING");
        type.addItem("GLAMPING");
        addStringValue("Type", type);

        TextField prix = new TextField(String.valueOf(r.getPrix()), "Prix");
        prix.setUIID("NewsTopLine");
        addStringValue("Prix", prix);

        Picker date = new Picker();
        date.setUIID("TextFieldBlack");
        date.setType(Display.PICKER_TYPE_DATE);
        addStringValue("Date", date);

        ComboBox status = new ComboBox();
        status.setUIID("TextFieldBlack");
        status.addItem("PROGRAMME");
        status.addItem("FINI");
        addStringValue("Status", status);

        Button img1 = new Button("Ajouer une image");
        CheckBox multiSelect = new CheckBox("multi");
        addStringValue("", img1);

        Button btnModifier = new Button("Modifier");
        btnModifier.setUIID("Button");

        img1.addActionListener((ActionEvent e) -> {
            if (FileChooser.isAvailable()) {
                FileChooser.setOpenFilesInPlace(true);
                FileChooser.showOpenDialog(multiSelect.isSelected(), ".jpg, .jpeg, .png", (ActionEvent e2) -> {
                    if (e2 == null || e2.getSource() == null) {
                        add("no file selected");
                        revalidate();
                        return;
                    }
                    if (multiSelect.isSelected()) {
                        String[] paths = (String[]) e2.getSource();
                        for (String path : paths) {
                            System.out.println(path);
                            CN.execute(path);
                        }
                        return;
                    }
                    String file = (String) e2.getSource();
                    System.out.println("here : " + file);
                    if (file == null) {
                        add("no file selected");
                        revalidate();
                    } else {
                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            try {
                                StringBuilder hi = new StringBuilder(file);
                                if (file.startsWith("file://")) {
                                    hi.delete(0, 7);
                                }
                                int lastIndexPeriod = hi.toString().lastIndexOf(".");
                                Log.p(hi.toString());
                                String ext = hi.toString().substring(lastIndexPeriod);
                                String hmore = hi.toString().substring(0, lastIndexPeriod - 1);

                                String namePic = saveFileToDevice(file, ext);
                                System.err.println(namePic);
                                btnModifier.addActionListener((ev) -> {

                                    try {
                                        if (name.getText().isEmpty() || description.getText().isEmpty()) {
                                            Dialog.show("Please fill all the fields", "", "Cancel", "OK");
                                        } else {
                                            InfiniteProgress ip = new InfiniteProgress();
                                            final Dialog iDialog = ip.showInfiniteBlocking();
                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            r.setNom(name.getText());
                                            r.setDescription(description.getText());
                                             r.setLieu(lieu.getText());
                                            r.setType(type.getSelectedItem().toString());
                                            r.setPrix(Integer.parseInt(prix.getText()));
                                            r.setStatus(type.getSelectedItem().toString());
                                            r.setDate(format.format(date.getDate()));
                                            r.setImage(namePic);
                                            ServiceEvent.getInstance().editEvent(r.getId(), r);

                                            iDialog.dispose();
                                            new ShowEvent(res).show();
                                            refreshTheme();
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                });
                            } catch (IOException ex) {
                            }
                            revalidate();
                        }
                    }
                });
            }
        });
        //Event onclick btnModifer

        Button btnAnnuler = new Button("Cancel");
        btnAnnuler.addActionListener(e -> {
            new ShowEvent(res).show();
        });

        Label l2 = new Label("");

        Label l3 = new Label("");

        Label l4 = new Label("");

        Label l5 = new Label("");

        Label l1 = new Label();

        Container content = BoxLayout.encloseY(
                l1, l2,
                createLineSeparator(),
                createLineSeparator(),
                btnModifier,
                btnAnnuler
        );

        add(content);
        show();

    }

    private void addStringValue(String s, Component v) {

        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
