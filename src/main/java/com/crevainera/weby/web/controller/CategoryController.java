package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.CategoryRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Category> getCategories() throws WebyException {
        return Lists.newArrayList(categoryRepository.findAll());
    }
}
