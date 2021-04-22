package com.bank.bank.service.impl;

import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.dto.ClientProductResponseDto;
import com.bank.bank.entity.Account;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.ClientProductService;
import com.bank.bank.service.FinanceProductService;
import com.baomidou.mybatisplus.extension.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class ClientProductServiceImplTest {
    @Autowired
    private ClientProductService clientProductService;

    @Autowired
    private FinanceProductService financeProductService;

    @Autowired
    private AccountService accountService;

    private int id = 0;

    @BeforeEach
    void setUp() throws ParseException {
        Account account = new Account();
        account.setLoanAmount(0.0);
        account.setBalance(10000.0);
        account.setIdNumber(123456L);
        account.setName("name");
        accountService.save(account);
        ClientProductRequestDto dto = new ClientProductRequestDto();
        dto.setClientId(account.getId());
        dto.setFpdId(1);
        dto.setPrincipal(100.0);
        String s = financeProductService.buyPro(dto);
        assertEquals("购买成功",s);

        id = account.getId();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void computeBenefit() throws ParseException {

        clientProductService.computeBenefit();
        List<ClientProductResponseDto> list = clientProductService.getClientPros(id);
        ClientProductResponseDto dto = list.get(0);
        if (dto.getFpdId() == 1){
            Double profit = dto.getPrincipal() * dto.getInterestRate();
            assertEquals(profit,dto.getProfit());
        }

    }

    @Test
    void getClientPros() throws ParseException {

        List<ClientProductResponseDto> list = clientProductService.getClientPros(id);
        assertEquals(1,list.size());
    }
}