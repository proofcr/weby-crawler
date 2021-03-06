package com.crevainera.weby.crawler.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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

    @Lob @JsonIgnore
    private byte[] thumb;

    @JsonIgnore
    private String body;

    @JsonIgnore
    @Column(name = "raw_body")
    private String rawBody;

    @Column(name = "scrap_date")
    private Date scrapDate;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private Site site;

    @JoinTable(name = "article_label",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Label> labelList = new HashSet<>();

    @JsonIgnore
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "article_id")
    private Set<Image> imageList = new HashSet<>();
}
