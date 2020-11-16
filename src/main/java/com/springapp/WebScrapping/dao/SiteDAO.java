package com.springapp.WebScrapping.dao;


import com.springapp.WebScrapping.models.Site;

import java.sql.SQLException;
import java.util.List;

public interface SiteDAO {

    Site get(Integer id) throws SQLException;
    Site getLast() throws SQLException;
    Site getOneBy(String nom, String lien) throws SQLException;
    List<Site> getAll() throws SQLException;
    int save(Site t) throws SQLException;
    int update(Site t) throws SQLException;
    int delete(Integer id) throws SQLException;
    int compter() throws SQLException;
}
