package com.iaan.vup.module.sample.adapter.web;

// https://github.com/ftoumHub/buckpal
// https://github.com/Meet-Coder-Study/Get-Your-Hands-Dirty-on-Clean-Architecture/tree/main/chapter4

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iaan.vup.module.sample.application.port.in.SendMoneyCommand;
import com.iaan.vup.module.sample.application.port.in.SendMoneyUseCase;
import com.iaan.vup.module.sample.domain.Account.AccountId;
import com.iaan.vup.module.sample.domain.Money;

@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    /**
     * @category 한 계좌에서 다른 계좌로 송금
     * */
    @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    void sendMoney(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Long amount) {

        SendMoneyCommand command = new SendMoneyCommand(new AccountId(sourceAccountId),
                                                        new AccountId(targetAccountId),
                                                        Money.of(amount));
        
        sendMoneyUseCase.sendMoney(command);
    }

}
