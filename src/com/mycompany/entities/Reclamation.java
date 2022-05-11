package com.mycompany.entities;

import java.util.Date;

public class Reclamation {

    private int id;
    private String titre;
    private String description;
    private Date date;
    private Event event;
    private Utilisateurs utilisateur;

    public Reclamation() {
    }

    public Reclamation(int id, String titre, String description, Date date, Event event, Utilisateurs utilisateur) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.event = event;
        this.utilisateur = utilisateur;
    }

    public Reclamation(String titre, String description, Date date, Event event, Utilisateurs utilisateur) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.event = event;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Utilisateurs getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateurs utilisateur) {
        this.utilisateur = utilisateur;
    }


}