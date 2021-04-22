package com.bank.bank.service.impl;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.entity.Account;
import com.bank.bank.entity.ClientProduct;
import com.bank.bank.entity.FinanceProduct;
import com.bank.bank.exception.BalanceNotEnoughException;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class FinanceProductServiceImplTest {
    @Autowired
    private FinanceProductService financeProductService;

    @Autowired
    private ClientProductService clientProductService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProducts() {
        assertEquals(3,financeProductService.getProducts().size());
    }

    @Test
    void getProById() {

        assertEquals(3,financeProductService.getProById(3).size());
    }

    @Test
    void buyPro() throws ParseException {
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

        Account account1 = new Account();
        account1.setLoanAmount(0.0);
        account1.setBalance(100.0);
        account1.setIdNumber(123456L);
        account1.setName("name");
        accountService.save(account1);
        ClientProductRequestDto dto1 = new ClientProductRequestDto();
        dto1.setClientId(account1.getId());
        dto1.setFpdId(1);
        dto1.setPrincipal(101.0);

        assertThrows(BalanceNotEnoughException.class,() -> financeProductService.buyPro(dto1));

    }
}