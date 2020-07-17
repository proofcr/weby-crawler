package com.crevainera.weby.crawler.services.headline;

import com.crevainera.weby.crawler.dto.HeadLineDto;
import com.crevainera.weby.crawler.entities.ScrapRule;
import com.crevainera.weby.crawler.exception.WebyException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HeadlineScraperService {

    private HtmlDocumentScraper scraper;

    @Autowired
    public HeadlineScraperService(final HtmlDocumentScraper scraper) {
        this.scraper = scraper;
    }

    public  List<HeadLineDto> scrap(final Document document, final ScrapRule scrapRule) throws WebyException {

        List<HeadLineDto> headLineList = new ArrayList<>();
        for (Element article : scraper.getArticleElements(document, scrapRule.getHeadline())) {
            HeadLineDto headLine = new HeadLineDto();
            headLine.setTitle(scraper.getPlainText(article, scrapRule.getTitle()));
            headLine.setUrl(scraper.getPlainText(article, scrapRule.getLink()));
            headLine.setThumbUrl(scraper.getPlainText(article, scrapRule.getImage()));
            headLineList.add(headLine);
        }

        return Lists.reverse(headLineList);
     }
}
