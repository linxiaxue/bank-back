package com.bank.bank.service.impl;

import com.bank.bank.service.ClientProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest()
class ClientProductServiceImplTest {
    @Autowired
    private ClientProductService clientProductService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void computeBenefit() {
    }

    @Test
    void getClientPros() {
    }
}