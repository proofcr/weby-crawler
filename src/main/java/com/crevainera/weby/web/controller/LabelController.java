package com.crevainera.weby.web.controller;

import com.crevainera.weby.crawler.entities.Label;
import com.crevainera.weby.crawler.exception.WebyException;
import com.crevainera.weby.crawler.repositories.LabelRepository;
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
@RequestMapping("labels")
@Slf4j
public class LabelController {

    private LabelRepository labelRepository;

    @Autowired
    public LabelController(final LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Label> getLabels() throws WebyException {
        log.debug("getLabels");
        return Lists.newArrayList(labelRepository.findAll());
    }
}
