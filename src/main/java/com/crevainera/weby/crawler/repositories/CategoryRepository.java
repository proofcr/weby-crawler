package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
