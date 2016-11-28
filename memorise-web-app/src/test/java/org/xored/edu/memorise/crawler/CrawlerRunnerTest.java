package org.xored.edu.memorise.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.MemoStatus;
import org.xored.edu.memorise.api.memo.services.SearchMemoService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoly on 28.10.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class CrawlerRunnerTest {
    @Autowired
    private CrawlerRunner crawlerRunner;
    private CrawlerSettings crawlerSettings;
    private Memo memo;
    @Autowired
    private SearchMemoService searchMemoService;
    @Before
    public void setUp() throws Exception {
        ArrayList<String> seeds = new ArrayList<String>();
        seeds.add("http://www.eurosport.ru/football/champions-league/2016-2017/story_sto5959402.shtml");
        CrawlConfig crawlConfig = setCrawlConfig();
        crawlerSettings = new CrawlerSettings(crawlConfig, 1, seeds);
        memo = new Memo();
        memo.setTitle("Ростов");
    }

    private CrawlConfig setCrawlConfig() {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("src/resources/test/crawlerTemporaryDirectory");
        crawlConfig.setMaxDepthOfCrawling(1);
        crawlConfig.setMaxPagesToFetch(1);
        return crawlConfig;
    }

    @Test
    public void run() throws Exception {
        crawlerRunner.run(crawlerSettings, memo);

        Assert.assertTrue(memo.getCounter() > 10L);
        Assert.assertEquals(MemoStatus.ACTUAL, memo.getStatus());
        Assert.assertTrue(!searchMemoService.findMemosByTitle("Ростов").isEmpty());
    }

    @Test
    public void runNullCandidate() throws Exception {
        crawlerRunner.run(crawlerSettings, null);

        Assert.assertEquals(0L, memo.getCounter().longValue());
    }
}