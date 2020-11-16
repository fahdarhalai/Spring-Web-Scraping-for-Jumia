package com.springapp.WebScrapping.daoImpl;

import com.springapp.WebScrapping.dao.ProduitDAO;
import com.springapp.WebScrapping.models.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProduitDAOImpl implements ProduitDAO {

    @Autowired
    private Connection conn;

    public ProduitDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Produit get(Integer id) throws SQLException {
        Produit produit = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM produits WHERE id="+id);
        if(rs.next()) {
            produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setTitre(rs.getString("titre"));
            produit.setLien(rs.getString("lien"));
            produit.setImage(rs.getString("image"));
            produit.setMarque(rs.getString("marque"));
            produit.setTemps_action(rs.getTime("temps_action").toLocalTime());
            produit.setDate_action(rs.getDate("date_action").toLocalDate());
            produit.setId_sous_categorie(rs.getInt("id_sous_categorie"));
        }
        return produit;
    }

    @Override
    public List<Produit> getAll() throws SQLException {
        List<Produit> produits = new ArrayList<Produit>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM produits");
        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setTitre(rs.getString("titre"));
            produit.setLien(rs.getString("lien"));
            produit.setImage(rs.getString("image"));
            produit.setMarque(rs.getString("marque"));
            produit.setEvaluation(rs.getInt("evaluation"));
            produit.setTemps_action(rs.getTime("temps_action").toLocalTime());
            produit.setDate_action(rs.getDate("date_action").toLocalDate());
            produit.setId_sous_categorie(rs.getInt("id_sous_categorie"));
            produits.add(produit);
        }
        return produits;
    }

    @Override
    public List<Produit> getAll(Integer id_sousCategorie, String marque, Integer minEval, Integer maxEval, LocalTime t1, LocalTime t2, LocalDate d1, LocalDate d2, Integer compteur) throws SQLException {
        List<Produit> produits = new ArrayList<Produit>();
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM produits WHERE id_sous_categorie = " + id_sousCategorie;
        if(marque!=null){
            sql += " AND marque = " + marque;
        }
        if(minEval==null){
            if(maxEval!=null){
                sql += " AND evaluation <= " + maxEval;
            }
        }else{
            if(maxEval==null){
                sql += " AND evaluation >= " + minEval;
            } else{
                if(minEval==maxEval){
                    sql += " AND evaluation = " + minEval;
                }else {
                    sql += " AND evaluation BETWEEN " + minEval + " AND " + maxEval;
                }
            }
        }
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
            Produit produit = new Produit();
            produit.setId(rs.getInt("id"));
            produit.setTitre(rs.getString("titre"));
            produit.setLien(rs.getString("lien"));
            produit.setImage(rs.getString("image"));
            produit.setMarque(rs.getString("marque"));
            produit.setEvaluation(rs.getInt("evaluation"));
            produit.setTemps_action(rs.getTime("temps_action").toLocalTime());
            produit.setDate_action(rs.getDate("date_action").toLocalDate());
            produit.setId_sous_categorie(rs.getInt("id_sous_categorie"));
            produits.add(produit);
        }
        return produits;
    }

    @Override
    public int save(Produit t) {
        try{
            PreparedStatement st = conn.prepareStatement("INSERT INTO produits (titre, lien, image, marque, evaluation, temps_action, date_action, id_sous_categorie) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, t.getTitre());
            st.setString(2, t.getLien());
            st.setString(3, t.getImage());
            st.setString(4, t.getMarque());
            st.setFloat(5, t.getEvaluation());
            st.setTime(6, Time.valueOf(t.getTemps_action()));
            st.setDate(7, Date.valueOf(t.getDate_action()));
            st.setInt(8, t.getId_sous_categorie());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next())
            {
                return rs.getInt(1);
            }
            return 0;
        } catch(Exception e){
            return 0;
        }
    }

    @Override
    public int update(Produit t) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE produits SET titre, lien, image, marque, evaluation, temps_action, date_action, id_sous_categorie VALUES (?,?,?,?,?,?,?,?)");
            st.setString(1, t.getTitre());
            st.setString(2, t.getLien());
            st.setString(3, t.getImage());
            st.setString(4, t.getMarque());
            st.setFloat(5, t.getEvaluation());
            st.setTime(6, Time.valueOf(t.getTemps_action()));
            st.setDate(7, Date.valueOf(t.getDate_action()));
            st.setInt(8, t.getId_sous_categorie());
            st.executeUpdate();
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int delete(Integer id) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM produits WHERE id=?");
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
        ResultSet rs = st.executeQuery("SELECT count(*) as compteur FROM produits");
        if(rs.next()){
            return rs.getInt("compteur");
        }
        return 0;
    }
}
