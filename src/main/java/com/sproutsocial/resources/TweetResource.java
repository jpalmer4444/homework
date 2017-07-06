/*
 * TweetResource
 */
package com.sproutsocial.resources;

import com.codahale.metrics.annotation.Timed;
import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.api.Tweet;
import com.sproutsocial.api.Update;
import com.sproutsocial.core.exception.TwitterIdNotFoundException;
import com.sproutsocial.db.Account;
import com.sproutsocial.core.svc.AccountService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;
import com.sproutsocial.core.svc.APIService;
import com.sproutsocial.core.svc.TwitterService;

@Path("/tweet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TweetResource {
    
    private final static Logger logger = LoggerFactory.getLogger(TweetResource.class);
    
    private final AccountService accountService;
    
    private final APIService apiService;
    
    private final TwitterService twitterService;
    
    private final HomeworkConfiguration configuration;
    
    public TweetResource(AccountService accountService, APIService apiService, HomeworkConfiguration configuration, TwitterService twitterService) {
        this.accountService = accountService;
        this.apiService = apiService;
        this.twitterService = twitterService;
        this.configuration = configuration;
    }
    
    @POST
    @Timed
    public Tweet tweet(Update update) throws TwitterException, TwitterIdNotFoundException {
        logger.info(String.format("Looking up account for twitter_id: %s", update.getTwitterId()));
        Account account = accountService.findAccountByTwitterId(update.getTwitterId());
        if(null == account){
            logger.error(String.format("No account found for twitter_id: %s", update.getTwitterId()));
            throw new TwitterIdNotFoundException(String.format("No account found for twitter_id: %s", update.getTwitterId()));
        } 
        else 
        return apiService.tweet(twitterService.getTwitter(configuration, account), update.getContent());
    }
    
}

