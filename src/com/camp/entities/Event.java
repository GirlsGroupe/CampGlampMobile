package com.camp.entities;

public class Event {

    private int id;
    private String nom;

    public Event() {
    }

    public Event(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Event(String nom) {
        this.nom = nom;
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


}