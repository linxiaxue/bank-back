package com.bank.bank.service.impl;

import com.bank.bank.service.FinanceProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class FinanceProductServiceImplTest {
    @Autowired
    private FinanceProductService financeProductService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProducts() {
    }

    @Test
    void getProById() {
    }

    @Test
    void buyPro() {
    }
}