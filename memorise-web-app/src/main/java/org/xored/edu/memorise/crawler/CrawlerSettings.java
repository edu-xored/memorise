package org.xored.edu.memorise.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;

import java.util.Collection;

/**
 * Created by Anatoly on 28.10.2016.
 */
public class CrawlerSettings {
    private final CrawlConfig crawlConfig;
    private final int numberOfCrawlers;
    private final Collection<String> seeds;

    public CrawlerSettings(CrawlConfig crawlConfig, int numberOfCrawlers, Collection<String> seeds) {
        this.crawlConfig = crawlConfig;
        this.numberOfCrawlers = numberOfCrawlers;
        this.seeds = seeds;
    }

    public CrawlConfig getCrawlConfig() {
        return crawlConfig;
    }

    public int getNumberOfCrawlers() {
        return numberOfCrawlers;
    }

    public Collection<String> getSeeds() {
        return seeds;
    }
}
