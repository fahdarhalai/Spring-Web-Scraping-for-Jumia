package com.springapp.WebScrapping.dao;

import com.springapp.WebScrapping.models.SousCategorie;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SousCategorieDAO {

    SousCategorie get(Integer id) throws SQLException;
    List<SousCategorie> getAll() throws SQLException;
    List<SousCategorie> getAll(Integer id_categorie, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2) throws SQLException;
    int save(SousCategorie t);
    int update(SousCategorie t) throws SQLException;
    int delete(Integer id) throws SQLException;
    int compter() throws SQLException;
}
