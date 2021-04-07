package com.bank.bank.controller;


import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.entity.FinanceProduct;
import com.bank.bank.service.FinanceProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *  理财产品
 *
 *
 * @author yuwen
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/bank/finance-product")
public class FinanceProductController {

    @Autowired
    private FinanceProductService financeProductService;

    /**
     * 查看理财产品列表
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查看理财产品列表")
    public List<FinanceProduct> getProducts(){
        return financeProductService.getProducts();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户id查看可购买的产品列表")
    public List<FinanceProduct> getProById(@PathVariable(value = "id") Integer clientId){
        return financeProductService.getProById(clientId);
    }

    @GetMapping("/buyProduct")
    @ApiOperation(value = "用户购买理财产品")
    public ResponseEntity buyPro(@RequestBody ClientProductRequestDto clientProductRequestDto){
        return ResponseEntity.ok(financeProductService.buyPro(clientProductRequestDto));

    }


}
