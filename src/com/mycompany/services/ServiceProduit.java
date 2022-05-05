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
import com.mycompany.entities.Produit;
import com.mycompany.utlis.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ServiceProduit {
    
    public static ServiceProduit instance= null;
    private ConnectionRequest req;
    
    public static ServiceProduit getInstance() {
        
        if(instance == null) 
            instance = new ServiceProduit();
        return instance;
    }
    
   
    public ServiceProduit() {
        
        req = new ConnectionRequest();
    }
    
    
    public void ajouterProduit(Produit produit) {
        
        String url=Statics.BASE_URL+"/AjouterProduit?referenceproduit="+produit.getReferenceproduit()+"&nomproduit="+produit.getNomproduit()+"&prixproduit="+produit.getPrixproduit()+"&quantiteproduit="+produit.getQuantiteproduit()+"&marqueproduit="+produit.getMarqueproduit()+"&descriptionproduit="+produit.getDescriptionproduit()+"&couleur="+produit.getCouleur()+"&taille="+produit.getTaille()+"&imageproduit="+produit.getImageproduit();
     
          req.setUrl(url);
                req.addResponseListener((e)-> {
                    
                    String str = new String(req.getResponseData());
                    System.out.println("data=="+str);
                    });
                    NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    
    
    public ArrayList<Produit>afficherProduits()
            {
               ArrayList<Produit> result=new ArrayList<>();
               
               String url=Statics.BASE_URL+"/produit";
               req.setUrl(url);
               
               
               req.addResponseListener(new ActionListener<NetworkEvent> () {
                   
                   public void actionPerformed(NetworkEvent evt) {
                       
                       JSONParser jsonp;
                       jsonp=new JSONParser();
                       
                       try {
                       
                           Map<String, Object> mapProduits = jsonp.parseJSON(new CharArrayReader(new String (req.getResponseData()).toCharArray()));
                           
                       List<Map<String,Object>> listOfMaps=(List<Map<String,Object>>) mapProduits.get("root");
                       
                       
                       for (Map<String,Object> obj : listOfMaps) {
                           
                           Produit p=new Produit();
                           
                          float idpr= Float.parseFloat(obj.get("idproduit").toString());
                          float quantiteproduit= Float.parseFloat(obj.get("quantiteproduit").toString());
                          float prixproduit= Float.parseFloat(obj.get("prixproduit").toString());
                          float referenceproduit= Float.parseFloat(obj.get("referenceproduit").toString());

                          String nomproduit=obj.get("nomproduit").toString();
                          String marqueproduit=obj.get("marqueproduit").toString();
                          String descriptionproduit=obj.get("descriptionproduit").toString();
                          String couleur=obj.get("couleur").toString();
                          String taille=obj.get("taille").toString();
                          String imageproduit=obj.get("imageproduit").toString();

                          p.setIdproduit((int)idpr);
                          p.setNomproduit(nomproduit);
                          p.setReferenceproduit((int)referenceproduit);
                          p.setDescriptionproduit(descriptionproduit);
                          p.setCouleur(couleur);
                          p.setImageproduit(imageproduit);
                          p.setPrixproduit((int)prixproduit);
                          p.setQuantiteproduit((int)quantiteproduit);
                         //insert data into result                                           
                          result.add(p);
                          
                       }
                    
                      
                       } catch (Exception ex) {
                           ex.printStackTrace();
                       } 
                   }
                       
               });
                   
                NetworkManager.getInstance().addToQueueAndWait(req);
                return result;
                   
               }
    
}
                
            

            
