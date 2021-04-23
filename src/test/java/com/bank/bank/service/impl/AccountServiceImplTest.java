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

import java.util.Date;

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
        AccountDto accountDto = new AccountDto();
        accountDto.setAge(33);
        accountDto.setName("abcd");
        accountDto.setIdNumber(1234567L);
        accountDto.setBalance(0.0);
        accountService.create(accountDto);
        Account account = accountService.getByIDN(1234567L);
        assertEquals("abcd",account.getName());
    }

    @Test
    void reduceAccountLoad() {
        Account account = new Account();
        account.setIdNumber(123l);
        account.setLoanAmount(500.00);
        accountService.save(account);
        int id = account.getId();
        accountService.reduceAccountLoad(id,200.00);
        double now = accountService.getAccountById(id).getLoanAmount();
        assertEquals(300.00,now);
    }

    @Test
    void reduceAccountBalance() {
        Account account = new Account();
        account.setIdNumber(12345l);
        account.setLoanAmount(100.00);
        account.setBalance(999.00);
        accountService.save(account);
        int id = account.getId();
        accountService.reduceAccountBalance(id,333.00);
        double now = accountService.getAccountById(id).getBalance();
        assertEquals(666.00,now);
    }

    @Test
    void getNowTime() {
        Date a = accountService.getNowTime();
        Date b = accountService.getAccountById(1).getNowDate();
        assertEquals(a,b);
    }

    @Test
    void addTime() {
        Date a = accountService.getNowTime();
        accountService.addTime();
        Date b = accountService.getNowTime();
        int days = (int) ((b.getTime() - a.getTime()) / (1000*3600*24));
        assertEquals(1,days);
    }

    @Test
    void deposit() throws Exception {
        Account account = new Account();
        account.setIdNumber(2333l);
        account.setLoanAmount(100.00);
        account.setBalance(233.00);
        accountService.save(account);
        int id = account.getId();
        accountService.deposit(id,200.00);
        double now = accountService.getAccountById(id).getBalance();
        assertEquals(433.00,now);
    }
}