package com.crevainera.webycrawler.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String url;
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
