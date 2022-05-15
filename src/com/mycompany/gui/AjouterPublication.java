/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Util;
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
import com.codename1.ui.Stroke;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Publications;
import com.mycompany.services.ServicePublications;
import java.io.InputStream;
import java.util.Random;

/**
 *
 * @author MossMoss
 */
public class AjouterPublication extends BaseForm{
     Form current;
         private ServicePublications SP;
    String GlobalPath = "";
    String GlobalExtension = "";
     
         public AjouterPublication(Resources res) {
         super("", BoxLayout.y());

        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Utilisateur");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {

        });

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
        RadioButton mesListes = RadioButton.createToggle("Utilisateur", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Ajouter", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");

        mesListes.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new ListeUtilisateurs(res).show();
            refreshTheme();
        });
        partage.addActionListener((e) -> {
            InfiniteProgress ip = new InfiniteProgress();
            final Dialog ipDlg = ip.showInifiniteBlocking();

            new AjouterUtilisateur(res).show();
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
        
                Container f = new Container(BoxLayout.y());

        
        
        TextField description = new TextField("", "écrivez votre description", 50, TextField.ANY);
        description.getAllStyles().setFgColor(0x5f5f5f);
        description.getAllStyles().setPaddingBottom(10);
        description.getAllStyles().setBorder(RoundRectBorder.create().strokeColor(0).
                strokeOpacity(50).
                stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        description.getAllStyles().setMarginLeft(1);
        description.getAllStyles().setMarginRight(1);
        description.getAllStyles().setMarginTop(1);
        
          TextField tup = new TextField("", "Path");

        tup.getAllStyles().setFgColor(0x5f5f5f);
        Button upload = new Button("importer");
        Label limport = new Label("aucun fichier est  selectioné");
        upload.addPointerPressedListener((ei) -> {
            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".pdf,application/pdf,.gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", e2 -> {
                    String file = (String) e2.getSource();
                    System.out.println("file name :" + file);
                    if (file == null) {
                        System.out.println("No file was selected");
                    } else {
                        String extension = null;
                        if (file.lastIndexOf(".") > 0) {
                            extension = file.substring(file.lastIndexOf(".") + 1);
                        }
                        if ("txt".equals(extension)) {
                            FileSystemStorage fs = FileSystemStorage.getInstance();
                            try {
                                InputStream fis = fs.openInputStream(file);
                                System.out.println(Util.readToString(fis));
                            } catch (Exception ex) {
                                Log.e(ex);
                            }
                        } else {
                            //moveFile(file,)
                            String path = file.substring(7);
                            System.out.println("Selected file :" + file.substring(44) + "\n" + "path :" + path);
                            limport.setText("file imported");
                            limport.getAllStyles().setFgColor(0x69E781);
                            tup.setText(path);
                            GlobalPath = path;
                            GlobalExtension = file.substring(file.lastIndexOf(".") + 1);
                        }
                    }
                });
            }
        });
        Button submitPost = new Button("  Poster ");

        submitPost.addPointerPressedListener((e) -> {

            Random rand = new Random();
            int upperbound = 7483647;
            int int_random = rand.nextInt(upperbound);
            String Fullname = "MobileGenerated_" + int_random + "." + GlobalExtension;
            System.out.println(Fullname);


            boolean moving = moveFile(GlobalPath, "C:/xampp/htdocs/CampGlampWeb/public/uploads/PublicationImage/" + Fullname);

            Publications p = new Publications();
            p.setSourcePub(Fullname);
            p.setIdU(SessionManager.getId());
            
            p.setDescriptionPub(description.getText());

            if (ServicePublications.getInstance().addPublication(p)) {

                ToastBar.Status status = ToastBar.getInstance().createStatus();
                status.setMessage("Publication ajoutée");
                status.show();
                   new ListePublications(res).show();
           
            };
        }
            );
                        Container csub = new Container(BoxLayout.x());
 csub.addAll( submitPost);

        Container cimport = new Container(new BorderLayout());

        cimport.add(BorderLayout.WEST, limport);
        cimport.add(BorderLayout.EAST, upload);

        f.addAll(description, cimport);

        add(f);
        csub.getAllStyles().setMarginTop(750);
        add(csub);
        
    
}
         
         
         
          private void addStringValue(String s, Component c) {
        add(BorderLayout.west(new Label(s, "PaddedLabel"))
                .add(BorderLayout.CENTER, c));
        add(createLineSeparator(0xeeeeee));

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
    
    
    public boolean moveFile(String sourcePath, String targetPath) {
        System.out.println("inside movefile");
        File fileToMove = new File(sourcePath);
        return fileToMove.renameTo(new File(targetPath));
    }
}

