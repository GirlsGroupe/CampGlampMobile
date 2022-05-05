/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.ConnectionRequest;
import static com.codename1.io.Log.e;
import com.codename1.io.NetworkManager;
import com.mycompany.entities.Resevation;
import com.mycompany.utils.Statics;
/**
 *
 * @author Khach
 */
public class ServiceReservation {
    
    public static ServiceReservation instance = null;
    
    private ConnectionRequest req;
    
    public static ServiceReservation getInstance(){
        if(instance == null){
            instance = new ServiceReservation();
        } return instance;
    }
    
    public ServiceReservation(){
        req = new ConnectionRequest();
    }
    
    public void addReservation(Resevation resevation){
        String url = Statics.BASE_URL+"addRes/idreservation?etat="+resevation.getEtat()+"&datereservation="+resevation.getDatereservation()+
                "&id="+resevation.getId()+"&iduser="+resevation.getIduser();
        req.setUrl(url);
        req.addResponseListener((e)-> {
        String str= new String(req.getResponseData());
        System.out.println("data == "+str);
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
