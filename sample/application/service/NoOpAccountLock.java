package com.iaan.vup.module.sample.application.service;

import org.springframework.stereotype.Component;

import com.iaan.vup.module.sample.application.port.out.AccountLock;
import com.iaan.vup.module.sample.domain.Account.AccountId;

@Component
class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }

}
