package com.crevainera.weby.web.service;

import com.crevainera.weby.crawler.constant.WebyConstant;
import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private static final String ID_FIELD = "id";

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article findById(final Long id) throws WebyException {
        try {
            return articleRepository.findById(id).get();
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

    public List<Article> findAll(final Integer pageNo, final Integer pageSize) throws WebyException {
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(ID_FIELD).descending());

            Slice<Article> pagedResult = articleRepository.findAll(paging);

            if(pagedResult.hasContent()) {
                return pagedResult.getContent();
            } else {
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

    public List<Article> findByLabelId(final Long labelId, final Integer pageNo,
                                       final Integer pageSize) throws WebyException {
        try {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(ID_FIELD).descending());

            Slice<Article> pagedResult = articleRepository.fetchByArticleInnerJoinLabelListId(labelId, paging);

            if(pagedResult.hasContent()) {
                return pagedResult.getContent();
            } else {
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new WebyException(WebyConstant.ARTICLE_SERVICE_EXCEPTION.getMessage());
        }
    }

}
