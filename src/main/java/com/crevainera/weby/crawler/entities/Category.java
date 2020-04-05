package com.crevainera.weby.crawler.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String url;
    private Boolean enabled;
    @Column(name = "site_id")
    private long siteId;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private ScrapRule scrapRule;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private Label label;
}
