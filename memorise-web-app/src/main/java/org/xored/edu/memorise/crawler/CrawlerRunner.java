package org.xored.edu.memorise.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.api.MemoEntryFinder;
import org.xored.edu.memorise.crawler.api.MemoMatching;

import java.util.List;

/**
 * @author defremov
 */
public class CrawlerRunner {
    private MemoMatching memoMatching;
    private MemoEntryFinder memoEntryFinder;
    private CrawlerServicesContext servicesContext;

    public CrawlerRunner(MemoEntryFinder memoEntryFinder,
                         MemoMatching memoMatching,
                         CrawlerServicesContext servicesContext) {
        this.memoMatching = memoMatching;
        this.memoEntryFinder = memoEntryFinder;
        this.servicesContext = servicesContext;
    }

    public void run(final CrawlerSettings crawlerSettings, final List<Memo> memos) throws Exception {
        CrawlConfig config = crawlerSettings.getCrawlConfig();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed : crawlerSettings.getSeeds()) {
            controller.addSeed(seed);
        }

        ActionsCrawler.configure(memoEntryFinder, memoMatching, servicesContext, memos);
        controller.start(ActionsCrawler.class, crawlerSettings.getNumberOfCrawlers());
    }
}
