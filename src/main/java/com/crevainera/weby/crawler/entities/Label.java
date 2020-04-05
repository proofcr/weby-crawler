package com.crevainera.weby.crawler.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "label_id")
    private Set<Category> categoryList = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
