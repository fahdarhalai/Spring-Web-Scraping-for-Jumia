package com.springapp.WebScrapping.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class SousCategorie {
    private Integer id;
    private String nom;
    private String lien;
    private LocalTime temps_action;
    private LocalDate date_action;
    private Integer id_categorie;

    public SousCategorie(String nom, String lien, LocalTime temps_action, LocalDate date_action, Integer id_categorie) {
        this.id = id;
        this.nom = nom;
        this.lien = lien;
        this.temps_action = temps_action;
        this.date_action = date_action;
        this.id_categorie = id_categorie;
    }

    public SousCategorie(Integer id, String nom, String lien, LocalTime temps_action, LocalDate date_action, Integer id_categorie) {
        this(nom, lien, temps_action, date_action, id_categorie);
        this.id = id;
    }

    public SousCategorie() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
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

    public Integer getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(Integer id_categorie) {
        this.id_categorie = id_categorie;
    }

    @Override
    public String toString() {
        return "SousCategorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", lien='" + lien + '\'' +
                ", temps_action=" + temps_action +
                ", date_action=" + date_action +
                ", id_categorie=" + id_categorie +
                '}';
    }
}
