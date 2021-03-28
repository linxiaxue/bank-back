package com.bank.bank.controller;


import com.bank.bank.entity.Account;
import com.bank.bank.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/bank/account")
@Api(value = "账户接口")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/{idn}")
    @ApiOperation(value = "根据身份证查询账户")
    public ResponseEntity<Account> findByIDN(@PathVariable(value = "idn")@ApiParam(value = "身份证号") Long idn){
        return ResponseEntity.ok(accountService.getByIDN(idn));
    }
}
