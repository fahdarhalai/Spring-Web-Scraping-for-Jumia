package com.springapp.WebScrapping.dao;

import com.springapp.WebScrapping.models.Produit;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ProduitDAO {

    Produit get(Integer id) throws SQLException;
    List<Produit> getAll() throws SQLException;
    List<Produit> getAll(Integer id_sousCategorie, String marque, Integer minEval, Integer maxEval, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2, Integer compteur) throws SQLException;
    int save(Produit t);
    int update(Produit t) throws SQLException;
    int delete(Integer id) throws  SQLException;
    int compter() throws SQLException;
}
