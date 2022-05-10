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
import com.codename1.ui.util.Resources;
import com.mycompany.entities.centredecamping;
import com.mycompany.gui.Listecentre;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mocha
 */
public class servicecentre {

    public static servicecentre instance = null;
    private boolean result;
    private ConnectionRequest req;
    public ArrayList<centredecamping> centres;

    public static servicecentre getInstance() {
        if (instance == null) {
            instance = new servicecentre();
        }
        return instance;
    }

    public servicecentre() {
        req = new ConnectionRequest();
    }

//Ajouter centre
    public void addcentre(centredecamping centre) {
        String url = Statics.Base_URL + "/addCentreDeCampingJSON?idcentre= centre.getIdCentre()"
                + "&nomcentre=" + centre.getNomCentre()
                + "&adressecentre=" + centre.getAdresseCentre()
                + "&prixcentre=" + centre.getPrixCentre()
                + "&typecentre=" + centre.getTypeCentre()
                + "&villecentre=" + centre.getVilleCentre()
                + "&gouvernorat=" + centre.getGouvernorat();

        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    //affichage Centre
    public ArrayList<centredecamping> parse(String jsonTxt) {
        try {
            centres = new ArrayList<>();

            JSONParser parser = new JSONParser();
            System.out.println("-----------json----------------------");
            System.out.println(jsonTxt.toCharArray());

            Map<String, Object> centreJSON;
            centreJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
            List<Map<String, Object>> listOfMaps;
            listOfMaps = (List<Map<String, Object>>) centreJSON.get("root");
            for (Map<String, Object> obj : listOfMaps) {
                centredecamping centre = new centredecamping();
                float idcentre = Float.parseFloat(obj.get("idcentre").toString());
                String nomcentre = obj.get("nomcentre").toString();
                String adressecentre = obj.get("adressecentre").toString();
                Float prixcentre = Float.parseFloat(obj.get("prixcentre").toString());
                String typecentre = obj.get("typecentre").toString();
                String villecentre = obj.get("villecentre").toString();
                String gouvernorat = obj.get("gouvernorat").toString();

                centre.setIdCentre((int) idcentre);
                centre.setNomCentre(nomcentre);
                centre.setTypeCentre(typecentre);
                centre.setAdresseCentre(adressecentre);
                centre.setPrixCentre(prixcentre);
                centre.setTypeCentre(typecentre);
                centre.setVilleCentre(villecentre);
                centre.setGouvernorat(gouvernorat);

                centres.add(centre);

            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return centres;
    }

    public ArrayList<centredecamping> affichagecentres() {
        req = new ConnectionRequest();
        String url = Statics.Base_URL + "/listCentreDeCampingJSON";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                // System.out.println("hneeeeeeeeeeeeeeeeeeeeeeeee");
                centres = parse(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return centres;
    }

    //delete centre
    public boolean deletecentre(int id) {

        String url = Statics.Base_URL + "/DeleteCentreJSON/" + id;
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;

    }
//update centre

    public boolean modifiercentre(centredecamping centre, int idcentre) {
        String url = Statics.Base_URL + "/updateCentreJSON/" + idcentre
                + "?nomcentre=" + centre.getNomCentre()
                + "&adressecentre=" + centre.getAdresseCentre()
                + "&prixcentre=" + centre.getPrixCentre()
                + "&typecentre=" + centre.getTypeCentre()
                + "&villecentre=" + centre.getVilleCentre()
                + "&gouvernorat=" + centre.getGouvernorat()
                + "&adressecentre=" + centre.getAdresseCentre();
        req.setUrl(url);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
         NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

}
