package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
