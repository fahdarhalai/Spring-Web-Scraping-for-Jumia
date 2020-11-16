package com.springapp.WebScrapping.daoImpl;

import com.springapp.WebScrapping.dao.PrixDAO;
import com.springapp.WebScrapping.models.Prix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PrixDAOImpl implements PrixDAO{

    @Autowired
    private Connection conn;

    public PrixDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Prix get(Integer id) throws SQLException {
        Prix prix = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM prix WHERE id="+id);
        if(rs.next()) {
            prix = new Prix();
            prix.setId(rs.getInt("id"));
            prix.setPrix_produit(rs.getFloat("prix_produit"));
            prix.setDevise(rs.getString("devise"));
            prix.setTemps_action(rs.getTime("temps_action").toLocalTime());
            prix.setDate_action(rs.getDate("date_action").toLocalDate());
            prix.setId_produit(rs.getInt("id_produit"));
        }
        return prix;
    }

    @Override
    public Prix getLast(Integer id_produit) throws SQLException {
        Prix prix = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM prix WHERE id_produit="+id_produit);
        if(rs.next()) {
            prix = new Prix();
            prix.setId(rs.getInt("id"));
            prix.setPrix_produit(rs.getFloat("prix_produit"));
            prix.setDevise(rs.getString("devise"));
            prix.setTemps_action(rs.getTime("temps_action").toLocalTime());
            prix.setDate_action(rs.getDate("date_action").toLocalDate());
            prix.setId_produit(rs.getInt("id_produit"));
        }
        return prix;
    }

    @Override
    public List<Prix> getAll() throws SQLException {
        List<Prix> prix = new ArrayList<Prix>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM prix");
        while (rs.next()) {
            Prix p = new Prix();
            p.setId(rs.getInt("id"));
            p.setPrix_produit(rs.getFloat("prix_produit"));
            p.setDevise(rs.getString("devise"));
            p.setTemps_action(rs.getTime("temps_action").toLocalTime());
            p.setDate_action(rs.getDate("date_action").toLocalDate());
            p.setId_produit(rs.getInt("id_produit"));
        }
        return prix;
    }

    @Override
    public List<Prix> getAll(Integer id_produit, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2, Integer compteur) throws SQLException {
        List<Prix> prix = new ArrayList<Prix>();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM prix where id_produit = " + id_produit;
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
        if(compteur!=null){
            sql += " LIMIT " + compteur;
        }
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Prix p = new Prix();
            p.setId(rs.getInt("id"));
            p.setPrix_produit(rs.getFloat("prix_produit"));
            p.setDevise(rs.getString("devise"));
            p.setTemps_action(rs.getTime("temps_action").toLocalTime());
            p.setDate_action(rs.getDate("date_action").toLocalDate());
            p.setId_produit(rs.getInt("id_produit"));
            prix.add(p);
        }
        return prix;
    }

    @Override
    public int save(Prix t) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO prix (prix_produit, devise, temps_action, date_action, id_produit) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setFloat(1,t.getPrix_produit());
            st.setString(2, t.getDevise());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId_produit());
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
    public int update(Prix t) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE prix SET prix_produit=?, devise=?, temps_action=?, date_action=?, id_produit=? WHERE id=?");
            st.setFloat(1, t.getPrix_produit());
            st.setString(2, t.getDevise());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
            st.setInt(5, t.getId());
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int delete(Integer id) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM prix WHERE id=?");
            st.setInt(1, id);
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
