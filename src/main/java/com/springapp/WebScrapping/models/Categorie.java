package com.springapp.WebScrapping.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Categorie {
    private Integer id;
    private String nom;
    private String lien;
    private LocalTime temps_action;
    private LocalDate date_action;
    private Integer id_site;

    public Categorie(String nom, String lien, LocalTime temps_action, LocalDate date_action, Integer id_site) {
        this.id = id;
        this.nom = nom;
        this.lien = lien;
        this.temps_action = temps_action;
        this.date_action = date_action;
        this.id_site = id_site;
    }

    public Categorie(Integer id, String nom, String lien, LocalTime temps_action, LocalDate date_action, Integer id_site) {
        this(nom, lien, temps_action, date_action, id_site);
        this.id = id;
    }

    public Categorie() {

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

    public Integer getId_site() {
        return id_site;
    }

    public void setId_site(Integer id_site) {
        this.id_site = id_site;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", lien='" + lien + '\'' +
                ", temps_action=" + temps_action +
                ", date_action=" + date_action +
                ", id_site=" + id_site +
                '}';
    }
}
