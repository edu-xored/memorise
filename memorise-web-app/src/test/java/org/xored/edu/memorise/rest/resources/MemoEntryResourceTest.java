package org.xored.edu.memorise.rest.resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xored.edu.memorise.api.memo.Memo;
import org.xored.edu.memorise.impl.memo.MemoEntryDao;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Anatoly on 23.11.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class MemoEntryResourceTest {

    @Autowired
    private MemoEntryResource memoEntryResource;

    @Mock
    MemoEntryDao spy;

    @Before
    public void setUp() {
        initMocks(this);
        memoEntryResource.setMemoEntryDao(spy);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn("anonymousUser");
    }

    @Test
    public void list() throws Exception {
        //create entity to evict cache
        memoEntryResource.create(new Memo());
        for (int i = 0; i < 100; i++) {
            memoEntryResource.list();
        }
        verify(spy, times(1)).findAll();
    }

    @Test
    public void read() throws Exception {
        memoEntryResource.create(new Memo());
        memoEntryResource.list();
        memoEntryResource.create(new Memo());
        memoEntryResource.list();

        verify(spy, times(2)).findAll();
    }

    @Test
    public void update() throws Exception {
        memoEntryResource.update(1L, new Memo());
        memoEntryResource.list();
        memoEntryResource.update(1L, new Memo());
        memoEntryResource.list();

        verify(spy, times(2)).findAll();
    }

    @Test
    public void delete() throws Exception {
        memoEntryResource.delete(1L);
        memoEntryResource.list();
        memoEntryResource.delete(2L);
        memoEntryResource.list();

        verify(spy, times(2)).findAll();
    }

}