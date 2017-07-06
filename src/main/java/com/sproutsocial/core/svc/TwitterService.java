/*
 * TwitterService Interface
 */
package com.sproutsocial.core.svc;

import com.sproutsocial.HomeworkConfiguration;
import com.sproutsocial.db.Account;
import twitter4j.Twitter;

public interface TwitterService {
    
    Twitter getTwitter(HomeworkConfiguration configuration, Account account);
    
}
