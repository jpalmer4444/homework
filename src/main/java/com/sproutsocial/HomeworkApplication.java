/*
 * HomeworkApplication
 * Entry point for the application.
 */
package com.sproutsocial;

import com.sproutsocial.core.exception.GenericExceptionMapper;
import com.sproutsocial.core.exception.TwitterIdNotFoundException;
import com.sproutsocial.core.svc.AccountService;
import com.sproutsocial.core.svc.APIService;
import com.sproutsocial.core.svc.TwitterService;
import com.sproutsocial.core.svc.impl.AccountServiceImpl;
import com.sproutsocial.core.svc.impl.APIServiceImpl;
import com.sproutsocial.core.svc.impl.TwitterServiceImpl;
import com.sproutsocial.db.AccountDAO;
import com.sproutsocial.health.AppHealthCheck;
import com.sproutsocial.resources.TimelineResource;
import com.sproutsocial.resources.TweetResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeworkApplication extends Application<HomeworkConfiguration> {
    
    private final static Logger logger = LoggerFactory.getLogger(HomeworkApplication.class);

    public static void main(final String[] args) throws Exception {
        logger.info("Bootstrapping Homework Application.");
        new HomeworkApplication().run(args);
    }

    @Override
    public String getName() {
        return "Homework";
    }

    @Override
    public void initialize(final Bootstrap<HomeworkConfiguration> bootstrap) {
        //nothing to do
    }

    @Override
    public void run(final HomeworkConfiguration configuration, final Environment environment) {
        
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sqlite");
        
        final AccountDAO dao = jdbi.onDemand(AccountDAO.class);
        final AccountService accountService = new AccountServiceImpl(dao);
        
        environment.jersey().register(new TwitterIdNotFoundException());
        environment.jersey().register(new GenericExceptionMapper());

        final APIService apiService = new APIServiceImpl();
        final TwitterService twitterService = new TwitterServiceImpl();
        final TimelineResource timelineresource = new TimelineResource(
                accountService,
                apiService,
                configuration,
                twitterService
        );
        
        final TweetResource tweetresource = new TweetResource(
                accountService,
                apiService,
                configuration,
                twitterService
        );

        final AppHealthCheck appCheck = new AppHealthCheck();

        environment.healthChecks().register("app", appCheck);

        environment.jersey().register(timelineresource);
        environment.jersey().register(tweetresource);
        
    }

}
