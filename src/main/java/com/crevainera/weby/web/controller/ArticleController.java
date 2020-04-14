package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.web.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("article")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Article getArticle(@PathVariable long id) throws WebyException {
        return articleService.findById(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> getArticles() throws WebyException {
        return articleService.findAll();
    }

    @GetMapping(value = "/label/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> fetchByArticleInnerJoinLabelLabelId(@PathVariable long id) throws WebyException {
        return articleService.findByLabelId(id);
    }
}
