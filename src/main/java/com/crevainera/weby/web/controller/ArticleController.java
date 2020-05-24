package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.web.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("article")
@Slf4j
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Article getArticle(@PathVariable(name = "id", required = true) long id) throws WebyException {
        log.info("getArticle");
        return articleService.findById(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> getArticles(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @RequestParam(defaultValue = "id") String sortBy) throws WebyException {
        log.info("getArticles");
        return articleService.findAll(pageNo, pageSize, sortBy);
    }

    @GetMapping(value = "/label/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> findByLabelId(
            @PathVariable(name = "id", required = true) long id,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) throws WebyException {
        log.info("findByLabelId");
        return articleService.findByLabelId(id, pageNo, pageSize, sortBy);
    }

    @RequestMapping(value = "/thumb/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[] getThumb(@PathVariable(name = "id", required = true) long id) throws WebyException {
        log.info("getThumb");
        return articleService.findById(id).getThumb();
    }
}
