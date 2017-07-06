/*
 * TwitterService
 */
package com.sproutsocial.core.svc.impl;

import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.core.svc.TwitterService;
import com.sproutsocial.db.Account;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterServiceImpl implements TwitterService {

    @Override
    public Twitter getTwitter(HomeworkConfiguration configuration, Account account) {
        ConfigurationBuilder cb = getBuilder(configuration);

        cb.setOAuthAccessToken(account.getOauthToken())
                .setOAuthAccessTokenSecret(account.getOauthSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
    
    private ConfigurationBuilder getBuilder(HomeworkConfiguration configuration) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
                cb.setOAuthConsumerKey(configuration.getTwitterConsumerKey())
                .setOAuthConsumerSecret(configuration.getTwitterConsumerSecret());
        return cb;
    }
    
}
