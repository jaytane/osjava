package com.generationjava.comics;

import org.osjava.scraping.parser.UrlScraper;

import com.generationjava.scrape.HtmlScraper;

public class DilbertParser extends UrlScraper {

    protected String scrapeUrl(HtmlScraper scraper) {
        scraper.moveToTagWith("ALT", "Today's Dilbert Comic");

        return scraper.get("IMG[SRC]");
    }

}

