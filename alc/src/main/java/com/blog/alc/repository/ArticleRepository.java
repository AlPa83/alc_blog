package com.blog.alc.repository;

import com.blog.alc.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuteurContainingIgnoreCase(String auteur);
}