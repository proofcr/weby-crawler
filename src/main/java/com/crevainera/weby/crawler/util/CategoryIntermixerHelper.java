package com.crevainera.weby.crawler.util;

import com.crevainera.weby.crawler.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Categories intermixer per site
 */
public class CategoryIntermixerHelper {

    private CategoryIntermixerHelper() {
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

        Long[] siteIdkeys = categoryMap.keySet().toArray(new Long[categoryMap.size()]);

        int[] counters = new int[siteIdkeys.length];
        for (int i = 0; i < counters.length; i++) {
            counters[i] =0;
        }

        List<Category> intermixerCategories = new ArrayList<>();
        int sitesAdded = 0;
        int counterIndex = 0;

        while (sitesAdded < siteIdkeys.length) {

            long siteKey = siteIdkeys[counterIndex];
            if (counters[counterIndex] == categoryMap.get(siteKey).size()) {
                sitesAdded++;
            } else {
                intermixerCategories.add(categoryMap.get(siteKey).get(counters[counterIndex]));
                counters[counterIndex] = counters[counterIndex] + 1;
            }
            counterIndex = (counterIndex == siteIdkeys.length -1) ? counterIndex = 0 : counterIndex + 1;
        }

        return intermixerCategories;
    }


}