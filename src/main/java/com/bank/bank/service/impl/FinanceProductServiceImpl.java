package com.bank.bank.service.impl;

import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.entity.Account;
import com.bank.bank.entity.ClientProduct;
import com.bank.bank.entity.FinanceProduct;
import com.bank.bank.exception.BalanceNotEnoughException;
import com.bank.bank.mapper.AccountMapper;
import com.bank.bank.mapper.ClientProductMapper;
import com.bank.bank.mapper.FinanceProductMapper;
import com.bank.bank.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@Service
public class FinanceProductServiceImpl extends ServiceImpl<FinanceProductMapper, FinanceProduct> implements FinanceProductService {

    @Autowired
    public AccountService accountService;

    @Autowired
    public LoanRecordService loanRecordService;

    @Autowired
    public WaterLogService waterLogService;

    @Autowired
    public ClientProductService clientProductService;

    @Autowired
    public AccountMapper accountMapper;

    @Autowired
    public FinanceProductMapper financeProductMapper;

    @Autowired
    public ClientProductMapper clientProductMapper;


    @Override
    public List<FinanceProduct> getProducts() {
        return financeProductMapper.selectList(null);
    }

    @Override
    public List<FinanceProduct> getProById(Integer clientId) {
        Account account = accountService.getById(clientId);
        int creditRate = account.getCreditRate();
        LambdaQueryWrapper<FinanceProduct> wrapper = new QueryWrapper<FinanceProduct>().lambda();
        if (creditRate == 1){
            return getProducts();
        }else if (creditRate == 2){
            wrapper.eq(FinanceProduct::getType,2 ).or().eq(FinanceProduct::getType,0);
        }else if(creditRate == 3){
            wrapper.eq(FinanceProduct::getType,0);
        }else {
            return  null;
        }
        return financeProductMapper.selectList(wrapper);
    }

    @Override
    public String buyPro(ClientProductRequestDto clientProductRequestDto){
        String re = "";

        //要交罚金
        if(loanRecordService.freeFine(clientProductRequestDto.getClientId()) >= 0){
            Account account = accountService.getById(clientProductRequestDto.getClientId());
            FinanceProduct financeProduct = getById(clientProductRequestDto.getFpdId());
            LambdaQueryWrapper<ClientProduct> wrapper = new QueryWrapper<ClientProduct>().lambda();
            wrapper.eq(ClientProduct::getClientId,account.getId());
            wrapper.eq(ClientProduct::getFpdId,financeProduct.getId());
            ClientProduct clientProduct = clientProductMapper.selectOne(wrapper);
            if(clientProduct == null){
                clientProduct = new ClientProduct();
                clientProduct.setPrincipal(0.0);
            }
            if(account.getBalance() >= clientProductRequestDto.getPrincipal()){
                accountService.reduceAccountBalance(clientProductRequestDto.getClientId(),clientProductRequestDto.getPrincipal());

                clientProduct.setBuyTime(new Date().toString());
                clientProduct.setClientId(clientProductRequestDto.getClientId());
                clientProduct.setFpdId(clientProductRequestDto.getFpdId());
                clientProduct.setPrincipal(clientProductRequestDto.getPrincipal() + clientProduct.getPrincipal());
                clientProduct.setInterestRate(financeProduct.getInterestRate());
                clientProduct.setType(financeProduct.getType());
                clientProduct.setProfit(0.0);
                clientProductService.saveOrUpdate(clientProduct);
                waterLogService.createWaterLog(clientProduct.getClientId(),"-"+clientProductRequestDto.getPrincipal(),3);
                re = "购买成功";

            }else {
                throw new BalanceNotEnoughException();

            }
        }else {
            throw new BalanceNotEnoughException();

        }
        return re;
    }


}
