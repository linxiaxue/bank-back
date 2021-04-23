package com.bank.bank.service.impl;

import com.bank.bank.entity.LoanRecord;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.LoanRecordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.collection.IsEmptyCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@Transactional
@SpringBootTest()
class LoanRecordServiceImplTest {

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
    void getByUserId() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(1);
        loanRecord.setTotalAccount(9999.99);
        loanRecord.setCurrentAccount(9999.99);
        loanRecord.setFine(10.00);
        loanRecordService.save(loanRecord);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da = (Date) sdf.parse(loanRecord.getExpirationTime());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String exTime = sdf.format(da);
        loanRecord.setExpirationTime(exTime);
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da1 = (Date) sdf1.parse(loanRecord.getCreateTime());
        sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf1.format(da1);
        loanRecord.setCreateTime(createTime);

        assertThat(loanRecordService.getByUserId(1), hasItem(loanRecord));
    }

    @Test
    void getById() throws Exception{
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(1);
        loanRecord.setTotalAccount(9999.99);
        loanRecord.setCurrentAccount(9999.99);
        loanRecord.setFine(10.9876);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(10.9876,loanRecord1.getFine());
    }

    @Test
    void freeFine() throws Exception{
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(1);
        loanRecord.setTotalAccount(888.88);
        loanRecord.setCurrentAccount(888.88);
        loanRecord.setFine(10.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        loanRecordService.freeFine(id);
        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(0.00,loanRecord1.getFine());

        assertEquals(0,loanRecordService.freeFine(-1));

        LoanRecord loanRecord2 = new LoanRecord();
        loanRecord2.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord2.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord2.setStatus(0);
        loanRecord2.setClientId(1);
        loanRecord2.setTotalAccount(888.88);
        loanRecord2.setCurrentAccount(888.88);
        loanRecord2.setFine(0.00);
        loanRecordService.save(loanRecord2);
        int id1 = loanRecord2.getId();
        assertEquals(0,loanRecordService.freeFine(id1));

        LoanRecord loanRecord3 = new LoanRecord();
        loanRecord3.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord3.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord3.setStatus(0);
        loanRecord3.setClientId(1);
        loanRecord3.setTotalAccount(888.88);
        loanRecord3.setCurrentAccount(888.88);
        loanRecord3.setFine(-1.00);
        loanRecordService.save(loanRecord3);
        int id2 = loanRecord3.getId();
        assertEquals(-1,loanRecordService.freeFine(id2));


    }

    @Test
    void repayTotal() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(1);
        loanRecord.setTotalAccount(1.00);
        loanRecord.setCurrentAccount(1.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        loanRecordService.repayTotal(id);
        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(0.00,loanRecord1.getCurrentAccount());

        assertEquals(-1,loanRecordService.repayTotal(-1));


    }

    @Test
    void repayTotal2() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(1);
        loanRecord.setTotalAccount(1.00);
        loanRecord.setCurrentAccount(1.00);
        loanRecord.setFine(1.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        assertEquals(-1,loanRecordService.repayTotal(id));
    }


    @Test
    void repay() throws Exception{
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        loanRecordService.repay(id,11.00);
        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(9.00,loanRecord1.getCurrentAccount());

        assertEquals(-1,loanRecordService.repay(id,-1.00));

        assertEquals(-1,loanRecordService.repay(-1,10.00));


    }

    @Test
    void repay2() throws Exception{
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(1.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();


        assertEquals(-1,loanRecordService.repay(id,11.00));



    }

    @Test
    void repay3() throws Exception{
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();

        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(-1, loanRecordService.repay(id,30.00));


        loanRecordService.repay(id,20.00);
        assertEquals(1,loanRecordService.getById(id).getStatus());



    }

    @Test
    void updateDate() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date d1 = sdf1.parse("Wed Jul 10 04:19:19 UTC 2021");

        loanRecordService.updateDate(d1);

        LoanRecord loanRecord1 = loanRecordService.getById(id);

        assertEquals(0.00,loanRecord1.getCurrentAccount());

    }

    @Test
    void updateDate2() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(1.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date d1 = sdf1.parse("Wed Jul 10 04:19:19 UTC 2021");

        loanRecordService.updateDate(d1);

        LoanRecord loanRecord1 = loanRecordService.getById(id);

        assertEquals(0.00,loanRecord1.getCurrentAccount());




    }
    @Test
    void updateDate3() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(1.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date d1 = sdf1.parse("Wed Jul 10 04:19:19 UTC 2021");

        Date d2 = sdf1.parse("Mon Jul 08 04:19:20 UTC 2021");

        loanRecordService.updateDate(d2);

        LoanRecord loanRecord1 = loanRecordService.getById(id);

        assertEquals(0.00,loanRecord1.getCurrentAccount());



    }

    @Test
    void updateDate4() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul ");
        loanRecord.setExpirationTime("Mon Jul ");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date d1 = sdf1.parse("Wed Jul 10 04:19:19 UTC 2021");


        assertEquals(-1,loanRecordService.updateDate(d1));


    }

    @Test
    void updateLoanFine() throws Exception {
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        loanRecord.setExpirationTime("Mon Jul 08 04:19:19 UTC 2021");
        loanRecord.setStatus(0);
        loanRecord.setClientId(2);
        loanRecord.setTotalAccount(20.00);
        loanRecord.setCurrentAccount(20.00);
        loanRecord.setFine(0.00);
        loanRecordService.save(loanRecord);
        int id = loanRecord.getId();
        loanRecordService.repay(id,10.00);
        loanRecordService.updateLoanFine(id,0.50);
        LoanRecord loanRecord1 = loanRecordService.getById(id);
        assertEquals(0.50,loanRecord1.getFine());

        assertEquals(-1,loanRecordService.updateLoanFine(id,-1.00));
    }
}
