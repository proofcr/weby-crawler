package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.repositories.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("articles")
public class ArticleController {

    private ArticleRepository articleRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public @ResponseBody Article getArticle(@PathVariable long id) {
        return articleRepository.findById(id).get();
    }

}
