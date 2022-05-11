/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.*;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.gui.ListeUtilisateurs;
import com.mycompany.gui.ProfileForm;
import com.mycompany.gui.ResetPwd;
import com.mycompany.gui.SessionManager;
import com.mycompany.gui.SignInForm;
import com.mycompany.utils.CodeGenerator;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author MossMoss
 */
public class servicesUtilisateurs {

    public ArrayList<Utilisateurs> Users;
    private boolean resultat;
    public static servicesUtilisateurs instance = null;

    private ConnectionRequest req;

    public static servicesUtilisateurs getInstance() {
        if (instance == null) {
            instance = new servicesUtilisateurs();
        }
        return instance;
    }

    public servicesUtilisateurs() {
        req = new ConnectionRequest();

    }

    //affichage 
    public void ajoutUtilisateur(Utilisateurs utilisateur) {
        String url = Statics.Base_URL + "/mobile/addUsersM?cinuser=" + utilisateur.getCinUser() + "&nomuser=" + utilisateur.getNomUser() + "&prenomuser=" + utilisateur.getPrenomUser() + "&teluser=" + utilisateur.getTelUser() + "&adresseuser=" + utilisateur.getAdresseUser() + "&email=" + utilisateur.getEmail() + "&motdepasse=" + utilisateur.getMotdepasse() + "&role=" + Arrays.toString(utilisateur.getRole()) + "&image=" + utilisateur.getImage();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    //Affichage

    /*public ArrayList<Utilisateurs> afficherUtilisateurs() {
        ArrayList<Utilisateurs> rslt = new ArrayList<>();
        String url = Statics.Base_URL + "/listUsersM";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("ici");

                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapUtilisateurs = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapUtilisateurs.get("root");
                    for (Map<String, Object> obj : listOfMaps) {
                        Utilisateurs user = new Utilisateurs();

                        float iduser = Float.parseFloat((obj.get("iduser").toString()));
                        String cinuser = obj.get("cinuser").toString();
                        String nomuser = obj.get("nomuser").toString();
                        String prenomuser = obj.get("prenomuser").toString();
                        String teluser = obj.get("teluser").toString();
                        String adresseuser = obj.get("adresseuser").toString();
                        String email = obj.get("email").toString();
                        user.setIdUser((int) iduser);
                        user.setCinUser(cinuser);
                        user.setNomUser(nomuser);
                        user.setPrenomUser(prenomuser);
                        user.setTelUser(teluser);
                        user.setAdresseUser(adresseuser);
                        user.setEmail(email);
                        rslt.add(user);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return rslt;

    }*/
    public ArrayList<Utilisateurs> parse(String jsonTxt) {
        try {
            Users = new ArrayList<>();

            JSONParser parser = new JSONParser();
            /*System.out.println("-----------json----------------------");
            System.out.println(jsonTxt.toCharArray());
             */
            Map<String, Object> UsersJSON;
            UsersJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
            List<Map<String, Object>> listOfMaps;
            listOfMaps = (List<Map<String, Object>>) UsersJSON.get("root");
            for (Map<String, Object> obj : listOfMaps) {
                Utilisateurs user = new Utilisateurs();
                float iduser = Float.parseFloat((obj.get("iduser").toString()));
                String cinuser = obj.get("cinuser").toString();
                String nomuser = obj.get("nomuser").toString();
                String prenomuser = obj.get("prenomuser").toString();
                String teluser = obj.get("teluser").toString();
                String adresseuser = obj.get("adresseuser").toString();
                String email = obj.get("email").toString();
                String mdp = obj.get("motdepasse").toString();
                String image = obj.get("image").toString();
                user.setIdUser((int) iduser);
                user.setCinUser(cinuser);
                user.setNomUser(nomuser);
                user.setPrenomUser(prenomuser);
                user.setTelUser(teluser);
                user.setAdresseUser(adresseuser);
                user.setEmail(email);
                user.setMotdepasse(mdp);
                user.setImage(image);
                Users.add(user);

            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
        return Users;

    }

    public ArrayList<Utilisateurs> afficherUtilisateurs() {
        req = new ConnectionRequest();
        String url = Statics.Base_URL + "/mobile/listUsersM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Users = parse(new String(req.getResponseData()));
                System.out.println(Users);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Users;

    }

    public boolean updateUser(Utilisateurs user, int id) {
        System.out.println("here");
        String url = Statics.Base_URL + "/mobile/updateUsersM/" + id
                + "?cinuser=" + user.getCinUser()
                + "&nomuser=" + user.getNomUser()
                + "&prenomuser=" + user.getPrenomUser()
                + "&teluser=" + user.getTelUser()
                + "&adresseuser=" + user.getAdresseUser()
                + "&email=" + user.getEmail()
                + "&motdepasse=" + user.getMotdepasse()
                + "&image=" + user.getImage();

        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultat = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }

    public boolean deleteUser(int id) {
        String url = Statics.Base_URL + "/mobile/deleteUsersM/" + id;
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultat = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;

    }

    public void signup(TextField cinuser, TextField nomuser, TextField prenomuser, TextField teluser, TextField adresseuser, TextField email, TextField motdepasse, TextField Confirmermotdepasse, ComboBox vectorRoles, Resources res) {
        //Role
        String url = Statics.Base_URL + "/mobile/SignUpMobile"
                + "?cinuser=" + cinuser.getText()
                + "&nomuser=" + nomuser.getText()
                + "&prenomuser=" + prenomuser.getText()
                + "&teluser=" + teluser.getText()
                + "&adresseuser=" + adresseuser.getText()
                + "&email=" + email.getText()
                + "&motdepasse=" + motdepasse.getText()
                + "&roles=" + vectorRoles.getSelectedItem().toString();
        req.setUrl(url);

        //Control saisi
        if (cinuser.getText().equals("") || nomuser.getText().equals("") || prenomuser.getText().equals("") || teluser.getText().equals("") || adresseuser.getText().equals("") || email.getText().equals("") || motdepasse.getText().equals("") || vectorRoles.getSelectedItem().toString().equals("")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        } else if (!Confirmermotdepasse.getText().equals(motdepasse.getText())) {
            Dialog.show("Erreur", "Les mots de passe ne sont pas identiques", "OK", null);

        } //hethi wa9t tsir execution ta3 url 
        else {
            req.addResponseListener((e) -> {

                //njib data ly7atithom fi form 
                byte[] data = (byte[]) e.getMetaData();//lazm awl 7aja n7athrhom ke meta data ya3ni na5o id ta3 kol textField 
                String responseData = new String(data);//ba3dika na5o content 

                System.out.println("data ===>" + responseData);

                JSONParser j = new JSONParser();
                String json = new String(req.getResponseData()) + "";

                try {

                    if (json.equals("Failed")) {
                        Dialog.show("Echec", "Email déjà existe veuillez saisir de nouveau!!!", "OK", null);
                    } else {
                        Dialog.show("Succées", "Compte créer", "OK", null);
                        new SignInForm(res).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            );

            //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
            NetworkManager.getInstance().addToQueueAndWait(req);

        }
    }

    public void signin(TextField email, TextField motdepasse, Resources rs) {

        String url = Statics.Base_URL + "/mobile/SignIn?email=" + email.getText().toString() + "&motdepasse=" + motdepasse.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        if (email.getText().equals("") || motdepasse.getText().equals("") || email.getText().equals("") && motdepasse.getText().equals("")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        } else {
            req.addResponseListener((NetworkEvent e) -> {

                JSONParser j = new JSONParser();

                String json = new String(req.getResponseData()) + "";

                try {

                    if (json.equals("failed")) {
                        Dialog.show("Echec d'authentification", "Email ou mot de passe éronné", "OK", null);
                    } else {
                        System.out.println("data ==" + json);

                        Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
                        //Session 
                        float id = Float.parseFloat(user.get("iduser").toString());
                        SessionManager.setId((int) id);//jibt id ta3 user ly3ml login w sajltha fi session ta3i
                        SessionManager.setNom(user.get("nomuser").toString());
                        SessionManager.setPrenom(user.get("prenomuser").toString());
                        SessionManager.setEmail(user.get("email").toString());
                        //System.out.println(user.get("roles").toString());
                          String roleUser = user.get("roles").toString();
                        System.out.println(roleUser);
                          SessionManager.setRole(roleUser);
                  
                        //photo 
                        if (user.get("image") != null) {
                            SessionManager.setPhoto(user.get("image").toString());
                        }

                        if (user.size() > 0) // l9a user
                        {
                            new ProfileForm(rs).show();
                        }
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });

            //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
            NetworkManager.getInstance().addToQueueAndWait(req);
        }

    }

    public void ValiderCode(TextField code, Resources res) {
        if (code.getText().isEmpty() == false && code.getText().equals(CodeGenerator.getCode())) {
            Dialog.show("Succées", "Code valide", "OK", null);
            new ResetPwd(res).show();

        } else {
            Dialog.show("Echéc", "Code invalide réssayer de nouveau", "OK", null);

        }

    }

    public void ChangerMdp(String email, TextField motdepasse, TextField Confirmermotdepasse, Resources rs) {

        String url = Statics.Base_URL + "/mobile/ChangermdpMobile/" + email + "?motdepasse=" + motdepasse.getText().toString();
        req = new ConnectionRequest(url, false); //false ya3ni url mazlt matba3thtich lel server
        req.setUrl(url);
        if (motdepasse.getText().equals("") || Confirmermotdepasse.getText().equals("") || Confirmermotdepasse.getText().equals("") && motdepasse.getText().equals("")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        } else if (motdepasse.getText().equals(Confirmermotdepasse.getText()) == false) {
            Dialog.show("Erreur", "Les mots de passe ne sont pas identiques ! Veuillez remplir les champs", "OK", null);
        } else {
            req.addResponseListener((e) -> {

                JSONParser j = new JSONParser();

                String json = new String(req.getResponseData()) + "";

                if (json.equals("Done")) {
                    Dialog.show("Succées", "Mot de passe mis à jour", "OK", null);
                    new SignInForm(rs).show();

                }
            });

            //ba3d execution ta3 requete ely heya url nestanaw response ta3 server.
            NetworkManager.getInstance().addToQueueAndWait(req);

        }
    }

}
