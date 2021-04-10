package com.bank.bank.controller;


import com.bank.bank.entity.Account;
import com.bank.bank.entity.LoanRecord;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.LoanRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 *
 *  贷款记录
 *
 *
 * @author yuwen
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/bank/loan-record")
public class LoanRecordController {

    @Autowired
    private LoanRecordService loanRecordService;

    /**
     * 按照用户id查询对应的贷款记录
     * @param id
     * @return
     */
    @GetMapping( "/user/{id}")
    @ApiOperation(value = "按照用户id查询对应的贷款记录")
    public ResponseEntity<List<LoanRecord>> findByUserID(@PathVariable(value = "id")@ApiParam(value = "用户id") Integer id){
        return ResponseEntity.ok(loanRecordService.getByUserId(id));
    }
    /**
     * 按照贷款id查询对应的贷款记录
     * @param id
     * @return
     */
    @GetMapping( "/loan/{id}")
    @ApiOperation(value = "按照贷款id查询对应的贷款记录")
    public ResponseEntity<LoanRecord> findByID(@PathVariable(value = "id")@ApiParam(value = "贷款id") Integer id){
        return ResponseEntity.ok(loanRecordService.getById(id));
    }
    /**
     * 清缴罚款
     * @param id
     * @return
     */
    @GetMapping( "/fine/{id}")
    @ApiOperation(value = "按照贷款id缴清罚金")
    public ResponseEntity<String> freeFine(@PathVariable(value = "id")@ApiParam(value = "贷款id") Integer id){
        return ResponseEntity.ok(loanRecordService.freeFine(id) > 0 ? "罚金已缴清":"当前无罚金");
    }

    /**
     * 全部还款
     * @param id
     * @return
     */
    @GetMapping( "/repay/{id}")
    @ApiOperation(value = "按照贷款id全部还款")
    public ResponseEntity<Integer> repayTotoal(@PathVariable(value = "id")@ApiParam(value = "贷款id") Integer id){
        return ResponseEntity.ok(loanRecordService.repayTotal(id));
    }

    /**
     * 部分还款
     * @param id,account
     * @return
     */

    @PostMapping( "/repay")
    @ApiOperation(value = "按照贷款id部分还款")
    public ResponseEntity<Integer> repayTotoal(@RequestParam("id")@ApiParam(value = "贷款id") Integer id,@RequestParam("account") @ApiParam(value = "还款金额")Double account ){
        return ResponseEntity.ok(loanRecordService.repay(id,account));
    }

    /**
     * 更新新的一天
     * @param
     * @return
     */
    @GetMapping( "/updateDate")
    @ApiOperation(value = "更新到新的一天")
    public ResponseEntity<Integer> updateDate(){
        return ResponseEntity.ok(loanRecordService.updateDate());
    }



}
