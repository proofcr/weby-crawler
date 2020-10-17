package com.crevainera.weby.crawler.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String url;

    @Lob
    @JsonIgnore
    private byte[] thumb;
    @Lob
    @JsonIgnore
    private byte[] image;

    @Column(name = "scrap_date")
    private Date scrapDate;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private Article article;
}
