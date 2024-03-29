package com.iaan.vup.sample.adapter.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.iaan.vup.module.sample.adapter.web.SendMoneyController;
import com.iaan.vup.module.sample.application.port.in.SendMoneyCommand;
import com.iaan.vup.module.sample.application.port.in.SendMoneyUseCase;
import com.iaan.vup.module.sample.domain.Account.AccountId;
import com.iaan.vup.module.sample.domain.Money;

import static org.mockito.BDDMockito.*;

@WebMvcTest(controllers = SendMoneyController.class)
public class SendMoneyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendMoneyUseCase sendMoneyUseCase;

    @Test
    void testSendMoney() throws Exception {

        mockMvc.perform(post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                41L, 42L, 500)
                .header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        then(sendMoneyUseCase).should()
                .sendMoney(eq(new SendMoneyCommand(
                        new AccountId(41L),
                        new AccountId(42L),
                        Money.of(500L))));
    }

}
