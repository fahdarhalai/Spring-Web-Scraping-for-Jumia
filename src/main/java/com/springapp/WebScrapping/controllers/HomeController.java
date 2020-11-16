package com.springapp.WebScrapping.controllers;

import com.springapp.WebScrapping.dao.*;
import com.springapp.WebScrapping.jsoup.CategorieScraper;
import com.springapp.WebScrapping.jsoup.SousCategorieScraper;
import com.springapp.WebScrapping.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    ProduitDAO pd;
    @Autowired
    PrixDAO prd;
    @Autowired
    CategorieDAO cd;
    @Autowired
    SiteDAO sd ;
    @Autowired
    CategorieScraper cs;
    @Autowired
    SousCategorieDAO scd;
    @Autowired
    SousCategorieScraper scs;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() throws SQLException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("welcome");

        return mv;
    }

    @RequestMapping(value = "/admin")
    public ModelAndView welcome(@ModelAttribute("id_site") Integer id_site) throws SQLException {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("index");

            Site site = sd.get(id_site);
            List<Categorie> categorieList = cd.getAll(site.getId(), null, null, null, null);
            List<Produit> produitList = pd.getAll();
            for (Produit prod : produitList) {
                Prix p = prd.getLast(prod.getId());
                prod.setPrix_produit(p.getPrix_produit());
                prod.setDevise(p.getDevise());
            }

            int c1 = sd.compter();
            int c2 = cd.compter();
            int c3 = scd.compter();
            int c4 = pd.compter();

            mv.addObject("catlist", categorieList);
            mv.addObject("prodlist", produitList);
            mv.addObject("sites", c1);
            mv.addObject("cats", c2);
            mv.addObject("souscats", c3);
            mv.addObject("prods", c4);
        }catch (Exception e){
            mv.setViewName("redirect:/");
        }

        return mv;
    }

    @RequestMapping(value = "/{id_cat}")
    public ModelAndView listerCategorie(@PathVariable(value = "id_cat") Integer id_cat) throws SQLException{
        ModelAndView mv = new ModelAndView();
        mv.setViewName("listerCategorie");

        Categorie cat = cd.get(id_cat);
        List<SousCategorie> sousCategorieList = scd.getAll(id_cat, null,null,null,null);
        if(sousCategorieList.isEmpty()){
            sousCategorieList.addAll(cs.scrapSousCategories(cat));
        }
        System.out.println(sousCategorieList);
        for (SousCategorie souscat:sousCategorieList){
            souscat.setId_categorie(id_cat);
            int id = scd.save(souscat);
            if(id == 0){
                id = souscat.getId();
            }
            List<Produit> produits = scs.scrapProduits(souscat);
            for (Produit produit:produits){
                produit.setId_sous_categorie(id);
                int id_prod = pd.save(produit);
                if(id_prod != 0){
                    Prix p = new Prix(produit.getPrix_produit(), produit.getDevise(), LocalTime.now(), LocalDate.now(), id_prod);
                    prd.save(p);
                }
            }
        }

        List<Categorie> categorieList = cd.getAll();
        List<SousCategorie> sousCategorieList1 = scd.getAll(id_cat,null,null,null,null);
        List<Produit> listProduits = new ArrayList<>();

        for(SousCategorie souscat:sousCategorieList1){
            List<Produit> list = pd.getAll(souscat.getId(),null,null,null,null,null,null,null, null);
            listProduits.addAll(list);
        }

        for (Produit prod : listProduits) {
            Prix p = prd.getLast(prod.getId());
            prod.setPrix_produit(p.getPrix_produit());
            prod.setDevise(p.getDevise());
            System.out.println(prod);
        }

        int c1 = sd.compter();
        int c2 = cd.compter();
        int c3 = scd.compter();
        int c4 = pd.compter();

        mv.addObject("catlist", categorieList);
        mv.addObject("souscatlist", sousCategorieList1);
        mv.addObject("prodlist", listProduits);
        mv.addObject("sites", c1);
        mv.addObject("cats", c2);
        mv.addObject("souscats", c3);
        mv.addObject("prods", c4);

        return mv;
    }
}
