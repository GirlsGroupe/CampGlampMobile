/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.*;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MossMoss
 */
public class servicesUtilisateurs {

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

    public ArrayList<Utilisateurs> afficherUtilisateurs() {
        ArrayList<Utilisateurs> rslt = new ArrayList<>();
        String url = Statics.Base_URL + "listUsersM";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
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
                        user.setIdUser((int)iduser);
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

    }

}
