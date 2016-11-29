package org.xored.edu.memorise;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.CrawlerRunner;
import org.xored.edu.memorise.crawler.CrawlerSettings;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MemoCrawlerJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static List<String> SEEDS = Arrays.asList(
            "http://www.eurosport.ru/football/champions-league/2016-2017/story_sto5959402.shtml",
            "http://ru.xored.com/about/"
    );

    private final static List<Memo> MEMOS = Arrays.asList(new Memo(
                    "Ростов",
                    "Сенсационную победу ФК «Ростов» над «Баварией» запомнят надолго. Мюнхенская ..."
            ),
            new Memo(
                    "Xored",
                    "Xored Software is a software and service company that helps developers and " +
                            "corporations make the most of their investment in development tools, platforms, ..."
            ),
            new Memo(
                    "Трамп",
                    "До́нальд Джон Трамп (англ. Donald John Trump; род. 14 июня 1946 года, Джамейка, " +
                            "боро Куинс, Нью-Йорк, США) — американский бизнесмен и политик..."
            )
    );

    private static Lock crawlerExecuteLock = new ReentrantLock();

    private final static String CRAWL_TEMP_DIR = "src/resources/test/crawlerTemporaryDirectory";
    private final static int NUMBER_OF_CRAWLERS = 2;
    private final static int MAX_DEPTH_OF_CRAWLING = 1;
    private final static int MAX_PAGES_TO_FETCH = 2;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //if crawler already has been running, skip it
        if (!crawlerExecuteLock.tryLock())
            return;
        try {
            logger.info("MemoCrawlerJob is running");
            long startTime, workingTime;

            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            CrawlerRunner crawlerRunner = (CrawlerRunner) jobDataMap.get("crawlRun");

            startTime = System.currentTimeMillis();

            CrawlConfig crawlConfig = setCrawlConfig();
            CrawlerSettings crawlerSettings = new CrawlerSettings(crawlConfig, NUMBER_OF_CRAWLERS, SEEDS);

            crawlerRunner.run(crawlerSettings, MEMOS);

            workingTime = System.currentTimeMillis() - startTime;
            logger.info("Crawler has worked for " + workingTime + " milliseconds");
        } catch (Exception e) {
            logger.info("Crawler crash");
            //TODO: add more info about cause crawler error
            throw new JobExecutionException("Crawler error");
        } finally {
            crawlerExecuteLock.unlock();
        }
    }

    private CrawlConfig setCrawlConfig() {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder(CRAWL_TEMP_DIR);
        crawlConfig.setMaxDepthOfCrawling(MAX_DEPTH_OF_CRAWLING);
        crawlConfig.setMaxPagesToFetch(MAX_PAGES_TO_FETCH);
        return crawlConfig;
    }
}

