package com.crevainera.weby.crawler.entities;


import com.crevainera.weby.crawler.repositories.ArticleRepository;
import com.crevainera.weby.crawler.repositories.LabelRepository;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ArticleTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private SiteRepository siteRepository;

    @Test
    public void testGet() {
        Optional<Article> articleReturned = articleRepository.findById(1L);

        assertThat(articleReturned.get().getTitle(), is(equalTo("titulo1")));
    }

    @Test
    public void testSave() {

//        Site site = siteRepository.findById(1L).get();
//        Category category = site.getCategoryList().get(0);
//
//        Article article = new Article();
//        article.setTitle("titulo1");
//        article.setUrl("http://proof/page.html");
//        article.setSite_id(site.getId());
//
//        Set<Label> labels = new HashSet<>();
//        labels.add(labelRepository.findById(category.getLabel_id()).get());
//        article.setLabelList(labels);
//
//        articleRepository.save(article);
//
//        Optional<Article> articleReturned = articleRepository.findById(1L);
//
//        assertThat(articleReturned.get().getTitle(), is(equalTo("titulo1")));
    }
}