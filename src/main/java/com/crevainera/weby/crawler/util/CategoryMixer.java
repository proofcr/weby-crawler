package com.crevainera.weby.crawler.util;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Mix categories per site so category crawler can be called equitably per site
 */
public class CategoryMixer {

    private List<Stack<Category>> siteCategoryList;
    private List<Category> categoryMixedList;
    private List<Site> siteList;
    private int nextIndex;

    public CategoryMixer(final List<Site> siteList) {
        this.siteList = siteList;
        siteCategoryList = new ArrayList<>();
        categoryMixedList = new ArrayList<>();
        nextIndex = 0;
    }

    public List<Category> getCategoriesMixedEquitablyPerSite() {
        if (categoryMixedList.isEmpty()) {
            siteList.forEach(site -> addSiteCategories(site.getCategoryList()));

            while (isPoolEmpty()) {
                if (!isEmptyPoolPerCurrentSite()) {
                    categoryMixedList.add(getCurrentCallableStack().pop());
                }
                nextIndex();
            }
        }

        return categoryMixedList;
    }

    private void addSiteCategories(final List<Category> categoryList) {
        Stack<Category> categoryStack = new Stack<>();
        categoryStack.addAll(categoryList);
        siteCategoryList.add(categoryStack);
    }

    private int nextIndex() {
        if (nextIndex < siteCategoryList.size()-1) {
            nextIndex++;
        } else {
            nextIndex = 0;
        }

        return nextIndex;
    }

    private boolean isEmptyPoolPerCurrentSite() {
        return siteCategoryList.get(getCurrentIndex()).empty();
    }

    private Stack<Category> getCurrentCallableStack() {
        return siteCategoryList.get(getCurrentIndex());
    }

    private int getCurrentIndex() {
        return nextIndex;
    }

    private boolean isPoolEmpty() {
        boolean isEmptyList = false;
        for (int i=0 ; i < siteCategoryList.size(); i++) {
            if (!siteCategoryList.get(i).empty()) {
                isEmptyList = true;
            }
        }
        return isEmptyList;
    }
}