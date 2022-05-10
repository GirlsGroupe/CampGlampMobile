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
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Event;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author louay
 */
public class ServiceEvent {

    public static ServiceEvent instance = null;
    String json;
    private boolean resultat;
    public ArrayList<Event> events;
    private MultipartRequest request;

    private final ConnectionRequest req;

    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }

    public ServiceEvent() {
        req = new ConnectionRequest();
        request = new MultipartRequest();
    }

    public boolean addEvent(Event event) {
        try {
            String url = Statics.BASE_URL + "/addEventjson?nom=" + event.getNom()
                    + "&description=" + event.getDescription()
                    + "&type=" + event.getType()
                    + "&lieu=" + event.getLieu()
                    + "&date=" + event.getDate()
                    + "&prix=" + event.getPrix()
                    + "&image=" + event.getImage()
                    + "&status=" + event.getStatus();
            System.out.println(url);
            req.setPost(true);

            req.setUrl(url);
            req.setFailSilently(true);

            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    System.out.println("action performed");

                    resultat = req.getResponseCode() == 200;
                    System.out.println(req.getResponseCode());
                    System.out.println(req.getRequestBody());
                    req.removeResponseListener(this);

                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultat;

    }

    public void editEvent(int id, Event event) {
        String url = Statics.BASE_URL + "/modifEventjson/" + id + "?nom=" + event.getNom()
                + "&description=" + event.getDescription()
                + "&type=" + event.getType()
                + "&lieu=" + event.getLieu()
                + "&date=" + event.getDate()
                + "&prix=" + event.getPrix()
                + "&image=" + event.getImage()
                + "&status=" + event.getStatus();
        System.out.println(url);
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public boolean deleteEvent(int id) {
        String url = Statics.BASE_URL + "/removeEventjson/" + id;
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

    public ArrayList<Event> parse(String jsonTxt) {
        try {
            events = new ArrayList<>();

            JSONParser parser = new JSONParser();

            Map<String, Object> CategoriesJSON;
            CategoriesJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
            List<Map<String, Object>> listeRec;
            listeRec = (List<Map<String, Object>>) CategoriesJSON.get("root");

            for (Map<String, Object> item : listeRec) {
                Event event = new Event();

                float id = Float.parseFloat(item.get("id").toString());
                event.setId((int) id);
                event.setNom(item.get("nom").toString());
                event.setDescription(item.get("description").toString());
                event.setType(item.get("type").toString());
                event.setLieu(item.get("lieu").toString());
                event.setDate(item.get("date").toString());
                float prix = Float.parseFloat(item.get("prix").toString());
                event.setPrix((int) prix);
                event.setImage(item.get("image").toString());
                event.setStatus(item.get("status").toString());
                events.add(event);
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return events;

    }

    public ArrayList<Event> showEvents() {
        request = new MultipartRequest();
        String url = Statics.BASE_URL + "/alleventsjson";
        request.setUrl(url);

        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    String dataaa = new String(request.getResponseData(), "utf-8");
                    System.out.println("our dataaa " + dataaa);
                    events = parse(dataaa);
                    request.removeResponseListener(this);
                } catch (Exception ex) {
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(request);
        return events;
    }
}
