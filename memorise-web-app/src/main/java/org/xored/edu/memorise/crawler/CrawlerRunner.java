package org.xored.edu.memorise.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.xored.edu.memorise.api.memo.BasicMemoService;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.crawler.api.MemoMatching;
import org.xored.edu.memorise.crawler.api.MemoParser;

/**
 * @author defremov
 */
public class CrawlerRunner {
    private MemoMatching memoMatching;
    private MemoParser memoParser;
    private BasicMemoService basicMemoService;
    private SearchMemoService searchMemoService;

    public CrawlerRunner(MemoParser memoParser,
                         MemoMatching memoMatching,
                         BasicMemoService basicMemoService,
                         SearchMemoService searchMemoService) {
        this.memoMatching = memoMatching;
        this.memoParser = memoParser;
        this.basicMemoService = basicMemoService;
        this.searchMemoService = searchMemoService;
    }

    void run(final CrawlerSettings crawlerSettings, final Memo memo) throws Exception {
        CrawlConfig config = crawlerSettings.getCrawlConfig();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed : crawlerSettings.getSeeds()) {
            controller.addSeed(seed);
        }

        ActionsCrawler.configure(memoParser, memoMatching, basicMemoService, searchMemoService, memo);
        controller.start(ActionsCrawler.class, crawlerSettings.getNumberOfCrawlers());
    }
}
