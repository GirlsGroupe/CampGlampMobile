package com.mycompany.entities;

public class Commantaire {

    private int id;
    private String description;
    private Reclamation reclamation;

    public Commantaire() {
    }

    public Commantaire(int id, String description, Reclamation reclamation) {
        this.id = id;
        this.description = description;
        this.reclamation = reclamation;
    }

    public Commantaire(String description, Reclamation reclamation) {
        this.description = description;
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

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }


    @Override
    public String toString() {
        return "Commantaire : " +
                 ", Description=" + description
                + ", Reclamation=" + reclamation.getTitre()
                ;
    }


}