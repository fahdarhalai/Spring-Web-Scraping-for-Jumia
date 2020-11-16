package com.springapp.WebScrapping.dao;

import com.springapp.WebScrapping.models.Categorie;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CategorieDAO {

    Categorie get(Integer id) throws SQLException;
    List<Categorie> getAll() throws SQLException;
    List<Categorie> getAll(Integer id_site, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2) throws SQLException;
    int save(Categorie t);
    int update(Categorie t) throws SQLException;
    int delete(Integer id) throws SQLException;
    int compter() throws SQLException;
}
