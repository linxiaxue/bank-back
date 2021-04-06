package com.bank.bank.service;

import com.bank.bank.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
public interface AccountService extends IService<Account> {

    public Account getByIDN(Long idn);

    public Account getAccountById(Integer clientid);
    public int reduceAccountLoad(Integer clientid,double reducedAccount);
    public int reduceAccountBalance(Integer clientid,double cost);
}
