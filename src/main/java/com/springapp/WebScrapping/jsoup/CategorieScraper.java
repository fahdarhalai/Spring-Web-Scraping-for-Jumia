package com.springapp.WebScrapping.jsoup;

import com.springapp.WebScrapping.models.Categorie;
import com.springapp.WebScrapping.models.SousCategorie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategorieScraper {

    public List<SousCategorie> scrapSousCategories(Categorie categorie){
        List<SousCategorie> listSousCat = new ArrayList<SousCategorie>();
        Elements menu;

        try{
            Document doc = Jsoup.connect(categorie.getLien()).get();
            menu = doc.select(".osh-category-tree > .osh-subcategories > li.osh-subcategory > a");
        }catch (Exception e){
            return  null;
        }

        for(Element souscat:menu){
            SousCategorie sousCategorie = new SousCategorie();
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();

            Element x;
            if((x=souscat) != null){
                sousCategorie.setLien(x.attr("href"));
            }else{continue;}
            if((x=souscat.selectFirst(".-name")) != null) {
                sousCategorie.setNom(x.text());
            }else{continue;}

            sousCategorie.setTemps_action(t);
            sousCategorie.setDate_action(d);

            listSousCat.add(sousCategorie);
        }
        return listSousCat;
    }

}
