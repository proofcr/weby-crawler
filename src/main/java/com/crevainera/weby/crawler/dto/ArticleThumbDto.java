package com.crevainera.weby.crawler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleThumbDto {
    private long articleId;
    private String thumbUrl;
}
