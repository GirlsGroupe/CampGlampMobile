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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Publications;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MossMoss
 */
public class ServicePublications {

    public ArrayList<Publications> pubs;
    private boolean resultat;
    public static ServicePublications instance = null;

    private ConnectionRequest req;

    public static ServicePublications getInstance() {
        if (instance == null) {
            instance = new ServicePublications();
        }
        return instance;
    }

    public ServicePublications() {
        req = new ConnectionRequest();

    }

    //parse publication
    public ArrayList<Publications> parsePublication(String jsonText) {
        try {
            pubs = new ArrayList<>();
            JSONParser j = new JSONParser();
            System.out.println("-----------json----------------------");
            System.out.println(jsonText.toCharArray());
            Map<String, Object> PostsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) PostsListJson.get("root");
            //parsing json in individual publications 
            for (Map<String, Object> obj : list) {
                Publications publication = new Publications();
                System.out.println("icii");

                float idPub = Float.parseFloat(obj.get("idpub").toString());
                publication.setIdPub((int) idPub);

                Object idu = obj.get("idu");
                LinkedHashMap<Object, Object> ids = new LinkedHashMap<>();
                ids = (LinkedHashMap<Object, Object>) obj.get("iduser");
                publication.setIdUser(ids);
                publication.setDateCreation(obj.get("datecreation").toString().substring(0, 10));
                publication.setDescriptionPub(obj.get("descriptionpub").toString());
                float likes = Float.parseFloat(obj.get("nbrlikes").toString());

                publication.setNbrlikes((int) likes);

                publication.setSourcePub(obj.get("sourcePub").toString());

                if (obj.get("sourcePub").toString().contains(".png") || obj.get("sourcePub").toString().contains(".jpg") || obj.get("sourcePub").toString().contains(".gif")) {
                    pubs.add(publication);
                }
            }

        } catch (IOException ex) {
            System.out.println("Exception in parsing publications ");
        }

        return pubs;
    }

    //consommation du webservice getAllPublications
    public ArrayList<Publications> getAllPublications() {
        req = new ConnectionRequest();
        String url = "http://127.0.0.1:8000/mobile/listePub";

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                pubs = parsePublication(new String(req.getResponseData()));
                System.out.println(pubs);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return pubs;
    }

        public boolean addPublication(Publications p) {

        String url = Statics.Base_URL + "/mobile/addPublication?sourcePub=" + p.getSourcePub()+ "&descriptionpub=" + p.getDescriptionPub()+ "&idUser=" + p.getIdU();
        req.setUrl(url);
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
