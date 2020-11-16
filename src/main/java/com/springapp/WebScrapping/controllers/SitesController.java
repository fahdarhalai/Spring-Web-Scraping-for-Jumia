package com.springapp.WebScrapping.controllers;

import com.springapp.WebScrapping.dao.CategorieDAO;
import com.springapp.WebScrapping.dao.SiteDAO;
import com.springapp.WebScrapping.jsoup.SiteScraper;
import com.springapp.WebScrapping.models.Categorie;
import com.springapp.WebScrapping.models.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
public class SitesController {

    @Autowired
    SiteScraper ss;
    @Autowired
    SiteDAO sd;
    @Autowired
    CategorieDAO cd;

    @RequestMapping(value = "/site/ajout", method = RequestMethod.POST)
    public ModelAndView ajouterSite(@RequestParam("lien_site")String lien, RedirectAttributes ra) throws SQLException {
        ModelAndView mv;
        Site site = ss.siteExtractInfo(lien);

        int id = sd.save(site);
        if(id != 0){
            mv = new ModelAndView("redirect:/categories");
            List<Categorie> categorieList = ss.scrapCategories(site);
            for (Categorie cat:categorieList) {
                System.out.println("test");
                cat.setId_site(id);
                cd.save(cat);
            }
            ra.addFlashAttribute("id_site", id);
        } else {
            mv = new ModelAndView("redirect:/admin");
            String nom = site.getNom();
            site = sd.getOneBy(nom, null);
            ra.addFlashAttribute("id_site", site.getId());
        }
        return mv;
    }

}