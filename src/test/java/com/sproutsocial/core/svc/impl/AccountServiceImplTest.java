/*
 * Tests AccountService
 */
package com.sproutsocial.core.svc.impl;

import com.sproutsocial.core.svc.AccountService;
import com.sproutsocial.db.Account;
import com.sproutsocial.db.AccountDAO;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountServiceImplTest {
    
    private final String twitterId = "123";
    private final String oauthToken = "token";
    private final String oauthSecret = "secret";
    
    private final Account account = new Account(1, oauthToken, oauthSecret, 123);
    
    private final AccountDAO accountDAO = mock(AccountDAO.class);
    private final AccountService accountService = new AccountServiceImpl(accountDAO);
    
    @Before
    public void setup(){
        when(accountService.findAccountByTwitterId(eq(twitterId))).thenReturn(account);
    }
    
    @Test 
    public void testAccountServiceCallsDAO(){
        assertThat(accountService.findAccountByTwitterId(twitterId)).isEqualTo(account);
        verify(accountDAO).findAccountByTwitterId(twitterId);
    }
    
}
