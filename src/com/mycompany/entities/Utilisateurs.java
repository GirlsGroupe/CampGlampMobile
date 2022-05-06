/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author MossMoss
 */
public class Utilisateurs {
     private int idUser, isverified;
     private String nomUser, prenomUser, telUser,adresseUser, email, motdepasse,cinUser,image;
     private String[] role;
    
     
     public Utilisateurs(String nomUser, String prenomUser, String image) {
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.image = image;
    }

    public Utilisateurs() {

    }

    public Utilisateurs(String cinUser, String nomUser, String prenomUser, String telUser, String adresseUser, String email, String motdepasse) {
        this.cinUser = cinUser;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.telUser = telUser;
        this.adresseUser = adresseUser;
        this.email = email;
        this.motdepasse = motdepasse;
    }

    public Utilisateurs(int idUser, int isverified, String nomUser, String prenomUser, String telUser, String adresseUser, String email, String motdepasse, String cinUser, String image, String[] role) {
        this.idUser = idUser;
        this.isverified = isverified;
        this.nomUser = nomUser;
        this.prenomUser = prenomUser;
        this.telUser = telUser;
        this.adresseUser = adresseUser;
        this.email = email;
        this.motdepasse = motdepasse;
        this.cinUser = cinUser;
        this.image = image;
        this.role = role;
    }



    public String getPrenomUser() {
        return prenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsverified() {
        return isverified;
    }

    public void setIsverified(int isverified) {
        this.isverified = isverified;
    }

    @Override
    public String toString() {
        return "Utilisateurs{" + "idUser=" + idUser + ", cinUser=" + cinUser + ", telUser=" + telUser + ", nomUser=" + nomUser + ", prenomUser=" + prenomUser + ", adresseUser=" + adresseUser + ", email=" + email + '}';
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getCinUser() {
        return cinUser;
    }

    public void setCinUser(String cinUser) {
        this.cinUser = cinUser;
    }

    public String getTelUser() {
        return telUser;
    }

    public void setTelUser(String telUser) {
        this.telUser = telUser;
    }

   

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getPernomUser() {
        return prenomUser;
    }

    public void setPernomUser(String prenomUser) {
        this.prenomUser = prenomUser;
    }

    public String getAdresseUser() {
        return adresseUser;
    }

    public void setAdresseUser(String adresseUser) {
        this.adresseUser = adresseUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

}

