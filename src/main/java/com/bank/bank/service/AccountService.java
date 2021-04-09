package com.bank.bank.service;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@Transactional(readOnly = true)
public interface AccountService extends IService<Account> {

    Account getByIDN(Long idn);

    Account getAccountById(Integer clientid);

    @Transactional
    int reduceAccountLoad(Integer clientid,double reducedAccount);

    @Transactional
    int reduceAccountBalance(Integer clientid,double cost);

    void create(AccountDto accountDto);

    Date getNowTime();

    @Transactional(rollbackFor = Exception.class)
    String addTime();

    @Transactional(rollbackFor = Exception.class)
    String deposit(Integer id,Double money);
}
