package org.xored.edu.memorise.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.xored.edu.memorise.api.memo.services.BasicMemoService;
import org.xored.edu.memorise.api.memo.services.SearchMemoService;

/**
 * Created by Anatoly on 26.11.2016.
 */
class CrawlerServicesContext {
    private BasicMemoService basicMemoService;
    private SearchMemoService searchMemoService;

    @Autowired
    public CrawlerServicesContext(BasicMemoService basicMemoService, SearchMemoService searchMemoService) {
        this.basicMemoService = basicMemoService;
        this.searchMemoService = searchMemoService;
    }

    public BasicMemoService getBasicMemoService() {
        return basicMemoService;
    }

    public SearchMemoService getSearchMemoService() {
        return searchMemoService;
    }
}
