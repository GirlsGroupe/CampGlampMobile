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
import com.codename1.ui.layouts.BoxLayout;

import com.mycompany.entities.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.ui.util.Resources;


/**
 *
 * @author LENOVO
 */
public class ServiceProduit {
    
    public static ServiceProduit instance= null;
    private ConnectionRequest req;
    
    public ArrayList<Produit> PRODUITS;
    private boolean resultat;
    
    public static ServiceProduit getInstance() {
        
        if(instance == null) 
            instance = new ServiceProduit();
        return instance;
    }
    
   
    public ServiceProduit() {
        
        req = new ConnectionRequest();
    }
    
    
    public void ajouterProduit(Produit produit) {
        
        String url="http://127.0.0.1:8000/AjouterProduitMobile?referenceproduit="+produit.getReferenceproduit()+"&nomproduit="+produit.getNomproduit()+"&prixproduit="+produit.getPrixproduit()+"&quantiteproduit="+produit.getQuantiteproduit()+"&marqueproduit="+produit.getMarqueproduit()+"&descriptionproduit="+produit.getDescriptionproduit()+"&typeproduit="+produit.getTypeproduit()+"&couleur="+produit.getCouleur()+"&taille="+produit.getTaille()+"&imageproduit="+produit.getImageproduit();
     
          req.setUrl(url);
                req.addResponseListener((e)-> {
                    
                    String str = new String(req.getResponseData());
                    System.out.println("data=="+str);
                    });
                    NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    
    
    
    public  ArrayList<Produit>parse(String jsonTxt)
            { 
                
                 ArrayList<Produit> PRODUITS=new ArrayList<>();
                try {
                       
              
              
               
             JSONParser parser;
                       parser=new JSONParser();
                   Map<String, Object> mapProduits = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
                   List<Map<String,Object>> listOfMaps=(List<Map<String,Object>>) mapProduits.get("root");
                       
                  for (Map<String,Object> obj : listOfMaps) {
                      
                       Produit p=new Produit();
                         
                       float idpr= Float.parseFloat(obj.get("idproduit").toString());
                          String nomproduit=obj.get("nomproduit").toString();
                    /*      String marqueproduit=obj.get("marqueproduit").toString();
                          String descriptionproduit=obj.get("descriptionproduit").toString();
                          String couleur=obj.get("couleur").toString();
                          String taille=obj.get("taille").toString();
                          String imageproduit=obj.get("imageproduit").toString();
                          String quantiteproduit=obj.get("quantiteproduit").toString();
                          String prixproduit=obj.get("prixproduit").toString();
                          String referenceproduit=obj.get("referenceproduit").toString();
                       String typeproduit=obj.get("typeproduit").toString();

*/
                          p.setIdproduit((int)idpr);
                          p.setNomproduit(nomproduit);
                      /*    p.setReferenceproduit(referenceproduit);
                          p.setDescriptionproduit(descriptionproduit);
                          p.setCouleur(couleur);
                          p.setImageproduit(imageproduit);
                          p.setPrixproduit(prixproduit);
                          p.setQuantiteproduit(quantiteproduit);
                          p.setTaille(taille);
                       p.setMarqueproduit(marqueproduit);
                       p.setTypeproduit(typeproduit);
                 */        //insert data into result                                           
                          PRODUITS.add(p);
                               }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
 
            return PRODUITS;
                   
               }
    
      public ArrayList<Produit> afficherProduits() {
        req = new ConnectionRequest();
        String url = "http://127.0.0.1:8000/produitMobile";
        req.setUrl(url);
        req.setPost(false);
      //  res = parse(new String(req.getResponseData()));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
            PRODUITS=parse(new String(req.getResponseData()));
                req.removeResponseListener(this);
                           System.out.println(PRODUITS +"from sevice PRODUITS");

            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return PRODUITS; 
      
      }
    

   
    
    public boolean SupprimerProduit(int id) {
        String url ="http://127.0.0.1:8000/supprimerMobile/" + id;
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
    
      public boolean ModifierProduit(Produit p, int id) {
        System.out.println("test");
        String url = "http://127.0.0.1:8000/modifierPMobile/"+id
                + "?referenceproduit=" + p.getReferenceproduit()
                + "&nomproduit=" + p.getNomproduit()
                + "&prixproduit=" + p.getPrixproduit()
                + "&quantiteproduit=" + p.getQuantiteproduit()
                + "&marqueproduit=" + p.getMarqueproduit()
                + "&descriptionproduit=" + p.getDescriptionproduit()
                + "&typeproduit=" + p.getTypeproduit()
                +"&couleur="+p.getCouleur()
                + "&taille=" + p.getTaille()
                +"&imageproduit="+p.getImageproduit()
                ;

        req.setUrl(url);
        System.out.println(url);
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
    
}
                
            

            
