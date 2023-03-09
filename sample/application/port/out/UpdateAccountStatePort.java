package com.iaan.vup.module.sample.application.port.out;

import com.iaan.vup.module.sample.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
