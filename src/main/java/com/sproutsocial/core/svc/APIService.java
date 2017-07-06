/*
 * APIService Interface
 */
package com.sproutsocial.core.svc;

import com.sproutsocial.api.Tweet;
import java.util.List;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public interface APIService {
    
    List<Tweet> getTweets(Twitter twitter) throws TwitterException;
    
    Tweet tweet(Twitter twitter, String text) throws TwitterException;
    
}
