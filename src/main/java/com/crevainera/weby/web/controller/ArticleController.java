package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Article;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.web.constant.ImageFormatMediaTypeMap;
import com.crevainera.weby.web.service.ArticleService;
import com.crevainera.weby.web.service.ImageResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.crevainera.weby.web.constant.ImageFormatEnum.PNG;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("articles")
@Slf4j
public class ArticleController {

    private ArticleService articleService;
    private ImageResourceService imageResourceService;

    @Autowired
    public ArticleController(final ArticleService articleService, final ImageResourceService imageResourceService) {
        this.articleService = articleService;
        this.imageResourceService = imageResourceService;
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Article getArticle(@PathVariable(name = "id", required = true) long id) throws WebyException {
        log.info("getArticle");
        return articleService.findById(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> getArticles(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) throws WebyException {
        log.info("getArticles");
        return articleService.findAll(pageNo, pageSize);
    }

    @GetMapping(value = "/label/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Article> findByLabelId(
            @PathVariable(name = "id", required = true) long id,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize) throws WebyException {
        log.info("findByLabelId");
        return articleService.findByLabelId(id, pageNo, pageSize);
    }

    @RequestMapping(value = "/thumb/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumb(@PathVariable(name = "id", required = true) long id) throws WebyException {
        log.info("getThumb/" + id);
        Article article = articleService.findById(id);

        byte[] imageThumb;
        MediaType imageThumbFormat;
        if (article.getThumb() != null) {
            imageThumbFormat = ImageFormatMediaTypeMap.get(FilenameUtils.getExtension(article.getUrl()));
            imageThumb = article.getThumb();
        } else {
            imageThumbFormat = ImageFormatMediaTypeMap.get(PNG.getName());
            imageThumb = imageResourceService.getDefaultNewsImage();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(imageThumbFormat);

        return new ResponseEntity<>(imageThumb, headers, HttpStatus.OK);
    }

}
