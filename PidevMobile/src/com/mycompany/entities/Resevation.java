/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author Khach
 */
public class Resevation {
    private int idreservation;
    private String etat;
    private Date datereservation;
    private int id, iduser;

    public Resevation(String etat, Date datereservation, int id, int iduser) {
        this.etat = etat;
        this.datereservation = datereservation;
        this.id = id;
        this.iduser = iduser;
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

    public Date getDatereservation() {
        return datereservation;
    }

    public void setDatereservation(Date datereservation) {
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
