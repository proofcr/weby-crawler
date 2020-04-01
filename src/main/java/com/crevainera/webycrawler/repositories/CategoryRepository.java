package com.crevainera.webycrawler.repositories;

import com.crevainera.webycrawler.entities.Site;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Site, Long> {
}
