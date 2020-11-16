package com.springapp.WebScrapping.daoImpl;

import com.springapp.WebScrapping.dao.CategorieDAO;
import com.springapp.WebScrapping.models.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategorieDAOImpl implements CategorieDAO {

    @Autowired
    private Connection conn;

    public CategorieDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Categorie get(Integer id) throws SQLException {
        Categorie categorie = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM categories WHERE id="+id);
        if(rs.next()) {
            categorie = new Categorie();
            categorie.setId(rs.getInt("id"));
            categorie.setLien(rs.getString("lien"));
            categorie.setNom(rs.getString("nom"));
            categorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            categorie.setDate_action(rs.getDate("date_action").toLocalDate());
            categorie.setId_site(rs.getInt("id_site"));
        }
        return categorie;
    }

    @Override
    public List<Categorie> getAll() throws SQLException {
        List<Categorie> categories = new ArrayList<Categorie>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM categories");
        while(rs.next()) {
            Categorie categorie = new Categorie();
            categorie.setId(rs.getInt("id"));
            categorie.setLien(rs.getString("lien"));
            categorie.setNom(rs.getString("nom"));
            categorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            categorie.setDate_action(rs.getDate("date_action").toLocalDate());
            categorie.setId_site(rs.getInt("id_site"));
            categories.add(categorie);
        }
        return categories;
    }

    @Override
    public List<Categorie> getAll(Integer id_site, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2) throws SQLException {
        List<Categorie> categories = new ArrayList<Categorie>();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM categories WHERE id_site = " + id_site;
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
        while(rs.next()) {
            Categorie categorie = new Categorie();
            categorie.setId(rs.getInt("id"));
            categorie.setLien(rs.getString("lien"));
            categorie.setNom(rs.getString("nom"));
            categorie.setTemps_action(rs.getTime("temps_action").toLocalTime());
            categorie.setDate_action(rs.getDate("date_action").toLocalDate());
            categorie.setId_site(rs.getInt("id_site"));
            categories.add(categorie);
        }
        return categories;
    }

    @Override
    public int save(Categorie t) {
        try{
            PreparedStatement st = conn.prepareStatement("INSERT INTO categories (nom, lien, temps_action, date_action, id_site) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, t.getNom());
            st.setString(2, t.getLien());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId_site());
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
    public int update(Categorie t) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE categories SET nom=?, lien=?, temps_action=?, date_action=?, id_site=? WHERE id=?");
            st.setString(1, t.getNom());
            st.setString(2, t.getLien());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId_site());
            st.setInt(6, t.getId());
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM categories WHERE id=?");
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
        ResultSet rs = st.executeQuery("SELECT count(*) as compteur FROM categories");
        if(rs.next()){
            return rs.getInt("compteur");
        }
        return 0;
    }
}
