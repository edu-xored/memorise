package org.xored.edu.memorise.crawler;

import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.api.MemoMatching;
import org.xored.edu.memorise.crawler.api.MemoParser;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author defremov
 */
public class CrawlerRunner {
    private MemoMatching memoMatching;
    private MemoParser memoParser;

    @Autowired
    public CrawlerRunner(MemoMatching memoMatching, MemoParser memoParser) {
        this.memoMatching = memoMatching;
        this.memoParser = memoParser;
    }

    public void run(final CrawlerSettings crawlerSettings, final Memo memo) throws Exception {
        CrawlConfig config = crawlerSettings.getCrawlConfig();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed : crawlerSettings.getSeeds()) {
            controller.addSeed(seed);
        }

        ActionsCrawler.configure(memoParser, memoMatching, memo);
        controller.start(ActionsCrawler.class, crawlerSettings.getNumberOfCrawlers());
    }
}
