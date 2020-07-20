package com.crevainera.weby.crawler.services.image;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class ImageService {

    private ArticleRepository articleRepository;
    private ThumbService thumbService;

    @Autowired
    public ImageService(final ArticleRepository articleRepository, final ThumbService thumbService) {
        this.articleRepository = articleRepository;
        this.thumbService = thumbService;
    }

    public void process(final Long articleId) {
        if (articleId != null) {
            Article article = articleRepository.findById(articleId).get();
            try {
                article.setThumb(thumbService.resize(new URL(article.getThumbUrl())));
                articleRepository.save(article);
            } catch (WebyException | MalformedURLException e) {
                log.error(e.getMessage());
            }
        }
    }
}
