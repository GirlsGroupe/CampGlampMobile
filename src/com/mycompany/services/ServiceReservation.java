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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.spinner.Picker;
import com.mycompany.entities.Resevation;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Khach
 */
public class ServiceReservation {

    public static ServiceReservation instance = null;
    public ArrayList<Resevation> reservations;
    private ConnectionRequest req;
    public boolean result;
    private MultipartRequest request;

    public static ServiceReservation getInstance() {
        if (instance == null) {
            instance = new ServiceReservation();
        }
        return instance;
    }

    public ServiceReservation() {
        req = new ConnectionRequest();
    }

    public boolean addReservation(TextField etat, TextField datereservation) {
        try {
            String url = Statics.Base_URL + "/addRes?etat=" + etat.getText().toString()
                    + "&datereservation=" + datereservation.getText().toString();
            System.out.println(url);
            req.setPost(true);
            req.setUrl(url);
            req.setFailSilently(true);
            req.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    System.out.println("action performed");

                    result = req.getResponseCode() == 200;
                    System.out.println(req.getResponseCode());
                    System.out.println(req.getRequestBody());
                    req.removeResponseListener(this);

                }
            });
            NetworkManager.getInstance().addToQueueAndWait(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
    //affichage Centre

    public ArrayList<Resevation> parse(String jsonTxt) {
        try {
            reservations = new ArrayList<>();

            JSONParser parser = new JSONParser();
            // System.out.println("-----------json----------------------");
            System.out.println(jsonTxt.toCharArray());

            Map<String, Object> reservationJSON;
            reservationJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
            List<Map<String, Object>> listOfMaps;
            listOfMaps = (List<Map<String, Object>>) reservationJSON.get("root");
            for (Map<String, Object> obj : listOfMaps) {
                Resevation reservation = new Resevation();
                float idreservation = Float.parseFloat(obj.get("idreservation").toString());
                String etat = obj.get("etat").toString();
                String datereservation = obj.get("datereservation").toString();
                reservation.setIdreservation((int)idreservation);
                reservation.setEtat(etat);
                reservation.setDatereservation(datereservation);
                reservations.add(reservation);
            }
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
                        System.out.println("aaa");

        }
        return reservations;
    }

    public ArrayList<Resevation> getReservation() {
        request = new MultipartRequest();
        ArrayList<Resevation> result = new ArrayList<>();
        String url = Statics.Base_URL + "/listeRes";
        request.setUrl(url);
        System.out.println("hello2");

        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    System.out.println("hello3");

                    String dataaa = new String(request.getResponseData(), "utf-8");
                    System.out.println("our dataaa " + dataaa);
                    reservations = parse(dataaa);
                    request.removeResponseListener(this);
                } catch (Exception ex) {

                }
            }

        });
        System.out.println("hello4");

        NetworkManager.getInstance().addToQueueAndWait(request);
        return reservations;
    }

    public boolean deleteReservation(int idreservation) {
        String url = Statics.Base_URL + "/deleteResJson?idreservation=" + idreservation;
       req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode() == 200;
                req.removeResponseListener(this);
                System.out.println("arso");
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

    public boolean updateReservation(int idreservation, TextField etat, TextField datereservation) {
        String url = Statics.Base_URL + "/updateResJson?idreservation=" +idreservation +"&etat=" 
                + etat.getText() + "&datereservation=" + datereservation.getText();
        req.setUrl(url);
         req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }

}

