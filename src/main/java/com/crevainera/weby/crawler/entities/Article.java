package com.crevainera.weby.crawler.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String url;
    private String thumbUrl;
    @Lob
    private byte[] thumb;
    private String body;
    @Column(name = "scrap_date")
    private Date scrapDate;
    @Column(name = "site_id")
    private long siteId;

    @JoinTable(name = "ARTICLE_LABEL",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    @ManyToMany()
    private Set<Label> labelList = new HashSet<>();
}
