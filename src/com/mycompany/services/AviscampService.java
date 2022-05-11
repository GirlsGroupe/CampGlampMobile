package com.mycompany.services;

import com.mycompany.entities.Aviscamp;
import com.mycompany.entities.Event;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Utilisateurs;
import com.mycompany.utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AviscampService {

    public static AviscampService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Aviscamp> listAviscamps;


    private AviscampService() {
        cr = new ConnectionRequest();
    }

    public static AviscampService getInstance() {
        if (instance == null) {
            instance = new AviscampService();
        }
        return instance;
    }

    public ArrayList<Aviscamp> getAll() {
        listAviscamps = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.Base_URL + "/mobile/aviscamp");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listAviscamps = getList();
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

        return listAviscamps;
    }

    private ArrayList<Aviscamp> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Aviscamp aviscamp = new Aviscamp(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("description"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("date")),
                        (int) Float.parseFloat(obj.get("like").toString()),
                        Float.parseFloat(obj.get("rate").toString()),
                        makeUtilisateur((Map<String, Object>) obj.get("utilisateur")),
                        makeEvent((Map<String, Object>) obj.get("event")),
                        makeReclamation((Map<String, Object>) obj.get("reclamation"))

                );

                listAviscamps.add(aviscamp);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listAviscamps;
    }

    public Utilisateurs makeUtilisateur(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateurs utilisateur = new Utilisateurs();
        //utilisateur.setIdUser((int) Float.parseFloat(obj.get("id").toString()));
        utilisateur.setNomUser((String) obj.get("nom"));
        return utilisateur;
    }

    public Event makeEvent(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Event event = new Event();
        //event.setId((int) Float.parseFloat(obj.get("id").toString()));
        event.setNom((String) obj.get("nom"));
        return event;
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

    public int add(Aviscamp aviscamp) {
        return manage(aviscamp, false);
    }

    public int edit(Aviscamp aviscamp) {
        return manage(aviscamp, true);
    }

    public int manage(Aviscamp aviscamp, boolean isEdit) {

        cr = new ConnectionRequest();


        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.Base_URL + "/mobile/aviscamp/edit");
            cr.addArgument("id", String.valueOf(aviscamp.getId()));
        } else {
            cr.setUrl(Statics.Base_URL + "/mobile/aviscamp/add");
        }

        cr.addArgument("description", aviscamp.getDescription());
        cr.addArgument("date", new SimpleDateFormat("dd-MM-yyyy").format(aviscamp.getDate()));
        cr.addArgument("like", String.valueOf(aviscamp.getLike()));
        cr.addArgument("rate", String.valueOf(aviscamp.getRate()));
        cr.addArgument("utilisateur", String.valueOf(aviscamp.getUtilisateur().getIdUser()));
        cr.addArgument("event", String.valueOf(aviscamp.getEvent().getId()));
        cr.addArgument("reclamation", String.valueOf(aviscamp.getReclamation().getId()));


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

    public int delete(int aviscampId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.Base_URL + "/mobile/aviscamp/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(aviscampId));

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
