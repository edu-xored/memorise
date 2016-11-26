package org.xored.edu.memorise.crawler;


import org.xored.edu.memorise.api.memo.BasicMemoService;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.crawler.api.MemoMatching;
import org.xored.edu.memorise.crawler.api.MemoParser;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.xored.edu.memorise.api.memo.Memo;

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
    private static MemoParser memoParser;
    private static Memo memo;
    private static MemoMatching memoMatching;
    private static BasicMemoService basicMemoService;
    private static SearchMemoService searchMemoService;
    private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
            + "|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf"
            + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    static void configure(MemoParser memoParser,
                          MemoMatching memoMatching,
                          BasicMemoService basicMemoService,
                          SearchMemoService searchMemoService,
                          Memo memo) {
        ActionsCrawler.memoParser = memoParser;
        ActionsCrawler.memoMatching = memoMatching;
        ActionsCrawler.basicMemoService = basicMemoService;
        ActionsCrawler.searchMemoService = searchMemoService;
        ActionsCrawler.memo = memo;
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
            memo = tryFindMemo();
            memoParser.parse(text, memo);
            logger.info("Meme info counter = " + memo.getCounter());
            memoMatching.match(memo);
            basicMemoService.saveMemo(memo);
        }
    }

    private Memo tryFindMemo() {
        List memosByTitle = searchMemoService.findMemosByTitle(memo.getTitle());
        return !memosByTitle.isEmpty() ? (Memo) memosByTitle.get(0) : memo;
    }

    private String getPageText(Page page) {
        return ((HtmlParseData) page.getParseData()).getText();
    }
}