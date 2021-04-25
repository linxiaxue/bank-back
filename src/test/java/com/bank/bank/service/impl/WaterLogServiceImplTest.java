package com.bank.bank.service.impl;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.service.AccountService;

import com.bank.bank.service.LoanRecordService;
import com.bank.bank.service.WaterLogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class WaterLogServiceImplTest {

    @Autowired
    private WaterLogService waterLogService;
    @Autowired
    private AccountService accountService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    public boolean equal(WaterLog waterLog,WaterLog waterLog1){
        if(waterLog.getClientId().equals(waterLog1.getClientId()) && waterLog.getType().equals(waterLog1.getType())
                && waterLog.getAccountChange().equals(waterLog1.getAccountChange()) && waterLog.getId().equals(waterLog1.getId())
                && waterLog.getCreateTime().equals(waterLog1.getCreateTime())){
            return true;
        }
        else
            return false;
    }

    @Test
    void createWaterLog() throws ParseException {
        WaterLog waterLog = new WaterLog();
        waterLog.setAccountChange(String.valueOf(100));
        waterLog.setClientId(1);
        Date nowDate = accountService.getNowTime();
        waterLog.setCreateTime(nowDate.toString());
        waterLog.setType(0);
        waterLogService.save(waterLog);
        int id = waterLog.getId();
        waterLogService.createWaterLog(id,String.valueOf(100),0);
        WaterLog waterLog1 = waterLogService.getById(id);
        assertEquals(String.valueOf(100),waterLog1.getAccountChange());

    }

    @Test
    void getList() throws ParseException {
        WaterLog waterLog = new WaterLog();
        waterLog.setAccountChange(String.valueOf(1000));
        waterLog.setClientId(2);
        waterLog.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        waterLog.setType(1);
        waterLogService.save(waterLog);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da = (Date) sdf.parse(waterLog.getCreateTime().toString());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(da);
        waterLog.setCreateTime(createTime);

        List<WaterLog> list = waterLogService.getList(2);
        WaterLog waterLog1 = list.get(list.size()-1);
        boolean isEqual = equal(waterLog,waterLog1);
        assertEquals(true,isEqual);

    }

    @Test
    void findByWID() throws ParseException {
        WaterLog waterLog = new WaterLog();
        waterLog.setAccountChange(String.valueOf(-200));
        waterLog.setType(2);
        waterLog.setClientId(1);
        waterLog.setCreateTime("Sat Jul 06 04:19:19 UTC 2021");
        waterLogService.save(waterLog);
        int id = waterLog.getId();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da = (Date) sdf.parse(waterLog.getCreateTime().toString());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(da);
        waterLog.setCreateTime(createTime);

        List<WaterLog> list = waterLogService.findByWID(1,id);
        boolean isEqual = equal(waterLog,list.get(0));
        assertEquals(true,isEqual);
    }
}