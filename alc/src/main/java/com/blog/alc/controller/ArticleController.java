package com.blog.alc.controller;

import com.blog.alc.model.Article;
import com.blog.alc.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    // ---- ACCUEIL + RECHERCHE ----
    @GetMapping("/")
    public String index(@RequestParam(required = false) String auteur, Model model) {

        List<Article> list;

        if (auteur != null && !auteur.isEmpty()) {
            list = service.searchByAuteur(auteur);
        } else {
            list = service.getAll();
        }

        model.addAttribute("articles", list);
        model.addAttribute("count", list.size());
        model.addAttribute("auteur", auteur);

        return "index";
    }

    // ---- FORMULAIRE DE CREATION ----
    @GetMapping("/article/new")
    public String showCreateForm(Model model) {
        model.addAttribute("article", new Article());
        return "form";
    }

    // ---- FORMULAIRE DE MODIFICATION ----
    @GetMapping("/article/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Article article = service.getById(id);
        model.addAttribute("article", article);
        return "form";
    }

    // ---- AJOUT ----
    @PostMapping("/article/add")
    public String add(@ModelAttribute Article article) {
        service.add(article);
        return "redirect:/";
    }

    // ---- MISE À JOUR ----
    @PostMapping("/article/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Article article) {
        service.update(id, article);
        return "redirect:/";
    }

    // ---- DÉTAIL ----
    @GetMapping("/article/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Article article = service.getById(id);
        if (article == null) return "redirect:/";
        model.addAttribute("article", article);
        return "detail";
    }

    // ---- SUPPRESSION ----
    @GetMapping("/article/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
