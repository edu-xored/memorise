package org.xored.edu.memorise.rest.resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Anatoly on 23.11.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class MemoEntryResourceTest {

    @Autowired
    private MemoEntryResource memoEntryResource;
    MemoEntryResource spy;

    @Before
    public void setUp() {
        spy = spy(memoEntryResource);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn("anonymousUser");
    }

    @Test
    public void list() throws Exception {
        //doReturn("first").doReturn("second").when(spy).list();
        /*doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return "123";
            }
        }).when(spy).list();*/

        String first = spy.list();
        String second = spy.list();

        //test can be passed for any value of n in times(n)
        verify(spy, times(1)).list();
        //verify(spy, times(2)).list();
        //verify(spy, times(1000)).list();
        //verify(spy, never()).list();

        assertEquals(first, second);
    }

    @Test
    public void read() throws Exception {

    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

}