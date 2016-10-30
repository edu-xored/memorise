package com.eduxored.memorise.crawler;


import com.eduxored.memorise.crawler.api.MatchingMemeCandidate;
import com.eduxored.memorise.crawler.api.MemeParser;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: danilama
 * Date: 8/22/14
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionsCrawler extends WebCrawler {
    private static MemeParser memeParser;
    private static MemeCandidate memeCandidate;
    private static MatchingMemeCandidate matchingMemeCandidate;
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    static void configure(MemeParser parser, MatchingMemeCandidate matching, MemeCandidate candidate) {
        memeParser = parser;
        matchingMemeCandidate = matching;
        memeCandidate = candidate;
    }

    @Override
    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        logger.info("URL: " + page.getWebURL().getURL());
        if (page.getParseData() instanceof HtmlParseData) {
            String text = getPageText(page);
            MemeInfo info = parseMemeCandidate(text);
            logger.info("Meme info counter = " + info.getCounter());
            matchingMemeCandidate.match(memeCandidate, info);
        }
    }

    private String getPageText(Page page) {
        return ((HtmlParseData) page.getParseData()).getText();
    }

    private MemeInfo parseMemeCandidate(String text) {
        return memeParser.parse(text, memeCandidate);
    }

}