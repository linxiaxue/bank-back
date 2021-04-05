package com.bank.bank.controller;


import com.bank.bank.entity.Account;
import com.bank.bank.entity.LoanRecord;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.LoanRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  贷款记录
 * </p>
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
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "按照用户id查询对应的贷款记录")
    public Page<LoanRecord> findByIDN(@PathVariable(value = "id")@ApiParam(value = "用户id") Long id){
        return loanRecordService.getByUserId(id);
    }
}
