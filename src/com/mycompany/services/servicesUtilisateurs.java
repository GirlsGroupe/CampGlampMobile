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
import com.codename1.ui.events.*;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        String url = Statics.Base_URL + "/addUsersM?cinuser=" + utilisateur.getCinUser() + "&nomuser=" + utilisateur.getNomUser() + "&prenomuser=" + utilisateur.getPrenomUser() + "&teluser=" + utilisateur.getTelUser() + "&adresseuser=" + utilisateur.getAdresseUser() + "&email=" + utilisateur.getEmail() + "&motdepasse=" + utilisateur.getMotdepasse() + "&role=" + Arrays.toString(utilisateur.getRole()) + "&image=" + utilisateur.getImage();
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
        String url = Statics.Base_URL + "/listUsersM";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Users = parse(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Users;

    }

    public boolean updateUser(Utilisateurs user, int id) {
        System.out.println("here");
        String url = Statics.Base_URL + "/updateUsersM/"+id
                + "?cinuser=" + user.getCinUser()
                + "&nomuser=" + user.getNomUser()
                + "&prenomuser=" + user.getPrenomUser()
                + "&teluser=" + user.getTelUser()
                + "&adresseuser=" + user.getAdresseUser()
                + "&email=" + user.getEmail()
                + "&motdepasse=" + user.getMotdepasse()
                +"&image="+user.getImage();

                
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
        String url = Statics.Base_URL + "/deleteUsersM/" + id;
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
}
