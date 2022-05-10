package com.camp.entities;

import java.util.Date;

public class Aviscamp {

    private int id;
    private String description;
    private Date date;
    private int like;
    private float rate;
    private Utilisateur utilisateur;
    private Event event;
    private Reclamation reclamation;

    public Aviscamp() {
    }

    public Aviscamp(int id, String description, Date date, int like, float rate, Utilisateur utilisateur, Event event, Reclamation reclamation) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.like = like;
        this.rate = rate;
        this.utilisateur = utilisateur;
        this.event = event;
        this.reclamation = reclamation;
    }

    public Aviscamp(String description, Date date, int like, float rate, Utilisateur utilisateur, Event event, Reclamation reclamation) {
        this.description = description;
        this.date = date;
        this.like = like;
        this.rate = rate;
        this.utilisateur = utilisateur;
        this.event = event;
        this.reclamation = reclamation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }


}