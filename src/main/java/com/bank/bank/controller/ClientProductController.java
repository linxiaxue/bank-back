package com.bank.bank.controller;


import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.dto.ClientProductResponseDto;
import com.bank.bank.service.ClientProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 *  客户-产品接口
 *
 * @author yuwen
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/bank/clientroduct")
public class ClientProductController {
    private ClientProductService clientProductService;
    /**
     * 查看用户购买的理财产品
     */
    @GetMapping("/{cid}")
    @ApiOperation(value = "查看用户购买的理财产品")
    public ResponseEntity<List<ClientProductResponseDto>> getClientPros(@PathVariable(value = "cid") Integer cid ){
        return ResponseEntity.ok(clientProductService.getClientPros(cid));
    }
}
