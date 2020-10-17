package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site, Long> {
    Optional<List<Site>> findByEnabledTrue();
}
