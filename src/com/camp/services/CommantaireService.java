package com.camp.services;

import com.camp.entities.Commantaire;
import com.camp.entities.Reclamation;
import com.camp.utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommantaireService {

    public static CommantaireService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Commantaire> listCommantaires;


    private CommantaireService() {
        cr = new ConnectionRequest();
    }

    public static CommantaireService getInstance() {
        if (instance == null) {
            instance = new CommantaireService();
        }
        return instance;
    }

    public ArrayList<Commantaire> getAll() {
        listCommantaires = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commantaire");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommantaires = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listCommantaires;
    }

    private ArrayList<Commantaire> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Commantaire commantaire = new Commantaire(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("description"),
                        makeReclamation((Map<String, Object>) obj.get("reclamation"))

                );

                listCommantaires.add(commantaire);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCommantaires;
    }

    public Reclamation makeReclamation(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Reclamation reclamation = new Reclamation();
        reclamation.setId((int) Float.parseFloat(obj.get("id").toString()));
        reclamation.setTitre((String) obj.get("titre"));
        return reclamation;
    }

    public int add(Commantaire commantaire) {
        return manage(commantaire, false);
    }

    public int edit(Commantaire commantaire) {
        return manage(commantaire, true);
    }

    public int manage(Commantaire commantaire, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL + "/commantaire/edit");
            cr.addArgument("id", String.valueOf(commantaire.getId()));
        } else {
            cr.setUrl(Statics.BASE_URL + "/commantaire/add");
        }

        cr.addArgument("description", commantaire.getDescription());
        cr.addArgument("reclamation", String.valueOf(commantaire.getReclamation().getId()));


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int commantaireId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/commantaire/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(commantaireId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
