/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author LENOVO
 */
public class Produit {
    
  private int idproduit;
    private String referenceproduit ;
    private String nomproduit;
    private String prixproduit;
    private String quantiteproduit;
    private String marqueproduit;
    private String descriptionproduit;
    private String typeproduit;
    private String couleur;
    private String taille;
    private String imageproduit;

    public Produit() {
        
        
    }

    public Produit(int idproduit, String referenceproduit, String nomproduit, String prixproduit, String quantiteproduit, String marqueproduit, String descriptionproduit, String typeproduit, String couleur, String taille, String imageproduit) {
        this.idproduit = idproduit;
        this.referenceproduit = referenceproduit;
        this.nomproduit = nomproduit;
        this.prixproduit = prixproduit;
        this.quantiteproduit = quantiteproduit;
        this.marqueproduit = marqueproduit;
        this.descriptionproduit = descriptionproduit;
        this.typeproduit = typeproduit;
        this.couleur = couleur;
        this.taille = taille;
        this.imageproduit = imageproduit;
    }

    public Produit(String referenceproduit, String nomproduit, String prixproduit, String quantiteproduit, String marqueproduit, String descriptionproduit, String typeproduit, String couleur, String taille, String imageproduit) {
        this.referenceproduit = referenceproduit;
        this.nomproduit = nomproduit;
        this.prixproduit = prixproduit;
        this.quantiteproduit = quantiteproduit;
        this.marqueproduit = marqueproduit;
        this.descriptionproduit = descriptionproduit;
        this.typeproduit = typeproduit;
        this.couleur = couleur;
        this.taille = taille;
        this.imageproduit = imageproduit;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    public String getReferenceproduit() {
        return referenceproduit;
    }

    public void setReferenceproduit(String referenceproduit) {
        this.referenceproduit = referenceproduit;
    }

    public String getNomproduit() {
        return nomproduit;
    }

    public void setNomproduit(String nomproduit) {
        this.nomproduit = nomproduit;
    }

    public String getPrixproduit() {
        return prixproduit;
    }

    public void setPrixproduit(String prixproduit) {
        this.prixproduit = prixproduit;
    }

    public String getQuantiteproduit() {
        return quantiteproduit;
    }

    public void setQuantiteproduit(String quantiteproduit) {
        this.quantiteproduit = quantiteproduit;
    }

    public String getMarqueproduit() {
        return marqueproduit;
    }

    public void setMarqueproduit(String marqueproduit) {
        this.marqueproduit = marqueproduit;
    }

    public String getDescriptionproduit() {
        return descriptionproduit;
    }

    public void setDescriptionproduit(String descriptionproduit) {
        this.descriptionproduit = descriptionproduit;
    }

    public String getTypeproduit() {
        return typeproduit;
    }

    public void setTypeproduit(String typeproduit) {
        this.typeproduit = typeproduit;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getImageproduit() {
        return imageproduit;
    }

    public void setImageproduit(String imageproduit) {
        this.imageproduit = imageproduit;
    }
    
    
    }



