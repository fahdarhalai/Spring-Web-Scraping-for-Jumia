package com.springapp.WebScrapping.dao;

import com.springapp.WebScrapping.models.Prix;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PrixDAO {

    Prix get(Integer id) throws SQLException;
    Prix getLast(Integer id_produit) throws SQLException;
    List<Prix> getAll() throws SQLException;
    List<Prix> getAll(Integer id_produit, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2, Integer compteur) throws SQLException;
    int save(Prix t);
    int update(Prix t) throws SQLException;
    int delete(Integer id) throws SQLException;

}
