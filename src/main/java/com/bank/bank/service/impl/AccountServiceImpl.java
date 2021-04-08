package com.bank.bank.service.impl;

import com.bank.bank.dto.AccountDto;
import com.bank.bank.entity.Account;
import com.bank.bank.mapper.AccountMapper;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.ClientProductService;
import com.bank.bank.service.LoanRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Autowired
    private LoanRecordService loanRecordService;

    @Autowired
    private ClientProductService clientProductService;

    private AccountMapper accountMapper;

    @Override
    public Account getByIDN(Long idn){
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getIdNumber,idn);

        return getOne(wrapper);
    }

    @Override
    public Account getAccountById(Integer clientid){
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("id",clientid);
        Account account=accountMapper.selectOne(accountQueryWrapper);
        return account;
    }


    @Override
    public void create(AccountDto accountDto){
        Account account = new Account();
        account.setAge(accountDto.getAge());
        account.setBalance(accountDto.getBalance());
        account.setCreditRate(accountDto.getCreditRate());
        saveOrUpdate(account);
    }

    @Override
    public int reduceAccountLoad(Integer clientid,double reducedAccount){
        UpdateWrapper<Account> accountUpdateWrapper=new UpdateWrapper<>();
        accountUpdateWrapper.eq("id",clientid);
        Account account=new Account();
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("id",clientid);
        Account account1=accountMapper.selectOne(accountQueryWrapper);
        double balance=account1.getBalance();
        double past_loan_amount=account1.getLoanAmount();
        double current_loan_amount=past_loan_amount-reducedAccount;
        account.setLoanAmount(current_loan_amount);
        if(balance-current_loan_amount>500000){
            account.setCreditRate(3);
        }
        else if(balance>=current_loan_amount){
            account.setCreditRate(2);
        }
        else {
            account.setCreditRate(1);
        }

        int ret=accountMapper.update(account,accountUpdateWrapper);
        return ret;
    }

    @Override
    public int reduceAccountBalance(Integer clientid,double cost){
        UpdateWrapper<Account> accountUpdateWrapper=new UpdateWrapper<>();
        accountUpdateWrapper.eq("id",clientid);
        Account account=new Account();
        QueryWrapper<Account> accountQueryWrapper=new QueryWrapper<>();
        accountQueryWrapper.eq("id",clientid);
        Account account1=accountMapper.selectOne(accountQueryWrapper);
        double past_balance=account1.getBalance();
        double loan_amount=account1.getLoanAmount();
        double current_balance=past_balance-cost;
        if(current_balance<0){
            return -1;
        }

        account.setBalance(current_balance);
        if(current_balance-loan_amount>500000){
            account.setCreditRate(3);
        }
        else if(current_balance>=loan_amount){
            account.setCreditRate(2);
        }
        else {
            account.setCreditRate(1);
        }

        int ret=accountMapper.update(account,accountUpdateWrapper);
        return ret;
    }

    @Override
    public Date getNowTime(){
        Account account1 = getById(1);
        return account1.getNowDate();
    }

    @Override
    public String addTime(){
        //更新一天
        Account account1 = getById(1);
        Date date = account1.getNowDate();
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.DATE, 1);
        Date dateNew = cld.getTime();
        account1.setNowDate(dateNew);
        saveOrUpdate(account1);

        loanRecordService.updateDate(dateNew);
        clientProductService.computeBenefit();

        return "日期更新成功";


    }

    @Override
    public String deposit(Integer id, Double money){
        Account account = getById(id);
        account.setBalance(account.getBalance() + money);
        saveOrUpdate(account);
        return "存款成功";
    }
}
