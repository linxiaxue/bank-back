package com.bank.bank.service.impl;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.service.AccountService;

import com.bank.bank.service.WaterLogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class WaterLogServiceImplTest {

    @Autowired
    private WaterLogService waterLogService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createWaterLog() {

    }

    @Test
    void getList() {

    }

    @Test
    void findByWID() {

    }
}