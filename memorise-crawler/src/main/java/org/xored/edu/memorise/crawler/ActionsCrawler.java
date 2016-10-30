package org.xored.edu.memorise.crawler;


import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: danilama
 * Date: 8/22/14
 * Time: 12:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class ActionsCrawler extends WebCrawler {


        private static final SimpleDateFormat ACTION_END_DATE_FORMAT = new SimpleDateFormat("Последний день: DD.MM.yyyy");
        private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g"
                                                          + "|png|tiff?|mid|mp2|mp3|mp4"
                                                          + "|wav|avi|mov|mpeg|ram|m4v|pdf"
                                                          + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

        /**
         * You should implement this function to specify whether
         * the given url should be crawled or not (based on your
         * crawling logic).
         */
        @Override
        public boolean shouldVisit(WebURL url) {
                String href = url.getURL().toLowerCase();
                return !FILTERS.matcher(href).matches() && href.startsWith("http://www.vottakskidka.ru");
        }

        /**
         * This function is called when a page is fetched and ready
         * to be processed by your program.
         */
        @Override
        public void visit(Page page) {
                String url = page.getWebURL().getURL();
                System.out.println("URL: " + url);

                if (page.getParseData() instanceof HtmlParseData) {
                        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                        String text = htmlParseData.getText();
                        String html = htmlParseData.getHtml();

                        // todo add parse memo candidate here

                        // todo add memo candidate matching to memo

                }
        }



}
