package org.xored.edu.memorise.crawler;


import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.crawler.api.MemoMatching;
import org.xored.edu.memorise.crawler.api.MemoEntryFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: danilama
 * Date: 8/22/14
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionsCrawler extends WebCrawler {
    private static MemoEntryFinder memoEntryFinder;
    private static List<Memo> memos;
    private static MemoMatching memoMatching;
    private static CrawlerServicesContext servicesContext;
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    static void configure(MemoEntryFinder memoEntryFinder,
                          MemoMatching memoMatching,
                          CrawlerServicesContext servicesContext,
                          List<Memo> memos) {
        ActionsCrawler.memoEntryFinder = memoEntryFinder;
        ActionsCrawler.memoMatching = memoMatching;
        ActionsCrawler.servicesContext = servicesContext;
        ActionsCrawler.memos = memos;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        logger.info("URL: " + page.getWebURL().getURL());
        if (page.getParseData() instanceof HtmlParseData) {
            String text = getPageText(page);

            for (Memo memo :
                    memos) {
                findAndSaveMemo(text, memo);
            }
        }
    }

    private void findAndSaveMemo(String text, Memo memo) {
        Memo foundMemo = tryFindMemo(memo);
        memoEntryFinder.findEntries(text, foundMemo);

        logger.info(foundMemo + " info counter = " + foundMemo.getCounter());

        memoMatching.match(foundMemo);
        servicesContext.getBasicMemoService().save(foundMemo);
    }

    private Memo tryFindMemo(Memo memo) {
        List<Memo> memosByTitle = servicesContext.getSearchMemoService().findMemosByTitle(memo.getTitle());
        return !memosByTitle.isEmpty() ? memosByTitle.get(0) : memo;
    }

    private String getPageText(Page page) {
        return ((HtmlParseData) page.getParseData()).getText();
    }
}