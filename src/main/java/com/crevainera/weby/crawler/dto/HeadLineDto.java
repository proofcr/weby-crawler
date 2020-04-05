package com.crevainera.weby.crawler.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HeadLineDto {
    private long id;
    private String title;
    private String description;
    private String url;
    private String thumbUrl;
}
