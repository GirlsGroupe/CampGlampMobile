/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.servicesUtilisateurs;
import java.util.Vector;

/**
 * Signup UI
 *
 * @author Shai Almog
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");

        TextField Cin = new TextField("", "Cin", 20, TextField.ANY);
        TextField Nom = new TextField("", "Nom", 20, TextField.ANY);
        TextField Prenom = new TextField("", "Prenom", 20, TextField.ANY);
        TextField Telephone = new TextField("", "Telephone", 20, TextField.ANY);
        TextField Adresse = new TextField("", "Adresse", 20, TextField.ANY);
        TextField email = new TextField("", "Email", 20, TextField.EMAILADDR);
        TextField Motdepasse = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        TextField confirmPassword = new TextField("", "confirmer", 20, TextField.PASSWORD);
        Vector<String> vectorRole;
        vectorRole = new Vector();
        vectorRole.add("camper");
        vectorRole.add("guide");
        

        ComboBox<String> roles = new ComboBox<>(vectorRole);
       
        Cin.setSingleLineTextArea(false);
        Nom.setSingleLineTextArea(false);
        Prenom.setSingleLineTextArea(false);
        Telephone.setSingleLineTextArea(false);
        Adresse.setSingleLineTextArea(false);
        email.setSingleLineTextArea(false);
        Motdepasse.setSingleLineTextArea(false);
        confirmPassword.setSingleLineTextArea(false);

   

        Button next = new Button("Confirmer");
        Button signIn = new Button("Se connecter");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Vous-avez déjà un compte?");

        Container content = BoxLayout.encloseY(
                new Label("Créer un compte", "LogoLabel"),
                new FloatingHint(Cin),
                createLineSeparator(),
                new FloatingHint(Nom),
                createLineSeparator(),
                new FloatingHint(Prenom),
                createLineSeparator(),
                 new FloatingHint(Telephone),
                createLineSeparator(),
                new FloatingHint(Adresse),
                createLineSeparator(),
                new FloatingHint(email),
                createLineSeparator(),
                 new FloatingHint(Motdepasse),
                createLineSeparator(),
                new FloatingHint(confirmPassword),
                createLineSeparator(),
                roles
        );
        content.setScrollableY(true);
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));
        next.requestFocus();
        next.addActionListener((e) -> {
                
                servicesUtilisateurs.getInstance().signup(Cin, Nom, Prenom, Telephone, Adresse, email, Motdepasse, confirmPassword, roles, res);
                
        
        
        });

}
}
