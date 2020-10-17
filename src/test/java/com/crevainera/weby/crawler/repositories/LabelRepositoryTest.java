package com.crevainera.weby.crawler.repositories;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("dev")
class LabelRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Test
    void whenSetLabelShouldGetLabel() {
        final Long id = 1L;
        final String title = "Proof";
        final Optional<Category> categoryOptional = categoryRepository.findById(id);
        Label label = new Label();
        label.setId(id);
        label.setTitle(title);
        label.setCategoryList(new HashSet<Category>(Arrays.asList(categoryOptional.get())));

        labelRepository.save(label);

        Label returnedLabel = labelRepository.findById(id).get();
        assertEquals(title, returnedLabel.getTitle());
        assertEquals(categoryOptional.get().getUrl(), returnedLabel.getCategoryList().stream().findAny().get().getUrl());
    }

}