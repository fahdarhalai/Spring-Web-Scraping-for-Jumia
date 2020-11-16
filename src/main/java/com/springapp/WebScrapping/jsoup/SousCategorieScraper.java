package com.springapp.WebScrapping.jsoup;

import com.springapp.WebScrapping.models.Produit;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SousCategorieScraper {

    public List<Produit> scrapProduits(SousCategorie sousCategorie) {
        List<Produit> listProd = new ArrayList<Produit>();
        Elements produits;
        try {
            Document document = Jsoup.connect(sousCategorie.getLien()).get();
            Element more = document.selectFirst("footer > .more");
            if(more!=null){
                String lien=more.attr("href");
                String pattern = "((((\\w*)://)?(\\w*\\.)?)(.*)((\\.\\w*)))?(/.*)?";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(lien);
                if(m.find()){
                    if(m.group(1)==null){
                        String url = sousCategorie.getLien();
                        Matcher n = r.matcher(url);
                        if(n.find()){
                            lien = n.group(1)+lien;
                        }
                    }
                }
                document = Jsoup.connect(lien).get();
            }
            Element mainProduits = document.selectFirst("main section.-mabaya.products");
            produits = mainProduits.select("div.-gallery.sku");
            if (produits.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        for (Element produit : produits) {

            Produit prod = new Produit();
            LocalTime t = LocalTime.now();
            LocalDate d = LocalDate.now();

            Element x;
            if((x=produit.selectFirst(".link > .title > .name")) != null){
                prod.setTitre(x.text());
            }else{continue;}
            if((x=produit.selectFirst(".link")) != null){
                prod.setLien(x.attr("href"));
            }else{continue;}
            if((x=produit.selectFirst(".link > .title > .brand")) != null){
                prod.setMarque(x.text());
            }else{prod.setMarque("Unconnue");}
            if((x=produit.selectFirst(".link > .clearfix.price-container > .price-box > span.price:nth-of-type(1) > span:nth-of-type(1)")) != null){
                prod.setPrix_produit(Float.parseFloat(x.attr("data-price")));
            }else{continue;}
            if((x=produit.selectFirst(".link > .clearfix.price-container > .price-box > span.price:nth-of-type(1) > span:nth-of-type(2)")) != null){
                prod.setDevise(x.attr("data-currency-iso"));
            }else{continue;}
            if((x=produit.selectFirst(".link > .default-state.image-wrapper > noscript > img")) != null){
                prod.setImage(x.attr("src"));
            }else{continue;}
            if((x=produit.selectFirst(".link > .rating-stars > .stars-container > .stars")) != null){
                String eval = x.attr("style").split(":")[1].split("%")[0];
                prod.setEvaluation(Integer.parseInt(eval.replaceAll("\\s+","")));
            }else{prod.setEvaluation(0);}

            prod.setTemps_action(t);
            prod.setDate_action(d);
            listProd.add(prod);
        }

        return listProd;
    }
}
