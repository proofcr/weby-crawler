package com.crevainera.weby.crawler.services.crawler;

import com.crevainera.weby.crawler.entities.Category;
import com.crevainera.weby.crawler.entities.Site;
import com.crevainera.weby.crawler.repositories.SiteRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link SiteCrawler}
 */
class SiteCrawlerTest {

    public static final int THREADS_POOL_SIZE_THREE = 3;

    private SiteCrawler siteCrawler;
    private ExecutorService executorService;

    @Mock
    private SiteRepository siteRepository;

    @Mock
    private CategoryCrawler categoryCrawler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        executorService = Executors.newFixedThreadPool(THREADS_POOL_SIZE_THREE);

        siteCrawler = new SiteCrawler(siteRepository, executorService, categoryCrawler);
    }

    @Test
    void whenSiteRepositoriesReturnSitesWithCategoriesShouldCrawlAllReturnedCategories() {
        final int expectedCategoryCrawlerCallsThree = 3;
        final Site site1 = createSiteStub("site1", Lists.list(createCategoryStub(),  createCategoryStub()));
        final Site site2 = createSiteStub("site2", Lists.list(createCategoryStub()));

        doReturn(Optional.ofNullable(Lists.list(site1, site2))).when(siteRepository).findByEnabledTrue();

        siteCrawler.crawlSites();

        verify(categoryCrawler, times(expectedCategoryCrawlerCallsThree))
                .crawlCategory(any(Site.class), any(Category.class));
    }

    @Test
    void whenCrawSitesIsCalledMultiplesTimesShouldWorkWithoutInitializeThreadPoolAgain() {
        final int expectedCategoryCrawlerCallsTwelve = 12;
        final Site site1 = createSiteStub("site1", Lists.list(createCategoryStub(),  createCategoryStub()));
        final Site site2 = createSiteStub("site2", Lists.list(createCategoryStub()));
        final Site site3 = createSiteStub("site3", Lists.list(createCategoryStub(),  createCategoryStub(),
                createCategoryStub()));
        doReturn(Optional.ofNullable(Lists.list(site1, site2, site3))).when(siteRepository).findByEnabledTrue();

        siteCrawler.crawlSites();
        siteCrawler.crawlSites();

        verify(categoryCrawler, times(expectedCategoryCrawlerCallsTwelve))
                .crawlCategory(any(Site.class), any(Category.class));
    }

    @Test
    void whenSiteListIsEmptyShouldNotThrownException() {
        doReturn(Optional.ofNullable(Lists.emptyList())).when(siteRepository).findByEnabledTrue();

        assertDoesNotThrow(() -> siteCrawler.crawlSites());
    }

    @Test
    void whenSiteWithoutCategoryShouldNotThrownException() {
        final Site site1 = createSiteStub("site1", null);

        doReturn(Optional.ofNullable(Lists.list(site1))).when(siteRepository).findByEnabledTrue();

        assertDoesNotThrow(() -> siteCrawler.crawlSites());
    }

    @Test
    void whenSiteWithoutEnableCategoryShouldNotThrownException() {
        Category category = createCategoryStub();
        category.setEnabled(false);
        final Site site1 = createSiteStub("site1", Lists.list(category));

        doReturn(Optional.ofNullable(Lists.list(site1))).when(siteRepository).findByEnabledTrue();

        assertDoesNotThrow(() -> siteCrawler.crawlSites());
    }

    private Site createSiteStub(final String siteId, final List<Category> categoryList) {
        Site site = new Site();
        site.setUrl("http://" + siteId + ".com");
        site.setTitle(siteId);
        Category category = new Category();
        category.setEnabled(true);
        site.setCategoryList(categoryList);

        return site;
    }

    private Category createCategoryStub() {
        Category category = new Category();
        category.setEnabled(true);

        return category;
    }
}