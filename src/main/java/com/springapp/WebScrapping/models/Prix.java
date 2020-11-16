package com.springapp.WebScrapping.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Prix {
    private Integer id;
    private Float prix_produit;
    private String devise;
    private LocalTime temps_action;
    private LocalDate date_action;
    private Integer id_produit;

    public Prix(Float prix_produit, String devise, LocalTime temps_action, LocalDate date_action, Integer id_produit) {
        this.prix_produit = prix_produit;
        this.devise = devise;
        this.temps_action = temps_action;
        this.date_action = date_action;
        this.id_produit = id_produit;
    }

    public Prix(Integer id, Float prix_produit, String devise, LocalTime temps_action, LocalDate date_action, Integer id_produit) {
        this(prix_produit, devise, temps_action, date_action, id_produit);
        this.id = id;
    }

    public Prix() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getPrix_produit() {
        return prix_produit;
    }

    public void setPrix_produit(Float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public LocalTime getTemps_action() {
        return temps_action;
    }

    public void setTemps_action(LocalTime temps_action) {
        this.temps_action = temps_action;
    }

    public LocalDate getDate_action() {
        return date_action;
    }

    public void setDate_action(LocalDate date_action) {
        this.date_action = date_action;
    }

    public Integer getId_produit() {
        return id_produit;
    }

    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }

}
