/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import static com.mycompany.gui.ForgetPwd.emailUser;
import com.mycompany.services.servicesUtilisateurs;

/**
 *
 * @author MossMoss
 */
public class ResetPwd  extends BaseForm {
  public ResetPwd(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("ForgetPwd");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("ForgetPwd");
        
  
        
        TextField mdp = new TextField("", "Saisir mdp", 20, TextField.PASSWORD);
        TextField Cmdp = new TextField("", "Saisir votre mdp", 20, TextField.PASSWORD);

       Button Valider = new Button("Changer");
       Label haveanaccount=new Label("retour de connecter?");
       Button SignIn = new Button("Renouveller votre mot de passe");
       SignIn.addActionListener(e->previous.showBack());
       SignIn.setUIID("CenterLink");
          Container content = BoxLayout.encloseY(
                new FloatingHint(mdp),
                createLineSeparator(),
                    new FloatingHint(Cmdp),
                createLineSeparator(),
                Valider,
                FlowLayout.encloseCenter(haveanaccount), 
                SignIn
        );
          content.setScrollableY(true);
          add(BorderLayout.SOUTH, content);
                  Valider.requestFocus();
        Valider.addActionListener(e -> {
          servicesUtilisateurs.getInstance().ChangerMdp(emailUser,mdp,Cmdp,res);
            refreshTheme();
        });
}
}