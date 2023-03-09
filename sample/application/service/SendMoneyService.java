package com.iaan.vup.module.sample.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.iaan.vup.module.sample.application.port.in.SendMoneyCommand;
import com.iaan.vup.module.sample.application.port.in.SendMoneyUseCase;
import com.iaan.vup.module.sample.application.port.out.AccountLock;
import com.iaan.vup.module.sample.application.port.out.LoadAccountPort;
import com.iaan.vup.module.sample.application.port.out.UpdateAccountStatePort;
import com.iaan.vup.module.sample.domain.Account;
import com.iaan.vup.module.sample.domain.Account.AccountId;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    /**
     * 1. 비즈니스 규칙 검증
     * 2. 모델 상태 조작
     * 3. 출력 값 반환
     * */
    @Override
    public boolean sendMoney(SendMoneyCommand command) {
    	
        checkThreshold(command);
        
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId(), baselineDate);
        Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId(), baselineDate);

        AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.getMoney(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.getMoney(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if(command.getMoney().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.getMoney());
        }
    }

}