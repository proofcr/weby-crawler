package com.crevainera.webycrawler.repositories;

import com.crevainera.webycrawler.entities.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findByUrl(String url);
}
