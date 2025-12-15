package com.blog.alc.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String auteur;
    private String contenu;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    public Article() {}

    public Article(Long id, String titre, String auteur, String contenu) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.contenu = contenu;
    }

    @PrePersist
    protected void onCreate() {
        this.dateCreation = LocalDateTime.now();
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}
