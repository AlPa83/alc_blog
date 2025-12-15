package com.blog.alc.service;

import com.blog.alc.model.Article;
import com.blog.alc.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    //  Récupère tous les articles
    public List<Article> getAll() {
        return repository.findAll();
    }

    //  Récupère une page d'articles (pour pagination: 100 par page)
    public Page<Article> getPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    //  Trouver par ID
    public Article getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    //  Ajouter un article
    public Article add(Article article) {
        return repository.save(article);
    }

    //  Supprimer
    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    //  Mettre à jour
    public Article update(Long id, Article updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitre(updated.getTitre());
                    existing.setAuteur(updated.getAuteur());
                    existing.setContenu(updated.getContenu());
                    return repository.save(existing);
                })
                .orElse(null);
    }

    //  Compter
    public long count() {
        return repository.count();
    }

    //  Recherche simple par auteur
    public List<Article> searchByAuteur(String auteur) {
        return repository.findAll().stream()
                .filter(a -> a.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .toList();
    }
}
