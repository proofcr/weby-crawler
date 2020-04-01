package com.crevainera.webycrawler.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table
public class ScrapRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String headline;
    private String title;
    private String link;
    private String image;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "scrap_rule_id")
    private Set<Category> categoryList = new HashSet<>();
}
