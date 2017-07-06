/*
 * AccountService
 */
package com.sproutsocial.core.svc.impl;

import com.sproutsocial.db.Account;
import com.sproutsocial.core.svc.AccountService;
import com.sproutsocial.db.AccountDAO;

public class AccountServiceImpl implements AccountService {
    
    private AccountDAO dao;

    public AccountServiceImpl() {
    }

    public AccountServiceImpl(AccountDAO dao) {
        this.dao = dao;
    }

    public AccountDAO getDao() {
        return dao;
    }

    public void setDao(AccountDAO dao) {
        this.dao = dao;
    }

    @Override
    public Account findAccountByTwitterId(String id) {
        return dao.findAccountByTwitterId(id);
    }
    
}

