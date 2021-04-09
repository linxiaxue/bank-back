package com.bank.bank.controller;

import com.bank.bank.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 基础接口
 */
@RestController
@RequestMapping("/bank")
@Api(value = "基础")
public class baseController {
    @Autowired
    private AccountService accountService;

    /**
     * 获取当前时间
     * @return
     */
    @GetMapping("/time")
    @ApiOperation(value = "获取当前时间")
    public ResponseEntity<Date> getNowTime(){
        return ResponseEntity.ok(accountService.getNowTime());
    }

    @GetMapping("/addTime")
    @ApiOperation(value = "增加一天")
    public ResponseEntity<String> addTime(){
        return ResponseEntity.ok(accountService.addTime());
    }

}
