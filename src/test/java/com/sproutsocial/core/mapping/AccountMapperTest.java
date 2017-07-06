/*
 * Test for AccountMapper
 */
package com.sproutsocial.core.mapping;

import com.sproutsocial.db.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.skife.jdbi.v2.StatementContext;

public class AccountMapperTest {
    
    private final ResultSet resultSet = mock(ResultSet.class);

    private final StatementContext ctx = mock(StatementContext.class);

    private final int twitterId = 123;
    private final String oauthToken = "token";
    private final String oauthSecret = "secret";

    private final Account account = new Account(1, oauthToken, oauthSecret, twitterId);

    private final AccountMapper mapper = new AccountMapper();

    @Before
    public void setup() throws SQLException {
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("oauth_token")).thenReturn(account.getOauthToken());
        when(resultSet.getString("oauth_secret")).thenReturn(account.getOauthSecret());
        when(resultSet.getInt("twitter_id")).thenReturn(account.getTwitterId());
    }

    @Test
    public void mapTest() throws SQLException {
        Account accountResult = mapper.map(0, resultSet, ctx);
        assertThat(accountResult).isEqualTo(account);

    }
}
