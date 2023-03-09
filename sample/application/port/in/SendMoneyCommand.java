package com.iaan.vup.module.sample.application.port.in;

import javax.validation.constraints.NotNull;

import com.iaan.vup.module.sample.domain.Account.AccountId;
import com.iaan.vup.module.sample.domain.Money;
import com.iaan.vup.module.sample.libs.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @category 입력 모델
 * @implNote 입력 유효성 검증하기 ( 유스케이스 클래스(서비스)의 책임은 아니지만 애플리케이션 계층의 책임 )
 * @apiNote 빌더 사용을 지양해야 된다. 유효하지 않은 상태의 불변 객체를 만드는 것에 대해 경고하지 못한다.
 * */
@Getter
@EqualsAndHashCode(callSuper = false) // TODO: 알아보기
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
    @NotNull
    private final AccountId sourceAccountId;

    @NotNull
    private final AccountId targetAccountId;

    @NotNull
    private final Money money;

    // 빌더 사용을 지양
    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;

        validateSelf();
    }

}
