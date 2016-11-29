package org.xored.edu.memorise.impl.memo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.MemoStatus;
import org.xored.edu.memorise.api.memo.services.SearchMemoService;
import org.xored.edu.memorise.impl.DataBaseInitializer;
import org.xored.edu.memorise.api.memo.services.MemoEntryDao;

import java.util.List;
import java.util.Date;

/**
 * Created by Anatoly on 22.11.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class JpaSearchMemoServiceTest {
    @Autowired
    DataBaseInitializer dataBaseInitializer;

    @Autowired
    SearchMemoService searchMemoService;

    private MemoEntryDao memoEntryDao;

    @Test
    public void testMemosByTitle() {
        List memos = searchMemoService.findMemosByTitle("Old meme");

        Assert.assertEquals("Old meme", ((Memo)memos.get(0)).getTitle());
    }

    @Test
    public void testMemosByDescription() {
        List memos = searchMemoService.findMemosByDescription("This is test meme");

        Assert.assertEquals("This is test meme", ((Memo)memos.get(0)).getDescription());
    }

    @Test
    public void testMemosContainsInTitle() {
        List memos = searchMemoService.findMemosContainsTextInTitle("Old");

        Assert.assertEquals(1, memos.size());
    }

    @Test
    public void testMemosContainsInDescription() {
        List memos = searchMemoService.findMemosContainsTextInDescription("meme");

        Assert.assertEquals(1, memos.size());
    }
}