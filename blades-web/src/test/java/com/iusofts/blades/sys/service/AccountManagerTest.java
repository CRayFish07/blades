package com.iusofts.blades.sys.service;

import com.iusofts.blades.sys.model.Account;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by Ivan Shen on 2017/5/3.
 */
public class AccountManagerTest extends BaseTests {
    
    @Resource
    private AccountService accountManager;
    
    @Test
    public void save() throws Exception {
        Account account = new Account();
        account.setUsername("admin");
        account.setPassword("123456");
        account.setId("1");
        this.accountManager.save(account);
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void getByUsername() throws Exception {
    }

    @Test
    public void updatePassword() throws Exception {
    }

    @Test
    public void login() throws Exception {
    }

    @Test
    public void isUserNameExist() throws Exception {
    }

}