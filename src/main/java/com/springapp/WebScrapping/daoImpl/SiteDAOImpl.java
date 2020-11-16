package com.springapp.WebScrapping.daoImpl;

import com.springapp.WebScrapping.dao.SiteDAO;
import com.springapp.WebScrapping.models.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SiteDAOImpl implements SiteDAO {
    @Autowired
    private Connection conn;

    public SiteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Site get(Integer id) throws SQLException {
        Site site = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sites WHERE id="+id);
        if(rs.next()) {
            site = new Site();
            site.setId(rs.getInt("id"));
            site.setLien(rs.getString("lien"));
            site.setNom(rs.getString("nom"));
            site.setTemps_action(rs.getTime("temps_action").toLocalTime());
            site.setDate_action(rs.getDate("date_action").toLocalDate());
        }
        return site;
    }

    @Override
    public Site getLast() throws SQLException {
        Site site = null;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sites ORDER BY id DESC LIMIT 1");
        if(rs.next()) {
            site = new Site();
            site.setId(rs.getInt("id"));
            site.setLien(rs.getString("lien"));
            site.setNom(rs.getString("nom"));
            site.setTemps_action(rs.getTime("temps_action").toLocalTime());
            site.setDate_action(rs.getDate("date_action").toLocalDate());
        }
        return site;
    }

    @Override
    public List<Site> getAll() throws SQLException {
        List<Site> sites = new ArrayList<Site>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM sites");
        while (rs.next()) {
            Site site = new Site();
            site.setId(rs.getInt("id"));
            site.setLien(rs.getString("lien"));
            site.setNom(rs.getString("nom"));
            site.setTemps_action(rs.getTime("temps_action").toLocalTime());
            site.setDate_action(rs.getDate("date_action").toLocalDate());
            sites.add(site);
        }
        return sites;
    }

    @Override
    public Site getOneBy(String nom, String lien) throws  SQLException{
        Site site = null;
        Statement st = conn.createStatement();
        String site_nom = null;
        if(nom == null){
            if(lien != null ) {
                String pattern = "(((\\w*)://)?(\\w*\\.)?)(.*)((\\.\\w*))(/.*)?";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(lien);
                if (m.find()) {
                    site_nom = m.group(5).substring(0, 1).toUpperCase() + m.group(5).substring(1).toLowerCase();
                } else {
                    return null;
                }
            }else{
                return null;
            }
        }else{
            site_nom = nom.substring(0, 1).toUpperCase() + nom.substring(1);
        }
        ResultSet rs = st.executeQuery("SELECT * FROM sites WHERE nom='"+site_nom+"'");
        if(rs.next()) {
            site = new Site();
            site.setId(rs.getInt("id"));
            site.setLien(rs.getString("lien"));
            site.setNom(rs.getString("nom"));
            site.setTemps_action(rs.getTime("temps_action").toLocalTime());
            site.setDate_action(rs.getDate("date_action").toLocalDate());
        }
        return site;
    }

    @Override
    public int save(Site t) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO sites (lien, nom, temps_action, date_action) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, t.getLien());
            st.setString(2, t.getNom());
            st.setTime(3, Time.valueOf(t.getTemps_action()));
            st.setDate(4, Date.valueOf(t.getDate_action()));
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
    public int update(Site t) throws SQLException {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE sites SET lien=?, nom=?, temps_action=?, date_action=? WHERE id=?");
            st.setString(1, t.getLien());
            st.setString(2, t.getNom());
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
            PreparedStatement st = conn.prepareStatement("DELETE FROM sites WHERE id=?");
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
        ResultSet rs = st.executeQuery("SELECT count(*) as compteur FROM sites");
        if(rs.next()){
            return rs.getInt("compteur");
        }
        return 0;
    }
}
