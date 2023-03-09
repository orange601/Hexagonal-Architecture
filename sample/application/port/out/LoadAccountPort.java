package com.iaan.vup.module.sample.application.port.out;

import java.time.LocalDateTime;

import com.iaan.vup.module.sample.domain.Account;
import com.iaan.vup.module.sample.domain.Account.AccountId;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
