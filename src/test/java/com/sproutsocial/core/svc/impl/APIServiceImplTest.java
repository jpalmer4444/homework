/*
 * Tests APIServiceImpl
 */
package com.sproutsocial.core.svc.impl;

import com.sproutsocial.api.Tweet;
import com.sproutsocial.core.svc.APIService;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import twitter4j.Twitter;

import static org.mockito.Mockito.*;
import twitter4j.ResponseList;
import twitter4j.TwitterException;

/**
 *
 * @author jpalmer
 */
public class APIServiceImplTest {
    
    private final APIService apiService = new APIServiceImpl();
    private final Twitter twitter = mock(Twitter.class);
    private final ResponseList list = mock(ResponseList.class);
    private final List<Tweet> empty = new ArrayList<>();
    
    @Before
    public void setup() throws TwitterException{
        when(list.isEmpty()).thenReturn(true);
        when(twitter.getHomeTimeline()).thenReturn(list);
    }
    
    @Test 
    public void testAPICallsTwitter() throws TwitterException{
        assertThat(apiService.getTweets(twitter)).isEqualTo(empty);
        verify(twitter).getHomeTimeline();
        verify(list).isEmpty();
    }
    
}
