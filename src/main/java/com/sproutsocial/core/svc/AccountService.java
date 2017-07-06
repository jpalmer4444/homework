/*
 * AccountService Interface
 */
package com.sproutsocial.core.svc;

import com.sproutsocial.db.Account;

public interface AccountService {
    
    Account findAccountByTwitterId(String id);
    
}
