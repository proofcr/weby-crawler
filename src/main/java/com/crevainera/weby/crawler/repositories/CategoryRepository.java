package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Site;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Site, Long> {
}
