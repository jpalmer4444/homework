/*
 * TimelineResource
 */
package com.sproutsocial.resources;

import com.codahale.metrics.annotation.Timed;
import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.api.Tweet;
import com.sproutsocial.core.exception.TwitterIdNotFoundException;
import com.sproutsocial.db.Account;
import com.sproutsocial.core.svc.AccountService;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;
import com.sproutsocial.core.svc.APIService;
import com.sproutsocial.core.svc.TwitterService;

@Path("/timeline")
@Produces(MediaType.APPLICATION_JSON)
public class TimelineResource {
    
    private final static Logger logger = LoggerFactory.getLogger(TimelineResource.class);
    
    private final AccountService accountService;
    
    private final APIService apiService;
    
    private final TwitterService twitterService;
    
    private final HomeworkConfiguration configuration;

    public TimelineResource(AccountService accountService, APIService apiService, HomeworkConfiguration configuration, TwitterService twitterService) {
        this.accountService = accountService;
        this.apiService = apiService;
        this.twitterService = twitterService;
        this.configuration = configuration;
    }
    
    @GET
    @Timed
    public List<Tweet> getHomeTimeline(@QueryParam("id") String id) throws TwitterException, TwitterIdNotFoundException {
        logger.info(String.format("Looking up account for twitter_id: %s", id));
        Account account = accountService.findAccountByTwitterId(id);
        if(null == account){
            logger.error(String.format("No account found for twitter_id: %s", id));
            throw new TwitterIdNotFoundException(String.format("No account found for twitter_id: %s", id));
        }
        return apiService.getTweets(twitterService.getTwitter(configuration, account));
        
    }
    
}

