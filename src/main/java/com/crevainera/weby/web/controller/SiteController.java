package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("sites")
@Slf4j
public class SiteController {
    private SiteRepository siteRepository;

    @Autowired
    public SiteController(final SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Site> getSites() throws WebyException {
        log.debug("getSites");
        return Lists.newArrayList(siteRepository.findAll());
    }
}
