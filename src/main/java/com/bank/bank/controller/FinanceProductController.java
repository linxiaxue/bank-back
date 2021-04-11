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

import java.text.ParseException;
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

    /**
     * 根据用户id查看可购买的产品列表
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    @ApiOperation(value = "根据用户id查看可购买的产品列表")
    public List<FinanceProduct> getProByCId(@PathVariable(value = "cid") Integer cid){
        return financeProductService.getProById(cid);
    }

    /**
     * 用户购买理财产品
     * @param clientProductRequestDto
     * @return
     */
    @PostMapping("/buyProduct")
    @ApiOperation(value = "用户购买理财产品")
    public ResponseEntity<String> buyPro(@RequestBody ClientProductRequestDto clientProductRequestDto) throws ParseException {
        return ResponseEntity.ok(financeProductService.buyPro(clientProductRequestDto));

    }




}
