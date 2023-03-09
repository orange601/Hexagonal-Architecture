package com.iaan.vup.module.sample.application.port.in;

public interface SendMoneyUseCase {
    boolean sendMoney(SendMoneyCommand command);
}


/**
 * 
 * 원래 여기에 있었지만 따로 생성함

@Value
@EqualsAndHashCode(callSuper = false)
class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
    @NotNull 
    private final AccountId sourceAccountId;
    @NotNull 
    private final AccountId targetAccountId;
    @NotNull 
    private final Money money;

    public SendMoneyCommand(AccountId sourceAccountId, AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
 * */