package com.crevainera.webycrawler.services;

import com.crevainera.webycrawler.dto.HeadLineDto;
import com.crevainera.webycrawler.entities.ScrapRule;
import com.crevainera.webycrawler.exception.WebyException;
import com.google.common.collect.Lists;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.crevainera.webycrawler.constant.WebyConstant.*;

@Service
@Slf4j
public class CategoryScraperService {

    public static final String INPUT_BINDING = "$input";

    public  List<HeadLineDto> scrap(final Document document, final ScrapRule scrapRule) throws WebyException {

        final Binding documentBinding = new Binding();
        documentBinding.setProperty(INPUT_BINDING, document);

        Elements articles;
        try {
            String article = removeHTMLTags(scrapRule.getHeadline());
            articles = (Elements) new GroovyShell(documentBinding).evaluate(article);
        } catch (Exception e) {
            log.error(SCRAPER_ERROR_HEADLINE.name(), e.getStackTrace().toString());
            throw new WebyException(SCRAPER_ERROR_HEADLINE.name());
        }

        List<HeadLineDto> headLineList = new ArrayList<>();
        for (Element article : articles) {
            HeadLineDto headLine = new HeadLineDto();

            final Binding articleBinding = new Binding();
            articleBinding.setProperty(INPUT_BINDING, article);

            try {
                String headlineTitle = (String) new GroovyShell(articleBinding).evaluate(scrapRule.getTitle());
                headLine.setTitle(removeHTMLTags(headlineTitle));
            } catch (Exception e) {
                log.error(SCRAPER_ERROR_TITLE.name(), e.getStackTrace().toString());
            }

            try  {
                String headlineLink = (String) new GroovyShell(articleBinding).evaluate(scrapRule.getLink());
                headLine.setUrl(removeHTMLTags(headlineLink));
            } catch (Exception e) {
                log.error(SCRAPER_ERROR_LINK.name(), e.getStackTrace().toString());
            }

            try {
                String headlineImage = (String) new GroovyShell(articleBinding).evaluate(scrapRule.getImage());
                headLine.setThumbUrl(removeHTMLTags(headlineImage));
            } catch (Exception e) {
                log.error(SCRAPER_ERROR_THUMB.name(), e.getStackTrace().toString());
            }

            headLineList.add(headLine);
        }

        return Lists.reverse(headLineList);
     }

     private String removeHTMLTags(final String html) {
         return Jsoup.clean(html, Whitelist.none());
     }
}
