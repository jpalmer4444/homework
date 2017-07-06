/*
 * AccountDAOTest that builds in-memory DB then tests
 * the AccountDAO instance given by the framework.
 * This would/should get much more verbose as the DAO
 * class incorporates more and more queries. This is an
 * excellent place to make sure the queries are returning 
 * the ResultSet expected.
 */
package com.sproutsocial.db;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Query;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.skife.jdbi.v2.util.IntegerMapper;

public class AccountDAOTest {
    
    private final DataSourceFactory hsqlConfig = new DataSourceFactory();
    private final int twitterId = 123;
    private final String oauthToken = "123";
    private final String oauthSecret = "123";

    private final Account account = new Account(1, oauthToken, oauthSecret, twitterId);

    {
        hsqlConfig.setUrl("jdbc:h2:mem:DbTest-" + System.currentTimeMillis());
        hsqlConfig.setUser("test");
        hsqlConfig.setDriverClass("org.h2.Driver");
        hsqlConfig.setValidationQuery("SELECT 1");
    }

    private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
    private final LifecycleEnvironment lifecycleEnvironment = mock(LifecycleEnvironment.class);
    private final Environment environment = mock(Environment.class);
    private final DBIFactory factory = new DBIFactory();
    private final List<Managed> managed = Lists.newArrayList();
    private final MetricRegistry metricRegistry = new MetricRegistry();
    private DBI dbi;

    @Before
    public void setUp() throws Exception {
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.lifecycle()).thenReturn(lifecycleEnvironment);
        when(environment.metrics()).thenReturn(metricRegistry);

        this.dbi = factory.build(environment, hsqlConfig, "hsql");
        final ArgumentCaptor<Managed> managedCaptor = ArgumentCaptor.forClass(Managed.class);
        verify(lifecycleEnvironment).manage(managedCaptor.capture());
        managed.addAll(managedCaptor.getAllValues());
        for (Managed obj : managed) {
            obj.start();
        }

        try (Handle handle = dbi.open()) {
            handle.createCall("DROP TABLE twitter_accounts IF EXISTS").invoke();
            handle.createCall(
                    "CREATE TABLE twitter_accounts (id integer primary key, twitter_id integer, oauth_token varchar(255), oauth_secret varchar(255))")
                  .invoke();
            handle.createStatement("INSERT INTO twitter_accounts VALUES (?, ?, ?, ?)")
                  .bind(0, 1)
                  .bind(1, 123)
                  .bind(2, oauthToken)
                  .bind(3, oauthSecret)
                  .execute();
        }
    }

    @After
    public void tearDown() throws Exception {
        for (Managed obj : managed) {
            obj.stop();
        }
        this.dbi = null;
    }

    @Test
    public void createsDBI() {
        final Handle handle = dbi.open();

        final Query<Integer> twitterIds = handle.createQuery("SELECT twitter_id FROM twitter_accounts")
                                          .map(IntegerMapper.FIRST);
        assertThat(ImmutableList.copyOf(twitterIds))
                .containsOnly(123);
    }

    @Test
    public void canLookupByTwitterId() throws Exception {
        final AccountDAO dao = dbi.open(AccountDAO.class);
        assertThat(dao.findAccountByTwitterId("123"))
                .isEqualTo(account);
    }
    
    @Test
    public void doesntFindDoesntExist() throws Exception {
        final AccountDAO dao = dbi.open(AccountDAO.class);
        assertThat(dao.findAccountByTwitterId("1"))
                .isNull();
    }
    
    @Test
    public void doesntFindNull() throws Exception {
        final AccountDAO dao = dbi.open(AccountDAO.class);
        assertThat(dao.findAccountByTwitterId(null))
                .isNull();
    }
}

