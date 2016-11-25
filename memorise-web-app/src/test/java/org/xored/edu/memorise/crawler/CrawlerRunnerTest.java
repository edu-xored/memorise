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
import org.xored.edu.memorise.crawler.api.MemoMatching;
import org.xored.edu.memorise.crawler.api.MemoParser;

import java.util.ArrayList;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Anatoly on 28.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class CrawlerRunnerTest {
    private CrawlerRunner crawlerRunner;
    private CrawlerSettings crawlerSettings;
    private Memo memo;
    @Autowired
    private MemoParser memoParser;
    @Autowired
    private MemoMatching memoMatching;

    @Before
    public void setUp() throws Exception {
        crawlerRunner = new CrawlerRunner(memoMatching, memoParser);
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

        Assert.assertTrue(memo.getCounter().longValue() > 10L);
        Assert.assertEquals(MemoStatus.ACTUAL, memo.getStatus());
    }

    @Test
    public void runNullCandidate() throws Exception {
        crawlerRunner.run(crawlerSettings, null);

        Assert.assertEquals(0L, memo.getCounter().longValue());
    }
}