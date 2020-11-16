package com.springapp.WebScrapping.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Produit {
    private Integer id;
    private String titre;
    private String lien;
    private String image;
    private Float prix_produit;
    private String devise;
    private String marque;
    private Integer evaluation;
    private LocalTime temps_action;
    private LocalDate date_action;
    private Integer id_sous_categorie;

    public Produit(String titre, String lien, String image, Float prix_produit, String devise, String marque, Integer evaluation, LocalTime temps_action, LocalDate date_action, Integer id_sous_categorie) {
        this.titre = titre;
        this.lien = lien;
        this.image = image;
        this.prix_produit = prix_produit;
        this.devise = devise;
        this.marque = marque;
        this.evaluation = evaluation;
        this.temps_action = temps_action;
        this.date_action = date_action;
        this.id_sous_categorie = id_sous_categorie;
    }

    public Produit(Integer id, String titre, String lien, String image, Float prix_produit, String devise, String marque, Integer evaluation, LocalTime temps_action, LocalDate date_action, Integer id_sous_categorie) {
        this(titre, lien, image, prix_produit, devise, marque, evaluation, temps_action, date_action, id_sous_categorie);
        this.id = id;
    }

    public Produit() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
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

    public Integer getId_sous_categorie() {
        return id_sous_categorie;
    }

    public void setId_sous_categorie(Integer id_sous_categorie) {
        this.id_sous_categorie = id_sous_categorie;
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

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", lien='" + lien + '\'' +
                ", image='" + image + '\'' +
                ", prix_produit=" + prix_produit +
                ", devise='" + devise + '\'' +
                ", marque='" + marque + '\'' +
                ", evaluation=" + evaluation +
                ", temps_action=" + temps_action +
                ", date_action=" + date_action +
                ", id_sous_categorie=" + id_sous_categorie +
                '}';
    }
}
