package com.camp.services;

import com.camp.entities.Event;
import com.camp.utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventService {

    public static EventService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Event> listEvents;


    private EventService() {
        cr = new ConnectionRequest();
    }

    public static EventService getInstance() {
        if (instance == null) {
            instance = new EventService();
        }
        return instance;
    }

    public ArrayList<Event> getAll() {
        listEvents = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/event");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listEvents = getList();
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

        return listEvents;
    }

    private ArrayList<Event> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Event event = new Event(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        (String) obj.get("nom")

                );

                listEvents.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listEvents;
    }

}
