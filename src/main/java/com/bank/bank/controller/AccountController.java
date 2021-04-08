package com.bank.bank.controller;


import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 *
 *  账户接口
 *
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

    /**
     * 按照身份证查询账户
     * @param idn
     * @return
     */
    @GetMapping("/{idn}")
    @ApiOperation(value = "根据身份证查询账户")
    public ResponseEntity<Account> findByIDN(@PathVariable(value = "idn")@ApiParam(value = "身份证号") Long idn){
        return ResponseEntity.ok(accountService.getByIDN(idn));
    }

    /**
     * 新增账户
     */
    @PostMapping( "/create")
    @ApiOperation(value = "新增账户")
    public ResponseEntity<String> create(@RequestBody AccountDto accountDto){
        accountService.create(accountDto);
        return ResponseEntity.ok("新增账户成功");
    }

    /**
     * 存款
     */
    @GetMapping("/{id}/{money}")
    @ApiOperation(value = "存款")
    public ResponseEntity<String> deposit(@PathVariable(value = "id") Integer id,@PathVariable(value = "money") Double money){
        return ResponseEntity.ok(accountService.deposit(id,money));
    }


}
