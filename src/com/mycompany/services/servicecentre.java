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
import com.mycompany.entities.centredecamping;
import com.mycompany.utils.Statics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mocha
 */
public class servicecentre {

    public static servicecentre instance = null;

    private ConnectionRequest req;
    public int result;

    public static servicecentre getInstance() {
        if (instance == null) {
            instance = new servicecentre();
        }
        return instance;
    }

    public servicecentre() {
        req = new ConnectionRequest();
    }
//ajouter Centre

    public void addcentre(centredecamping centre) {
        String url = Statics.BASE_URL + "/addCentreDeCampingJSON?nomCentre=" + centre.getNomCentre()
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
    public ArrayList<centredecamping> affichagecentres() {

        ArrayList<centredecamping> result = new ArrayList<>();
        String url = Statics.BASE_URL + "/listCentreDeCampingJSON";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapCentre = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapCentre.get("root");
                    for (Map<String, Object> obj : listOfMaps) {
                        centredecamping centre = new centredecamping();

                        int idcentre = Integer.parseInt(obj.get("idcentre").toString());
                        String nomcentre = obj.get("nomcentre").toString();
                        String adressecentre = obj.get("adressecentre").toString();
                        Float prixcentre = Float.parseFloat(obj.get("prixcentre").toString());
                        String typecentre = obj.get("typecentre").toString();
                        String villecentre = obj.get("villecentre").toString();
                        String gouvernorat = obj.get("gouvernorat").toString();

                        centre.setIdCentre(idcentre);
                        centre.setNomCentre(nomcentre);
                        centre.setTypeCentre(typecentre);
                        centre.setAdresseCentre(adressecentre);
                        centre.setPrixCentre(prixcentre);
                        centre.setTypeCentre(typecentre);
                        centre.setVilleCentre(villecentre);
                        centre.setGouvernorat(gouvernorat);

                        result.add(centre);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return result;

    }
    //delete centre

    public int deletecentre(centredecamping centre) {
        req.setUrl(Statics.BASE_URL + "/DeleteCentreJSON/idCentre");
        req.addArgument("idCentre", String.valueOf(centre.getIdCentre()));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode();
                req.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception e) {

        }
        return result;
    }
//update centre

    public int modifiercentre(centredecamping centre) {
        req.setUrl(Statics.BASE_URL + "/updateCentreJSON/idCentre?nomcentre=" + centre.getNomCentre()
                + "&adressecentre=" + centre.getAdresseCentre()
                + "&prixcentre=" + centre.getPrixCentre()
                + "&typecentre=" + centre.getTypeCentre()
                + "&villecentre=" + centre.getVilleCentre()
                + "&gouvernorat=" + centre.getGouvernorat()
                + "&adressecentre=" + centre.getAdresseCentre());
        req.addArgument("nomcentre", String.valueOf(centre.getNomCentre()));
        req.addArgument("adreesecentre", String.valueOf(centre.getAdresseCentre()));
        req.addArgument("prixcentre",String.valueOf( centre.getPrixCentre()));
        req.addArgument("typecentre", String.valueOf(centre.getTypeCentre()));
        req.addArgument("ville", String.valueOf(centre.getVilleCentre()));
        req.addArgument("gouvernorat", String.valueOf(centre.getGouvernorat()));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode();
                req.removeResponseListener(this);

            }
        });
        try {
            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception e) {

        }
        return result;
    }

}
