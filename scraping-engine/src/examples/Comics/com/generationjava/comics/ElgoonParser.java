package com.generationjava.comics;

import org.osjava.scraping.parser.UrlScraper;

import com.generationjava.scrape.HtmlScraper;

public class ElgoonParser extends UrlScraper {

    protected String scrapeUrl(HtmlScraper scraper) {
        scraper.moveToTagWith("ALT", "comic");

        return scraper.get("IMG[SRC]");
    }

}

