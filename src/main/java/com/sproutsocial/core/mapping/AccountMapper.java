/*
 * AccountMapper
 */
package com.sproutsocial.core.mapping;

import com.sproutsocial.db.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AccountMapper implements ResultSetMapper<Account>
{
  @Override
  public Account map(int index, ResultSet r, StatementContext ctx) throws SQLException
  {
    return new Account(r.getInt("id"), r.getString("oauth_token"), r.getString("oauth_secret"), r.getInt("twitter_id"));
  }
}
