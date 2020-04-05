package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findByUrl(String url);
}
