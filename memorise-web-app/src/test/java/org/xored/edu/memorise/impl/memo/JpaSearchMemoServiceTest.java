package org.xored.edu.memorise.impl.memo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.api.memo.SearchMemoService;
import org.xored.edu.memorise.impl.DataBaseInitializer;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Anatoly on 22.11.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context.xml")
public class JpaSearchMemoServiceTest {
    @Autowired
    DataBaseInitializer dataBaseInitializer;

    @Autowired
    SearchMemoService searchMemoService;

    @Test
    public void testMemosByTitle() {
        List memos = searchMemoService.findMemosByTitle("This is Memo title 1");

        Assert.assertEquals("This is Memo title 1", ((Memo)memos.get(0)).getTitle());
    }

    @Test
    public void testMemosByDescription() {
        List memos = searchMemoService.findMemosByDescription("This is Memo detailed info 1");

        Assert.assertEquals("This is Memo detailed info 1", ((Memo)memos.get(0)).getDescription());
    }

    @Test
    public void testMemosContainsInTitle() {
        List memos = searchMemoService.findMemosContainsTextInTitle("title 1");

        Assert.assertEquals(6, memos.size());
    }

    @Test
    public void testMemosContainsInDescription() {
        List memos = searchMemoService.findMemosContainsTextInDescription("2");

        Assert.assertEquals(2, memos.size());
    }
}