package com.crevainera.weby.crawler.util;

import com.crevainera.weby.crawler.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Categories intermixer per site
 */
public class CategoryIntermixer {

    private CategoryIntermixer() {
    }

    /**
     * Get categories intermingled per site
     *
     * @param categoryList {@link List<Category>}
     *
     * @return {@link List<Category>}
     */
    public static List<Category> getCategoriesIntermingledPerSite(final List<Category> categoryList) {
        Map<Long, List<Category>> categoryMap = categoryList.stream().collect(Collectors.groupingBy(Category::getSiteId));

        List<Stack<Category>> categoryStackList = new ArrayList<>();
        categoryMap.forEach((k, v) -> {
                Stack stack = new Stack();
                stack.addAll(v);
                categoryStackList.add(stack);
            });

        Integer siteTotal = categoryMap.keySet().size();
        List<Category> iterspersedCategories = new ArrayList<>();
        int sitesAdded = 0;
        int siteIndex = 0;
        while (sitesAdded < siteTotal) {

            if (categoryStackList.get(siteIndex).isEmpty()) {
                sitesAdded++;
            } else {
                iterspersedCategories.add(categoryStackList.get(siteIndex).pop());
            }

            siteIndex = (siteIndex == siteTotal-1) ? siteIndex = 0 : siteIndex+1;
        }

        return iterspersedCategories;
    }


}