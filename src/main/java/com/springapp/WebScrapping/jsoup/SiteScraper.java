package com.springapp.WebScrapping.jsoup;

import com.springapp.WebScrapping.models.Categorie;
import com.springapp.WebScrapping.models.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SiteScraper {

    public Site siteExtractInfo(String url) {
        try {
            System.out.println(url);
            Site site = new Site();
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();
            String pattern = "(((\\w*)://)?(\\w*\\.)?)(.*)((\\.\\w*))(/.*)?";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(url);
            String nom;
            if(m.find()){
                nom = m.group(5).substring(0,1).toUpperCase() + m.group(5).substring(1).toLowerCase();
            } else {
                return null;
            }
            site.setLien(url);
            site.setNom(nom);
            site.setTemps_action(t);
            site.setDate_action(d);
            return site;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Categorie> scrapCategories(Site site){
        List<Categorie> listCat = new ArrayList<Categorie>();
        Elements menu;
        try {
            Document document = Jsoup.connect(site.getLien()).get();
            menu = document.select(".flyout > a.itm");
            if(menu.isEmpty()){
                return null;
            }
        }catch (Exception e){
            return null;
        }
        menu.remove(0);
        menu.remove(0);
        menu.remove(8);

        for (Element elmt:menu) {
            Categorie categorie = new Categorie();
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();

            Element x;
            if((x=elmt.selectFirst("a.itm")) != null){
                String lien=x.attr("href");
                String pattern = "(((\\w*)://)?(\\w*\\.)?)((.*)((\\.\\w*)))?(/.*)";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(lien);
                if(m.find()){
                    if(m.group(6)!=null){
                        categorie.setLien(lien);
                    }else{
                        if(lien.startsWith("/")){
                            categorie.setLien(site.getLien()+lien);
                        }else{
                            categorie.setLien(site.getLien()+"/"+lien);
                        }
                    }
                }else {
                    continue;
                }
            }else{continue;}
            if((x=elmt.selectFirst("a.itm > .text")) != null) {
                categorie.setNom(x.text());
            }else{continue;}

            categorie.setTemps_action(t);
            categorie.setDate_action(d);

            listCat.add(categorie);
        }
        return listCat;
    }

}
