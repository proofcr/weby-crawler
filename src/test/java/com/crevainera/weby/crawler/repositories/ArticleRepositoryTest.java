package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Label;
import com.crevainera.weby.crawler.entities.Site;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("dev")
class ArticleRepositoryTest {

    @Autowired SiteRepository siteRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void whenSetLabelShouldGetLabel() {
        final Long labelId = 1L;
        final Long articleId = 1L;
        final Long siteId = 1L;
        final String title = "Proof";
        final String url = "http://proof.com";

        final Optional<Label> labelOptional = labelRepository.findById(labelId);
        final Optional<Site> siteOptional = siteRepository.findById(siteId);
        Article article = new Article();
        article.setId(articleId);
        article.setTitle(title);
        article.setUrl(url);
        article.setScrapDate(new Date());
        article.setLabelList(new HashSet<Label>(Arrays.asList(labelOptional.get())));
        article.setSite(siteOptional.get());

        articleRepository.save(article);

        Article returnedArticle = articleRepository.findById(articleId).get();
        assertEquals(title, returnedArticle.getTitle());

    }
}