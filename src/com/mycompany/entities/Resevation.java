package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author Khach
 */
public class Resevation {
    private int idreservation;
    private String etat;
    private String datereservation;
    private int id, iduser;

    public Resevation() {
    }
    
    

    public Resevation(String etat, String datereservation, int id, int iduser) {
        this.etat = etat;
        this.datereservation = datereservation;
        this.id = id;
        this.iduser = iduser;
    }

    public Resevation(int idreservation, String etat, String datereservation, int id, int iduser) {
        this.idreservation = idreservation;
        this.etat = etat;
        this.datereservation = datereservation;
        this.id = id;
        this.iduser = iduser;
    }

    public Resevation(String etat, String datereservation) {
        this.etat = etat;
        this.datereservation = datereservation;
    }
    

    public int getIdreservation() {
        return idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(String datereservation) {
        this.datereservation = datereservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "Resevation{" + "etat=" + etat + ", datereservation=" + datereservation + ", id=" + id + ", iduser=" + iduser + '}';
    }
    
    
}