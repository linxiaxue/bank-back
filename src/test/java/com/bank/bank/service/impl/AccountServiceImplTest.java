package com.bank.bank.service.impl;

import com.bank.bank.BankApplication;
import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.entity.LoanRecord;
import com.bank.bank.service.LoanRecordService;
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

    @Autowired
    private LoanRecordService loanRecordService;

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

        try{
            Account account1=accountService.getByIDN(11111111L);
            fail();
        }catch (Exception e){
            assertEquals("账号不存在",e.getMessage());
        }
    }





    @Test
    void getAccountById() {
        Account account = accountService.getAccountById(1);
        assertEquals("test",account.getName());
        try{
            Account account1=accountService.getAccountById(-1);
            fail();
        }catch (Exception e){
            assertEquals("账号不存在",e.getMessage());
        }
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

        Account account1 = new Account();
        account1.setIdNumber(121l);
        account1.setLoanAmount(100.00);
        account1.setBalance(600000.00);
        accountService.save(account1);
        int id1 = account1.getId();
        accountService.reduceAccountLoad(id1,1.00);
        double now1 = accountService.getAccountById(id1).getLoanAmount();
        assertEquals(99.00,now1);

        Account account2 = new Account();
        account2.setIdNumber(122l);
        account2.setLoanAmount(100.00);
        account2.setBalance(1000.00);
        accountService.save(account2);
        int id2 = account2.getId();
        accountService.reduceAccountLoad(id2,1.00);
        double now2 = accountService.getAccountById(id2).getLoanAmount();
        assertEquals(99.00,now2);

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

        Account account1 = new Account();
        account1.setIdNumber(12341l);
        account1.setLoanAmount(100.00);
        account1.setBalance(600000.00);
        accountService.save(account1);
        int id1 = account1.getId();
        accountService.reduceAccountBalance(id1,1.00);
        double now1 = accountService.getAccountById(id1).getBalance();
        assertEquals(599999.00,now1);

        Account account2 = new Account();
        account2.setIdNumber(12342l);
        account2.setLoanAmount(100.00);
        account2.setBalance(2.00);
        accountService.save(account2);
        int id2 = account2.getId();
        accountService.reduceAccountBalance(id2,1.00);
        double now2 = accountService.getAccountById(id2).getBalance();
        assertEquals(1.00,now2);

        Account account3 = new Account();
        account3.setIdNumber(12343l);
        account3.setLoanAmount(100.00);
        account3.setBalance(0.00);
        accountService.save(account3);
        int id3 = account3.getId();
        assertEquals(-1,accountService.reduceAccountBalance(id3,1.00));




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

        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:UTC");
        loanRecord.setExpirationTime("Mon Jul 08 04:");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        try{
            accountService.addTime();
            fail();
        }catch (RuntimeException e){
            assertTrue(true);
        }


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
