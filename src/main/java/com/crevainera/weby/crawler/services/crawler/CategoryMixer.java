package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Mix categories per site so category crawler can be called equitably per site
 */
public class CategoryMixer {

    private static List<Stack<Category>> siteCategoryList;
    private static int nextIndex;

    public CategoryMixer() {
        siteCategoryList = new ArrayList<>();
        nextIndex = 0;
    }

    public void addSiteCategories(final List<Category> categoryList) {
        Stack<Category> categoryStack = new Stack<>();
        categoryStack.addAll(categoryList);
        siteCategoryList.add(categoryStack);
    }

    public List<Category> getCategoriesMixedEquitablyPerSite() {
        List<Category> categoryMixedList = new ArrayList<>();
        while (isPoolEmpty()) {
            if (!isEmptyPoolPerCurrentSite()) {
                categoryMixedList.add(getCurrentCallableStack().pop());
            }
            nextIndex();
        }
        return categoryMixedList;
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