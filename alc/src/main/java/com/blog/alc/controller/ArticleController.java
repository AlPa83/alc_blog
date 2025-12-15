package com.blog.alc.controller;

import com.blog.alc.model.Article;
import com.blog.alc.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {

    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) String auteur,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            Model model) {

        Page<Article> articlesPage;

        //  Recherche par auteur
        if (auteur != null && !auteur.isEmpty()) {
            // Recherche JPA
            var list = service.searchByAuteur(auteur);

            // Pagination "manuelle" pour la recherche uniquement
            int start = Math.min(page * size, list.size());
            int end = Math.min(start + size, list.size());
            var subList = list.subList(start, end);

            model.addAttribute("articles", subList);
            model.addAttribute("count", list.size());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) list.size() / size));
            model.addAttribute("auteur", auteur);
            model.addAttribute("size", size);

            return "index";
        }

        // Pagination via Spring Data JPA
        articlesPage = service.getPage(page, size);

        model.addAttribute("articles", articlesPage.getContent());
        model.addAttribute("count", articlesPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlesPage.getTotalPages());
        model.addAttribute("size", size);
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
