package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.entities.Label;
import com.crevainera.weby.crawler.entities.Site;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findByUrl(String url);
    Slice<Article> findAll(final Pageable pageable);
    Slice<Article> findBySiteAndLabelList(final Site site, final Label label, final Pageable pageable);

    @Query("select a FROM Article a INNER JOIN a.labelList ll ON ll.id = :labelId ")
    Slice<Article> fetchByArticleInnerJoinLabelListId(@Param("labelId") final Long labelId, final Pageable pageable);
}
