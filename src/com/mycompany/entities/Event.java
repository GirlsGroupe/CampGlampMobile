/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author louay
 */
public class Event {

    private int id;
    private String nom;
    private String description;
    private String type;
    private String lieu;
    private String date;
    private int prix;
    private String image;
    private String status;

    public Event() {
    }

    public Event(String nom, String description, String type, String lieu, String date, int prix, String image, String status) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.lieu = lieu;
        this.date = date;
        this.prix = prix;
        this.image = image;
        this.status = status;
    }

    public Event(int id, String nom, String description, String type, String lieu, String date, int prix, String image, String status) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.lieu = lieu;
        this.date = date;
        this.prix = prix;
        this.image = image;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", type=" + type + ", lieu=" + lieu + ", date=" + date + ", prix=" + prix + ", image=" + image + ", status=" + status + '}';
    }

}
