/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author Mocha
 */
public class centredecamping {
    private int idCentre;
    private String nomCentre;
    private String adresseCentre;
    private float prixCentre;
    private String typeCentre;
    private String villeCentre;
    private String gouvernorat;

    public centredecamping() {
    }

    public centredecamping(String nomCentre, String adresseCentre, String typeCentre, String villeCentre, String gouvernorat) {
        this.nomCentre = nomCentre;
        this.adresseCentre = adresseCentre;
        this.typeCentre = typeCentre;
        this.villeCentre = villeCentre;
        this.gouvernorat = gouvernorat;
    }





    public int getIdCentre() {
        return idCentre;
    }

    public String getNomCentre() {
        return nomCentre;
    }

    public String getAdresseCentre() {
        return adresseCentre;
    }

    public float getPrixCentre() {
        return prixCentre;
    }

    public String getTypeCentre() {
        return typeCentre;
    }

    public String getVilleCentre() {
        return villeCentre;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setIdCentre(int idCentre) {
        this.idCentre = idCentre;
    }

    public void setNomCentre(String nomCentre) {
        this.nomCentre = nomCentre;
    }

    public void setAdresseCentre(String adresseCentre) {
        this.adresseCentre = adresseCentre;
    }

    public void setPrixCentre(float prixCentre) {
        this.prixCentre = prixCentre;
    }

    public void setTypeCentre(String typeCentre) {
        this.typeCentre = typeCentre;
    }

    public void setVilleCentre(String villeCentre) {
        this.villeCentre = villeCentre;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public centredecamping(int idCentre, String nomCentre, String adresseCentre, float prixCentre, String typeCentre, String villeCentre, String gouvernorat) {
        this.idCentre = idCentre;
        this.nomCentre = nomCentre;
        this.adresseCentre = adresseCentre;
        this.prixCentre = prixCentre;
        this.typeCentre = typeCentre;
        this.villeCentre = villeCentre;
        this.gouvernorat = gouvernorat;
    }

    @Override
    public String toString() {
        return "centredecamping{" + "idCentre=" + idCentre + ", nomCentre=" + nomCentre + ", adresseCentre=" + adresseCentre + ", prixCentre=" + prixCentre + ", typeCentre=" + typeCentre + ", villeCentre=" + villeCentre + ", gouvernorat=" + gouvernorat + '}';
    }

    
}
