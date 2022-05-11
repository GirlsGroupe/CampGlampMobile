/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.io.Preferences;

/**
 *
 * @author MossMoss
 */
public class SessionManager {

    public static Preferences pref; // 3ibara memoire sghira nsajlo fiha data 

    // hethom données ta3 user lyt7b tsajlhom fi session  ba3d login 
    private static int id;
    private static String nom;
    private static String prenom;
    private static String email;
    private static String cin;
    private static String tel;



    public static void setCin(String cin) {
        SessionManager.cin = cin;
    }

    public static String getTel() {
        return tel;
    }

    public static void setTel(String tel) {
        SessionManager.tel = tel;
    }
    private static String photo;
    private static String role;
    

   

    public SessionManager() {
    }

    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id", id);// kif nheb njib id user connecté apres njibha men pref 
    }

    public static void setId(int id) {
        pref.set("id", id);//nsajl id user connecté  w na3tiha identifiant "id";
    }

  
    


    public static String getEmail() {
        return pref.get("email", email);
    }

       public static String getRole() {
        return pref.get("role", role);
    }
         public static void setRole(String role) {
        pref.set("role", role);
    }

    public static void setEmail(String email) {
        pref.set("email", email);
    }



    public static String getPhoto() {
        return pref.get("photo", photo);
    }

    public static String getNom() {
          return pref.get("nom", nom);
    }

    public static String getPrenom() {
             return   pref.get("prenom", prenom);
    }

    public static void setNom(String nom) {
                pref.set("nom", nom);

    }

    public static void setPrenom(String prenom) {
       pref.set("prenom", prenom);

    }
    

    public static void setPhoto(String photo) {
        pref.set("photo", photo);
    }

}
