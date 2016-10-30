package com.eduxored.memorise.crawler;

import com.eduxored.memorise.crawler.api.MatchingMemeCandidate;
import com.eduxored.memorise.crawler.api.MemeParser;
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
    private MatchingMemeCandidate matchingMemeCandidate;
    private MemeParser memeParser;

    @Autowired
    public CrawlerRunner(MatchingMemeCandidate matchingMemeCandidate, MemeParser memeParser) {
        this.matchingMemeCandidate = matchingMemeCandidate;
        this.memeParser = memeParser;
    }

    public void run(final CrawlerSettings crawlerSettings, final MemeCandidate memeCandidate) throws Exception {
        CrawlConfig config = crawlerSettings.getCrawlConfig();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed : crawlerSettings.getSeeds()) {
            controller.addSeed(seed);
        }

        ActionsCrawler.configure(memeParser, matchingMemeCandidate, memeCandidate);
        controller.start(ActionsCrawler.class, crawlerSettings.getNumberOfCrawlers());
    }
}
