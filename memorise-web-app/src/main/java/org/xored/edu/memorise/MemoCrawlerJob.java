package org.xored.edu.memorise;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xored.edu.memorise.crawler.CrawlerRunner;
import org.xored.edu.memorise.crawler.CrawlerSettings;
import org.xored.edu.memorise.crawler.MemeCandidate;
import org.xored.edu.memorise.crawler.api.MatchingMemeCandidate;
import org.xored.edu.memorise.crawler.api.MemeParser;
import org.xored.edu.memorise.crawler.impl.SimpleMemeParserImpl;

import java.util.ArrayList;

public class MemoCrawlerJob extends QuartzJobBean{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private MemeParser memeParser;
    private CrawlerRunner crawlerRunner;
    private CrawlerSettings crawlerSettings;
    private MemeCandidate memeCandidate;
    private MatchingMemeCandidate matchingMemeCandidate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        logger.info("MemoCrawlerJob is running");
        long startTime, workingTime;
        startTime = System.currentTimeMillis();

        memeParser = new SimpleMemeParserImpl();
        crawlerRunner = new CrawlerRunner(matchingMemeCandidate, memeParser);
        ArrayList<String> seeds = new ArrayList<String>();
        seeds.add("http://the-flow.ru/videos/yelawolf-shadows/");
        CrawlConfig crawlConfig = setCrawlConfig();
        crawlerSettings = new CrawlerSettings(crawlConfig, 1, seeds);
        memeCandidate = new MemeCandidate("wolf");

        try {
            crawlerRunner.run(crawlerSettings, memeCandidate);
        } catch (Exception e) {
            //TODO: handle exception
        }

        workingTime = System.currentTimeMillis() - startTime;
        logger.info("Crawler has worked for " + workingTime + " milliseconds");
    }

    private CrawlConfig setCrawlConfig() {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("src/resources/test/crawlerTemporaryDirectory");
        crawlConfig.setMaxDepthOfCrawling(1);
        crawlConfig.setMaxPagesToFetch(1);
        return crawlConfig;
    }
}

