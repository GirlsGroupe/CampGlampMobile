/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Commande extends Produit {
    
    private int idcommande , numerodecommande;
    private String nomutilisateur, prenomutilisateur, adresseutilisateur , role ;
    private int prixcommande;
    private Date datecommande;
    private int iduser , idproduit;

    public Commande() {
    }

    public Commande(int idcommande, int numerodecommande, String nomutilisateur, String prenomutilisateur, String adresseutilisateur, String role, int prixcommande, Date datecommande, int iduser, int idproduit) {
        this.idcommande = idcommande;
        this.numerodecommande = numerodecommande;
        this.nomutilisateur = nomutilisateur;
        this.prenomutilisateur = prenomutilisateur;
        this.adresseutilisateur = adresseutilisateur;
        this.role = role;
        this.prixcommande = prixcommande;
        this.datecommande = datecommande;
        this.iduser = iduser;
        this.idproduit = idproduit;
    }

    public Commande(int numerodecommande, String nomutilisateur, String prenomutilisateur, String adresseutilisateur, String role, int prixcommande, Date datecommande, int iduser, int idproduit) {
        this.numerodecommande = numerodecommande;
        this.nomutilisateur = nomutilisateur;
        this.prenomutilisateur = prenomutilisateur;
        this.adresseutilisateur = adresseutilisateur;
        this.role = role;
        this.prixcommande = prixcommande;
        this.datecommande = datecommande;
        this.iduser = iduser;
        this.idproduit = idproduit;
    }

    public int getIdcommande() {
        return idcommande;
    }

    public void setIdcommande(int idcommande) {
        this.idcommande = idcommande;
    }

    public int getNumerodecommande() {
        return numerodecommande;
    }

    public void setNumerodecommande(int numerodecommande) {
        this.numerodecommande = numerodecommande;
    }

    public String getNomutilisateur() {
        return nomutilisateur;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getPrenomutilisateur() {
        return prenomutilisateur;
    }

    public void setPrenomutilisateur(String prenomutilisateur) {
        this.prenomutilisateur = prenomutilisateur;
    }

    public String getAdresseutilisateur() {
        return adresseutilisateur;
    }

    public void setAdresseutilisateur(String adresseutilisateur) {
        this.adresseutilisateur = adresseutilisateur;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPrixcommande() {
        return prixcommande;
    }

    public void setPrixcommande(int prixcommande) {
        this.prixcommande = prixcommande;
    }

    public Date getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Date datecommande) {
        this.datecommande = datecommande;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }
    
    
    
    
    
}

