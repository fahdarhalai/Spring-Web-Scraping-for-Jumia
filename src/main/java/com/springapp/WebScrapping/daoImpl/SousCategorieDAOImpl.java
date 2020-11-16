package com.springapp.WebScrapping.daoImpl;

import com.springapp.WebScrapping.dao.SousCategorieDAO;
import com.springapp.WebScrapping.models.SousCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SousCategorieDAOImpl implements SousCategorieDAO {

    @Autowired
    private Connection conn;

    public SousCategorieDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public SousCategorie get(Integer id) throws SQLException {
        SousCategorie sousCategorie = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sous_categories WHERE id="+id);
        if(rs.next()) {
            sousCategorie = new SousCategorie();
            sousCategorie.setId(rs.getInt("id"));
            sousCategorie.setLien(rs.getString("lien"));
            sousCategorie.setNom(rs.getString("nom"));
            sousCategorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            sousCategorie.setDate_action(rs.getDate("date_action").toLocalDate());
            sousCategorie.setId_categorie(rs.getInt("id_categorie"));
        }
        return sousCategorie;
    }

    @Override
    public List<SousCategorie> getAll() throws SQLException {
        List<SousCategorie> sousCategories = new ArrayList<SousCategorie>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sous_categories");
        while (rs.next()) {
            SousCategorie sousCategorie = new SousCategorie();
            sousCategorie.setId(rs.getInt("id"));
            sousCategorie.setLien(rs.getString("lien"));
            sousCategorie.setNom(rs.getString("nom"));
            sousCategorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            sousCategorie.setDate_action(rs.getDate("date_action").toLocalDate());
            sousCategorie.setId_categorie(rs.getInt("id_categorie"));
        }
        return sousCategories;
    }

    @Override
    public List<SousCategorie> getAll(Integer id_categorie, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2) throws SQLException {
        List<SousCategorie> sousCategories = new ArrayList<SousCategorie>();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM sous_categories WHERE id_categorie = " + id_categorie;
        if(t1==null){
            if(t2!=null){
                sql += " AND temps_action < " + Time.valueOf(t2).toString();
            }
        }else{
            if(t2==null){
                sql += " AND temps_action > " + Time.valueOf(t1).toString();
            }else{
                sql += " AND temps_action BETWEEN "+Time.valueOf(t1).toString()+" AND "+Time.valueOf(t2).toString();
            }
        }
        if(d1==null){
            if(d2!=null){
                sql += " AND date_action < " + Date.valueOf(d2).toString();
            }
        }else{
            if(d2==null){
                sql += " AND date_action > " + Date.valueOf(d1).toString();
            }else{
                sql += " AND date_action BETWEEN " + Date.valueOf(d1).toString() + " AND " + Date.valueOf(d2).toString();
            }
        }
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            SousCategorie sousCategorie = new SousCategorie();
            sousCategorie.setId(rs.getInt("id"));
            sousCategorie.setLien(rs.getString("lien"));
            sousCategorie.setNom(rs.getString("nom"));
            sousCategorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            sousCategorie.setDate_action(rs.getDate("date_action").toLocalDate());
            sousCategorie.setId_categorie(rs.getInt("id_categorie"));
            sousCategories.add(sousCategorie);
        }
        return sousCategories;
    }

    @Override
    public int save(SousCategorie t) {
        try{
            PreparedStatement st = conn.prepareStatement("INSERT INTO sous_categories (nom, lien, temps_action, date_action, id_categorie) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, t.getNom());
            st.setString(2, t.getLien());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId_categorie());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next())
            {
                return rs.getInt(1);
            }
        } catch(Exception e){
            return 0;
        }
        return 0;
    }

    @Override
    public int update(SousCategorie t) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE sous_categories SET nom=?, lien=?, temps_action=?, date_action=?, id_categorie=? WHERE id=?");
            st.setString(1, t.getNom());
            st.setString(2, t.getLien());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId_categorie());
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM sous_categories WHERE id=?");
            st.setInt(1, id);
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int compter() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT count(*) as compteur FROM sous_categories");
        if(rs.next()){
            return rs.getInt("compteur");
        }
        return 0;
    }
}
