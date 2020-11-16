package com.springapp.WebScrapping.controllers;

import com.springapp.WebScrapping.dao.CategorieDAO;
import com.springapp.WebScrapping.dao.PrixDAO;
import com.springapp.WebScrapping.dao.ProduitDAO;
import com.springapp.WebScrapping.dao.SousCategorieDAO;
import com.springapp.WebScrapping.jsoup.SousCategorieScraper;
import com.springapp.WebScrapping.models.Prix;
import com.springapp.WebScrapping.models.Produit;
import com.springapp.WebScrapping.models.SousCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class SousCategoriesController {

    @Autowired
    SousCategorieDAO scd;
    @Autowired
    SousCategorieScraper scs;
    @Autowired
    ProduitDAO pd;
    @Autowired
    PrixDAO prd;
    @Autowired
    CategorieDAO cd;

    @RequestMapping(value = "/sous-categories", method = RequestMethod.GET)
    public ModelAndView categories(@ModelAttribute("id_cat") Integer id_cat) throws SQLException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("souscategories");

        List<SousCategorie> sousCategorieList = scd.getAll(id_cat,null,null,null,null);

        mv.addObject("souscatlist", sousCategorieList);
        return mv;
    }

    @RequestMapping(value = "/sous-categorie/produits", method = RequestMethod.POST)
    public ModelAndView ajouterProduits(@RequestParam("id_souscat")int id_souscat, RedirectAttributes ra) throws SQLException{
        ModelAndView mv = new ModelAndView("redirect:/admin");
        SousCategorie souscat = scd.get(id_souscat);
        List<Produit> produitList = scs.scrapProduits(souscat);

        for (Produit prod:produitList) {
            prod.setId_sous_categorie(id_souscat);
            int id_prod = pd.save(prod);
            if(id_prod != 0){
                Prix p = new Prix(prod.getPrix_produit(), prod.getDevise(), LocalTime.now(), LocalDate.now(), id_prod);
                prd.save(p);
            }
        }
        Integer id_site = cd.get(souscat.getId_categorie()).getId_site();
        ra.addFlashAttribute("id_site", id_site);
        return mv;
    }

}
