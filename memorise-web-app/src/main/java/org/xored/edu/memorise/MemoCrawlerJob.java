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

public class MemoCrawlerJob extends QuartzJobBean{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final List<String> SEEDS = Arrays.asList("http://the-flow.ru/news/grebz-kharkov-2");

	private static Lock crawlerExecuteLock = new ReentrantLock();

	private final String CRAWL_TEMP_DIR = "src/resources/test/crawlerTemporaryDirectory";
	private final int NUMBER_OF_CRAWLERS = 1;
	private final int MAX_DEPTH_OF_CRAWLING =1;
	private final int MAX_PAGES_TO_FETCH = 1;
	private final String MEME_CANDIDATE_NAME = "группы";

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

			Memo memo = new Memo();
			memo.setTitle(MEME_CANDIDATE_NAME);
			CrawlConfig crawlConfig = setCrawlConfig();
			CrawlerSettings crawlerSettings = new CrawlerSettings(crawlConfig,NUMBER_OF_CRAWLERS, SEEDS);

			crawlerRunner.run(crawlerSettings, memo);

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

