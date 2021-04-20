package com.bank.bank.service.impl;

import com.bank.bank.BankApplication;
import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest()
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getByIDN() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAge(18);
        accountDto.setName("test-lics");
        accountDto.setIdNumber(11223344L);
        accountDto.setBalance(0.0);
        accountService.create(accountDto);

        Account account = accountService.getByIDN(11223344L);
        assertEquals("test-lics",account.getName());
    }

    @Test
    void getAccountById() {


        Account account = accountService.getAccountById(1);
        assertEquals("test",account.getName());

    }

    @Test
    void create() {

    }

    @Test
    void reduceAccountLoad() {
    }

    @Test
    void reduceAccountBalance() {
    }

    @Test
    void getNowTime() {
    }

    @Test
    void addTime() {
    }

    @Test
    void deposit() {
    }
}