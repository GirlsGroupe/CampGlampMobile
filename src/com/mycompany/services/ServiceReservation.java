/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.e;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
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
    
    public static ServiceReservation getInstance(){
        if(instance == null){
            instance = new ServiceReservation();
        } return instance;
    }
    
    public ServiceReservation(){
        req = new ConnectionRequest();
    }
    
    public void addReservation(Resevation resevation){
        String url = Statics.Base_URL+"/addRes?etat="+resevation.getEtat()+"&datereservation="+resevation.getDatereservation()+
                "&id="+resevation.getId()+"&iduser="+resevation.getIduser();
        req.setUrl(url);
        req.addResponseListener((e)-> {
        String str= new String(req.getResponseData());
        System.out.println("data == "+str);
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
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
            //    int id = Integer.parseInt(obj.get("id").toString());
            //    int iduser = Integer.parseInt(obj.get("iduser").toString());
             /*   Date currentTime = new Date (Double.valueOf(datereservation).longValue() * 1000);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = formatter.format(currentTime);*/

                reservation.setIdreservation( (int) idreservation);
                reservation.setEtat(etat);
                reservation.setDatereservation(datereservation);
             //   reservation.setId(id);
              //  reservation.setIduser(iduser);

                reservations.add(reservation);

            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
           ex.printStackTrace();
        }
        return reservations;
    }

    public ArrayList<Resevation> getReservation() {
        req = new ConnectionRequest();
        String url = Statics.Base_URL + "/listeRes";
        System.out.println("===>" + url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reservations = parse(new String(req.getResponseData()));
                req.removeResponseListener(this);
                               System.out.println("hneeeeeeeeeeeeeeeeeeeeeeeee");

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservations;
    }
     
     public boolean deleteReservation(int idreservation) {
        String url = Statics.Base_URL + "/deleteResJson/" + idreservation;
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
     public boolean updateReservation(Resevation r, int idreservation) {
        String url =Statics.Base_URL + "/updateResJson/" + idreservation+
                "?etat="+r.getEtat()
                +"&datereservation="+r.getDatereservation();
        req.setUrl(url);
      
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                result = req.getResponseCode() == 200;
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