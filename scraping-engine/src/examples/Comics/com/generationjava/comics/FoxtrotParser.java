package com.generationjava.comics;

import org.osjava.scraping.parser.UrlScraper;

import com.generationjava.scrape.HtmlScraper;

public class FoxtrotParser extends UrlScraper {

    protected String scrapeUrl(HtmlScraper scraper) {
        scraper.moveToTagWith("width", "600");

        return scraper.get("img[src]");
    }

}

