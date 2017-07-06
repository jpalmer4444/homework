/*
 * TwitterServiceImpl Test
 */
package com.sproutsocial.core.svc.impl;


import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.db.Account;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import twitter4j.Twitter;

import static org.mockito.Mockito.*;
import twitter4j.TwitterException;

public class TwitterServiceImplTest {
    
    private final HomeworkConfiguration configuration = mock(HomeworkConfiguration.class);
    private final Account account = mock(Account.class);
    private final String consumerKey = "123";
    private final String consumerSecret = "456";
    private final String oauthToken = "789";
    private final String oauthSecret = "012";
    private final TwitterServiceImpl twitterService = new TwitterServiceImpl();
    
    @Before
    public void setup() {
        when(configuration.getTwitterConsumerKey()).thenReturn(consumerKey);
        when(configuration.getTwitterConsumerSecret()).thenReturn(consumerSecret);
        when(account.getOauthToken()).thenReturn(oauthToken);
        when(account.getOauthSecret()).thenReturn(oauthSecret);
    }
    
    @Test 
    public void testTwitterServiceProcessesParameters() throws TwitterException{
        assertThat(twitterService.getTwitter(configuration, account)).isInstanceOf(Twitter.class);
        verify(configuration).getTwitterConsumerKey();
        verify(configuration).getTwitterConsumerSecret();
        verify(account).getOauthToken();
        verify(account).getOauthSecret();
    }
    
}
