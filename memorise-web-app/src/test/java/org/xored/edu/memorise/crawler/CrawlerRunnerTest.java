package org.xored.edu.memorise.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xored.edu.memorise.crawler.api.MatchingMemeCandidate;
import org.xored.edu.memorise.crawler.api.MemeParser;
import org.xored.edu.memorise.crawler.impl.SimpleMemeParserImpl;

import java.util.ArrayList;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Anatoly on 28.10.2016.
 */
public class CrawlerRunnerTest {
    private CrawlerRunner crawlerRunner;
    private CrawlerSettings crawlerSettings;
    private MemeCandidate memeCandidate;
    private MemeParser memeParser;
    @Mock
    private MatchingMemeCandidate matchingMemeCandidate;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        memeParser = new SimpleMemeParserImpl();
        crawlerRunner = new CrawlerRunner(matchingMemeCandidate, memeParser);
        ArrayList<String> seeds = new ArrayList<String>();
        seeds.add("http://the-flow.ru/videos/yelawolf-shadows/");
        CrawlConfig crawlConfig = setCrawlConfig();
        crawlerSettings = new CrawlerSettings(crawlConfig, 1, seeds);
        memeCandidate = new MemeCandidate("wolf");
    }

    private CrawlConfig setCrawlConfig() {
        CrawlConfig crawlConfig = new CrawlConfig();
        crawlConfig.setCrawlStorageFolder("src/resources/test/crawlerTemplateDirectory");
        crawlConfig.setMaxDepthOfCrawling(1);
        crawlConfig.setMaxPagesToFetch(1);
        return crawlConfig;
    }

    @Test
    public void run() throws Exception {
        crawlerRunner.run(crawlerSettings, memeCandidate);

        verify(matchingMemeCandidate, times(1)).match(eq(memeCandidate), (MemeInfo) anyObject());
    }

    @Test
    public void runNullCandidate() throws Exception {
        crawlerRunner.run(crawlerSettings, null);
        verify(matchingMemeCandidate, times(0)).match(eq(memeCandidate), (MemeInfo) anyObject());
    }
}