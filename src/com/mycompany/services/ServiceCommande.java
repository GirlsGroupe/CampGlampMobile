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
import com.mycompany.entities.Commande;
import static com.mycompany.services.ServiceProduit.instance;


/**
 *
 * @author LENOVO
 */
public class ServiceCommande {
    
      
    public static ServiceCommande instance= null;
    private ConnectionRequest req;
    
    public ArrayList<Commande> commandesss;
    private boolean resultat;
    
     public static ServiceCommande getInstance() {
        
        if(instance == null) 
            instance = new ServiceCommande();
        return instance;
    }
    
   
    public ServiceCommande() {
        
        req = new ConnectionRequest();
    }
    
      public  ArrayList<Commande>parse2(String jsonTxt)
            { 
                
                 ArrayList<Commande> commandesss=new ArrayList<>();
                try {
                       
              
              
               
             JSONParser parser;
                       parser=new JSONParser();
                   Map<String, Object> mapCommandes = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
                   List<Map<String,Object>> listOfMaps2=(List<Map<String,Object>>) mapCommandes.get("root");
                       
                  for (Map<String,Object> obj : listOfMaps2) {
                      
                       Commande c=new Commande();
                         
                       float idc =Float.parseFloat(obj.get("idcommande").toString());
                         float idp =Float.parseFloat(obj.get("idproduit").toString());
                             float idU =Float.parseFloat(obj.get("iduser").toString());
                             float numeroc =Float.parseFloat(obj.get("numerodecommande").toString());
                        String nomutilisateur=obj.get("nomutilisateur").toString();
                        String prenomutilisateur=obj.get("prenomutilisateur").toString();
                        String adresseutilisateur=obj.get("adresseutilisateur").toString();
                        String role=obj.get("role").toString();
                        String prixcommande=obj.get("prixcommande").toString();
                          String imageCommande=obj.get("imageCommande").toString();
                                           
                           c.setIdcommande((int)idc);
                         c.setNomutilisateur(nomutilisateur);
                         c.setPrenomutilisateur(prenomutilisateur);
                        c.setAdresseutilisateur(adresseutilisateur);
                          c.setRole(role);
                          c.setPrixcommande(prixcommande);
                            c.setIdproduit((int)idc);
                              c.setIdproduit((int)idc);
                               c.setNumerodecommande((int)numeroc);
                               c.setImageCommande(imageCommande);
                                  
                          
                     /*     c.setPrixcommande(prixcommande);
                           c.setIduser((int)iduser);
                          c.setNomutilisateur(nomutilisateur);
                          c.setAdresseutilisateur(adresseutilisateur);*/
                       //   c.setDatecommande(datecommande);
                          commandesss.add(c);
                           //     System.out.println(Commande +"from sevice");
                               }
        } catch (IOException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }
 
            return commandesss;
      
                   
               }
    
      public ArrayList<Commande> afficherCommande() {
        req = new ConnectionRequest();
        String url = "hhttp://127.0.0.1:8000/listCommandes/";
        System.out.println("ffff");
        req.setUrl(url);
        req.setPost(false);
        
           System.out.println("ffff" +url );
      //  res = parse(new String(req.getResponseData()));
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                System.out.println("allo");
            commandesss=parse2(new String(req.getResponseData()));
                req.removeResponseListener(this);
               //  System.out.println(Commande +"from sevice");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
           System.out.println(commandesss +"from sevice !!!!!!!!!!!!!!!");
        return commandesss; 
            
      
      }
      
       public void ajouterCommande(Commande c) {
  
        
        String url="http://127.0.0.1:8000/AjouterCommande/8?nomutilisateur="+c.getNomutilisateur()
             //   +"&numerodecommande="+c.getNumerodecommande()
                +"&prenomutilisateur="+c.getPrenomutilisateur()
                +"&adresseutilisateur="+c.getAdresseutilisateur()
                +"&role="+c.getRole() 
                +"&prixcommande="+c.getPrixcommande()
          //       +"&datecommande="+c.getDatecommande()
                  +"&imageCommande="+c.getImageCommande();
        
        
        
     
          req.setUrl(url);
                req.addResponseListener((e)-> {
                    
                    String str = new String(req.getResponseData());
                    System.out.println("data=="+str);
                    });
                    NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
}


