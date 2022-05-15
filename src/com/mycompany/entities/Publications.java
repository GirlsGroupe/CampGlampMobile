/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.LinkedHashMap;

/**
 *
 * @author MossMoss
 */
public class Publications {
    

    private LinkedHashMap<Object,Object> idUser;

    private int idU;
     private int idPub, nbrlikes;
     private String descriptionPub, sourcePub,dateCreation;
 
    public Publications() {
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

  

    public Publications(LinkedHashMap<Object, Object> idUser, int idPub, int nbrlikes, String descriptionPub, String sourcePub, String dateCreation) {
        this.idUser = idUser;
        this.idPub = idPub;
        this.nbrlikes = nbrlikes;
        this.descriptionPub = descriptionPub;
        this.sourcePub = sourcePub;
        this.dateCreation = dateCreation;
    }

    public LinkedHashMap<Object, Object> getIdUser() {
        return idUser;
    }

    public void setIdUser(LinkedHashMap<Object, Object> idUser) {
        this.idUser = idUser;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU (int idUser) {
        this.idU= idUser;
    }

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    public int getNbrlikes() {
        return nbrlikes;
    }

    public void setNbrlikes(int nbrlikes) {
        this.nbrlikes = nbrlikes;
    }

    public String getDescriptionPub() {
        return descriptionPub;
    }

    public void setDescriptionPub(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    public String getSourcePub() {
        return sourcePub;
    }

    @Override
    public String toString() {
        return "Publications{" +" idUser=" + idUser + ", idPub=" + idPub + ", nbrlikes=" + nbrlikes + ", descriptionPub=" + descriptionPub + ", sourcePub=" + sourcePub + '}';
    }

    public void setSourcePub(String sourcePub) {
        this.sourcePub = sourcePub;
    }

}
