package com.crevainera.weby.crawler.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String url;
    @JsonIgnore
    private Boolean enabled;
    @Column(name = "scrap_thumb_enabled")
    @JsonIgnore
    private Boolean scrapThumbEnabled;

    @OneToMany(
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "site_id")
    @JsonIgnore
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "site_id")
    @JsonIgnore
    private List<Article> articleList = new ArrayList<>();
}
