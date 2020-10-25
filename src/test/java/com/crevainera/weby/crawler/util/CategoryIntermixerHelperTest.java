package com.crevainera.weby.crawler.util;

import com.crevainera.weby.crawler.entities.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for {@link CategoryIntermixerHelper}
 */
class CategoryIntermixerHelperTest {

    @Test
    void whenMixCategoryListIsProvidedShouldGetCategoriesIntermingledPerSite() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(getCategoryStub(2));
        categoryList.add(getCategoryStub(5));
        categoryList.add(getCategoryStub(5));
        categoryList.add(getCategoryStub(2));
        categoryList.add(getCategoryStub(4));
        categoryList.add(getCategoryStub(2));
        categoryList.add(getCategoryStub(9));
        categoryList.add(getCategoryStub(4));

        List<Category> categoryResults = CategoryIntermixerHelper.getCategoriesIntermingledPerSite(categoryList);

        assertNotNull(categoryResults);
        assertEquals(8, categoryList.size());
        assertEquals(2L, categoryResults.get(0).getSiteId());
        assertEquals(4L, categoryResults.get(1).getSiteId());
        assertEquals(5L, categoryResults.get(2).getSiteId());
        assertEquals(9L, categoryResults.get(3).getSiteId());
        assertEquals(2L, categoryResults.get(4).getSiteId());
        assertEquals(4L, categoryResults.get(5).getSiteId());
        assertEquals(5L, categoryResults.get(6).getSiteId());
        assertEquals(2L, categoryResults.get(7).getSiteId());
    }

    private Category getCategoryStub(final long siteId) {
        Category category = new Category();
        category.setSiteId(siteId);

        return category;
    }
}