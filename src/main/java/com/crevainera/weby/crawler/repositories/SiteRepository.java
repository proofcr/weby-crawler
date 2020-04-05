package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Site;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteRepository extends CrudRepository<Site, Long> {
    List<Site> findByEnabledTrue();
}
