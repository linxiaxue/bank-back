package com.bank.bank.service.impl;

import com.bank.bank.entity.Account;
import com.bank.bank.entity.LoanRecord;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.mapper.AccountMapper;
import com.bank.bank.mapper.LoanRecordMapper;
import com.bank.bank.mapper.WaterLogMapper;
import com.bank.bank.service.AccountService;
import com.bank.bank.service.LoanRecordService;
import com.bank.bank.service.WaterLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@Service
public class LoanRecordServiceImpl extends ServiceImpl<LoanRecordMapper, LoanRecord> implements LoanRecordService {

    @Autowired
    private LoanRecordMapper loanRecordMapper;

    @Lazy
    @Autowired
    private AccountService accountService;

    @Autowired
    private WaterLogService waterLogService;

    @Override
    public List<LoanRecord> getByUserId(Integer id) {

        LambdaQueryWrapper<LoanRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanRecord::getClientId, id).eq(LoanRecord::getStatus,0);
        return loanRecordMapper.selectList(wrapper);

    }

    @Override
    public LoanRecord getById(Integer id){
        LambdaQueryWrapper<LoanRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanRecord::getId,id);

        return getOne(wrapper);

    }

    @Override
    public Integer freeFine(Integer id){
        //查询id和罚金

        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null)
            return -1;

        Integer clientid=loan1.getClientId();
        Double fine=loan1.getFine();
        if(fine != null && fine > 0 ) {

            //更新
            loan1.setFine(0.0);
            saveOrUpdate(loan1);
            String str = "-" + fine;
            accountService.reduceAccountBalance(clientid,fine);
            waterLogService.createWaterLog(clientid, str, 2);

            return 1;
        }else if(fine == 0){
            return 0;
        }else {
            return  -1;
        }


    }

    @Override
    public Integer repayTotal(Integer id){
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null)
            return -1;
        Double fine=loan1.getFine();
        if(fine==null||fine>0){
            return -1;
        }
        Integer clientid=loan1.getClientId();
        Double currentAccount=loan1.getCurrentAccount();
        loan1.setCurrentAccount(0.0);
        loan1.setStatus(1);
        saveOrUpdate(loan1);

        String str="-"+currentAccount;
        waterLogService.createWaterLog(clientid,str,1);
        accountService.reduceAccountLoad(clientid,currentAccount);


        return 1;
    }

    @Override
    public Integer repay(Integer id,Double account){
        if(account<0){
            return -1;
        }
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null)
            return -1;
        Double fine=loan1.getFine();
        if(fine==null||fine>0){
            return -1;
        }

        Integer clientid=loan1.getClientId();
        Double currentAccount=loan1.getCurrentAccount();
        if(currentAccount<account){
            return -1;
        }
        LoanRecord loanRecord=new LoanRecord();
        loanRecord.setCurrentAccount(currentAccount-account);
        if(currentAccount.equals(account)){
            loanRecord.setStatus(1);
        }

        UpdateWrapper<LoanRecord> loan=new UpdateWrapper<>();
        loan.eq("id",id);
        int ret=loanRecordMapper.update(loanRecord,loan);
        if(ret>0){

            String str="-"+account;

            waterLogService.createWaterLog(clientid,str,1);
            accountService.reduceAccountLoad(clientid,account);

        }
        return ret;

    }
    @Override
    public Integer updateDate(Date date){


        LambdaQueryWrapper<LoanRecord> loanRecordQueryWrapper = new QueryWrapper<LoanRecord>().lambda();
        loanRecordQueryWrapper.eq(LoanRecord::getStatus,0);
        List<LoanRecord> loanRecord=loanRecordMapper.selectList(loanRecordQueryWrapper);
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        for(LoanRecord loanRecord1:loanRecord){
            try {
                Date dueDate = sdf1.parse(loanRecord1.getExpirationTime());
                if(dueDate.before(date)){
                    double fine=loanRecord1.getCurrentAccount()*0.05;
                    Account account=accountService.getAccountById(loanRecord1.getClientId());
                    if(account.getBalance()>=fine){
                        accountService.reduceAccountBalance(loanRecord1.getClientId(),fine);
                        String str="-"+fine;
                        waterLogService.createWaterLog(account.getId(),str,2);
                        updateLoanFine(loanRecord1.getId(),0);

                        if(account.getBalance()-fine>=loanRecord1.getCurrentAccount()){
                            accountService.reduceAccountBalance(loanRecord1.getClientId(),loanRecord1.getCurrentAccount());
                            repayTotal(loanRecord1.getId());
                        }

                    }
                    else{
                        updateLoanFine(loanRecord1.getId(),fine);
                    }
                }
            }catch (Exception e){
                System.out.println(e);
                return -1;
            }



        }
        return 1;
    }



    public int updateLoanFine(Integer id,double fine){
        if(fine<0)
            return -1;
        UpdateWrapper<LoanRecord> loanRecordUpdateWrapper=new UpdateWrapper<>();
        loanRecordUpdateWrapper.eq("id",id);
        LoanRecord loanRecord=new LoanRecord();
        loanRecord.setFine(fine);
        int ret=loanRecordMapper.update(loanRecord,loanRecordUpdateWrapper);
        return ret;
    }

}
