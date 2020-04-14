package com.crevainera.weby.web.service;

import com.crevainera.weby.crawler.constant.WebyConstant;
import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article findById(Long id) throws WebyException {
        try {
            return articleRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

    public List<Article> findAll() throws WebyException {
        try {
            return Lists.newArrayList(articleRepository.findAll());
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

    public List<Article> findByLabelId(Long labelId) throws WebyException {
        try {
            return Lists.newArrayList(articleRepository.fetchByArticleInnerJoinLabelListId(labelId));
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

}
