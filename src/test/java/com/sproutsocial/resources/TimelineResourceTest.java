/*
 * Integration test for TimelineResource
 */
package com.sproutsocial.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.api.Tweet;
import com.sproutsocial.db.Account;
import com.sproutsocial.core.svc.AccountService;
import com.sproutsocial.core.svc.impl.AccountServiceImpl;
import com.sproutsocial.core.svc.impl.APIServiceImpl;
import io.dropwizard.testing.junit.ResourceTestRule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import static org.mockito.Mockito.*;
import twitter4j.TwitterException;
import com.sproutsocial.core.svc.APIService;
import com.sproutsocial.core.svc.TwitterService;
import com.sproutsocial.core.svc.impl.TwitterServiceImpl;

public class TimelineResourceTest {
    
    private final String twitterId = "123";
    private final List<Tweet> list = new ArrayList<>();
    private final String oauthToken = "token";
    private final String oauthSecret = "secret";
    private final DateTime createdAt = new DateTime("149893408");
    private final String post = "Just watching Guardians of the Galaxy.";
    
    private final Tweet tweet = new Tweet(
            twitterId, 
            post, 
            createdAt, 
            "http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png");
    
    private final Account account = new Account(1, oauthToken, oauthSecret, 123);
    
    private static final AccountService accountService = mock(AccountServiceImpl.class);
    private static final APIService apiService = mock(APIServiceImpl.class);
    private static final TwitterService twitterService = mock(TwitterServiceImpl.class);
    private static final HomeworkConfiguration configuration = mock(HomeworkConfiguration.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TimelineResource(accountService, apiService, configuration, twitterService))
            .build();

    @Before
    public void setup() throws TwitterException {
        when(accountService.findAccountByTwitterId(eq(twitterId))).thenReturn(account);
        list.add(tweet);
        when(apiService.getTweets(eq(twitterService.getTwitter(configuration, account)))).thenReturn(list);
    }

    @After
    public void tearDown(){
        list.clear();
        reset(accountService);
        reset(apiService);
        reset(configuration);
    }

    @Test
    public void testGetTweets() throws TwitterException, JsonProcessingException, IOException {
        assertThat(resources.client().target("/timeline?id=" + twitterId).request().
                get(List.class).toString().contains(post));
        verify(apiService).getTweets(twitterService.getTwitter(configuration, account));
        verify(accountService).findAccountByTwitterId(twitterId);
    }
}
