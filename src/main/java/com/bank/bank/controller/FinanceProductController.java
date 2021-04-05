package com.bank.bank.controller;


import com.bank.bank.entity.FinanceProduct;
import com.bank.bank.service.FinanceProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public Page<FinanceProduct> getProducts(){
        return financeProductService.getProducts();
    }
}
