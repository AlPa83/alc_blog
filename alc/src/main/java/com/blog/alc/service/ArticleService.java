package com.blog.alc.service;

import com.blog.alc.model.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final List<Article> articles = new ArrayList<>();
    private long nextId = 1;


    public List<Article> getAll() {

        if (articles.isEmpty()) {
            articles.add(new Article(nextId++, "Premier article", "Alice", "Contenu du premier article."));
            articles.add(new Article(nextId++, "Actualité", "Bob", "Aujourd’hui on parle de Spring Boot."));
            articles.add(new Article(nextId++, "Annonce", "Admin", "Voici un message important."));
        }

        return articles;
    }

    public Article getById(Long id) {
        return articles.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void add(Article article) {
        article.setId(nextId++);
        articles.add(article);
    }

    public boolean delete(Long id) {
        return articles.removeIf(a -> a.getId().equals(id));
    }

    public boolean update(Long id, Article updated) {
        Article existing = getById(id);
        if (existing == null) return false;

        existing.setTitre(updated.getTitre());
        existing.setAuteur(updated.getAuteur());
        existing.setContenu(updated.getContenu());

        return true;
    }

    public int count() {
        return articles.size();
    }

    public List<Article> searchByAuteur(String auteur) {
        return articles.stream()
                .filter(a -> a.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .toList();
    }
}
