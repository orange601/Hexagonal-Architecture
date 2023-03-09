package com.iaan.vup.module.sample.application.port.out;

import com.iaan.vup.module.sample.domain.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);

}
