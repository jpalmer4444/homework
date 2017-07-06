/*
 * AccountDAO
 */
package com.sproutsocial.db;

import com.sproutsocial.core.mapping.AccountMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface AccountDAO
{

  @SqlQuery("select id, oauth_token, oauth_secret, twitter_id from twitter_accounts where twitter_id = :id")
  @Mapper(AccountMapper.class)
  Account findAccountByTwitterId(@Bind("id") String id);

  void close();
}



