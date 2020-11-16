package com.springapp.WebScrapping.controllers;

import com.springapp.WebScrapping.dao.CategorieDAO;
import com.springapp.WebScrapping.dao.SiteDAO;
import com.springapp.WebScrapping.dao.SousCategorieDAO;
import com.springapp.WebScrapping.jsoup.CategorieScraper;
import com.springapp.WebScrapping.models.Categorie;
import com.springapp.WebScrapping.models.Site;
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
import java.util.List;

@Controller
public class CategoriesController {

    @Autowired
    SiteDAO sd;
    @Autowired
    CategorieDAO cd;
    @Autowired
    SousCategorieDAO scd;
    @Autowired
    CategorieScraper cs;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ModelAndView categories(@ModelAttribute("id_site") Integer id_site) throws SQLException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("categories");

        Site site = sd.get(id_site);
        List<Categorie> catlist = cd.getAll(site.getId(),null,null,null,null);

        mv.addObject("catlist", catlist);
        return mv;
    }

    @RequestMapping(value = "/categorie/sous-categorie", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView ajouterSousCategories(@RequestParam("id_cat")int id_cat, RedirectAttributes ra) throws SQLException {
        ModelAndView mv;
        List<SousCategorie> sousCategorieList = scd.getAll(id_cat, null, null, null, null);

        if(sousCategorieList.isEmpty()){
            mv = new ModelAndView("redirect:/sous-categories");
            Categorie cat = cd.get(id_cat);
            sousCategorieList = cs.scrapSousCategories(cat);
            for (SousCategorie souscat:sousCategorieList) {
                souscat.setId_categorie(id_cat);
                scd.save(souscat);
            }
            ra.addFlashAttribute("id_cat", cat.getId());
        }else{
            mv = new ModelAndView("redirect:/admin");
        }

        return mv;
    }

}
