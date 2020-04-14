package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findByUrl(String url);
    List<Article> findTop40ByOrderByScrapDateDesc();

    @Query("select a FROM Article a INNER JOIN a.labelList ll ON ll.id = :labelId ")
    List<Article> fetchByArticleInnerJoinLabelListId(@Param("labelId") Long labelId);
}
