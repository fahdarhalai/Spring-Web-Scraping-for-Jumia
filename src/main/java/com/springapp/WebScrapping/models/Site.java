package com.springapp.WebScrapping.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Site {
    private Integer id;
    private String lien;
    private String nom;
    private LocalTime temps_action;
    private LocalDate date_action;

    public Site() {
    }

    public Site(String lien, String nom, LocalTime temps_action, LocalDate date_action) {
        this.lien = lien;
        this.nom = nom;
        this.temps_action = temps_action;
        this.date_action = date_action;
    }

    public Site(Integer id, String lien, String nom, LocalTime temps_action, LocalDate date_action) {
        this(lien, nom, temps_action, date_action);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        return "Site{" +
                "id=" + id +
                ", lien='" + lien + '\'' +
                ", nom='" + nom + '\'' +
                ", temps_action=" + temps_action +
                ", date_action=" + date_action +
                '}';
    }
}
