/*
 * APIServiceImpl that uses Twitter REST API to retrieve Tweets based on 
 * Account and Configuration.
 */
package com.sproutsocial.core.svc.impl;

import com.sproutsocial.api.Tweet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import com.sproutsocial.core.svc.APIService;

public class APIServiceImpl implements APIService {

    private final Logger logger = LoggerFactory.getLogger(APIServiceImpl.class.getName());

    @Override
    public List<Tweet> getTweets(Twitter twitter) throws TwitterException {

        logger.info("Retrieving Twitter tweets.");

        List<Tweet> statii = new ArrayList<>();

        try {
            List<Status> tweets = twitter.getHomeTimeline();
            if (null != tweets && !tweets.isEmpty()) {
                tweets.stream().map((twt) -> {
                    String screenName = twt.getUser().getScreenName();
                    Date date = twt.getCreatedAt();
                    DateTime dateTime = new DateTime(date);
                    String post = twt.getText();
                    String profileImage = twt.getUser().getProfileImageURL();
                    Tweet tweet = new Tweet(screenName, post, dateTime, profileImage);
                    return tweet;
                }).forEachOrdered((tweet) -> {
                    statii.add(tweet);
                });
            }

        } catch (TwitterException te) {
            logger.error(String.format("TwitterException occurred while getting tweets. Error Code: %s, Exception Code: %s, Error Message: %s", te.getErrorCode(), te.getExceptionCode(), te.getErrorMessage()));
            throw te;
        }

        logger.info(String.format("Found %s tweets.", statii.size()));

        return statii;
    }

    @Override
    public Tweet tweet(Twitter twitter, String text) throws TwitterException {

        logger.info("POSTing tweet.");

        String screenName = null;
        String post = null;
        DateTime dateTime = null;
        String profileImage = null;
        try {
            Status twt = twitter.updateStatus(text);
            screenName = twt.getUser().getScreenName();
            Date date = twt.getCreatedAt();
            dateTime = new DateTime(date);
            post = twt.getText();
            profileImage = twt.getUser().getProfileImageURL();
        } catch (TwitterException te) {
            logger.error(String.format("TwitterException occurred while posting tweet. Error Code: %s, Exception Code: %s, Error Message: %s", te.getErrorCode(), te.getExceptionCode(), te.getErrorMessage()));
            throw te;
        }

        return new Tweet(screenName, post, dateTime, profileImage);
    }

}
