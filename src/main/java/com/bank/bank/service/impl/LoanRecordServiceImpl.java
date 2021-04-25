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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public List<LoanRecord> getByUserId(Integer id) throws ParseException {

        LambdaQueryWrapper<LoanRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanRecord::getClientId, id).eq(LoanRecord::getStatus,0);
        List<LoanRecord> list = loanRecordMapper.selectList(wrapper);
        for (LoanRecord record : list){
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date da = (Date) sdf.parse(record.getExpirationTime());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String exTime = sdf.format(da);
            record.setExpirationTime(exTime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date da2 = (Date) sdf2.parse(record.getCreateTime());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(da2);
            record.setCreateTime(createTime);
        }
        return list;

    }

    @Override
    public LoanRecord getById(Integer id) throws ParseException {
        LambdaQueryWrapper<LoanRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LoanRecord::getId,id);
        LoanRecord record = getOne(wrapper);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da = (Date) sdf.parse(record.getExpirationTime());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String exTime = sdf.format(da);
        record.setExpirationTime(exTime);
        SimpleDateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date da2 = (Date) sdf2.parse(record.getCreateTime());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(da2);
        record.setCreateTime(createTime);
        return record;

    }

    @Override
    public Integer freeFine(Integer id) throws ParseException {
        //查询id和罚金

        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null) {
            return 0;
        }
        Integer clientid=loan1.getClientId();
        Double fine=loan1.getFine();
        if(fine != null && fine > 0 ) {

            //更新
            loan1.setFine(0.0);
            loan1.setClientId(clientid);
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
    public Integer repayTotal(Integer id) throws ParseException {
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null) {
            return -1;
        }
        Double fine=loan1.getFine();
        if(fine>0){
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
    public Integer repay(Integer id,Double account) throws ParseException {
        if(account<0){
            return -1;
        }
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        if(loan1==null) {
            return -1;
        }
        Double fine=loan1.getFine();
        if(fine>0){
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


        String str="-"+account;

        waterLogService.createWaterLog(clientid,str,1);
        accountService.reduceAccountLoad(clientid,account);


        return ret;

    }
    @Override
    public Integer updateDate(Date date){


        LambdaQueryWrapper<LoanRecord> loanRecordQueryWrapper = new QueryWrapper<LoanRecord>().lambda();
        loanRecordQueryWrapper.eq(LoanRecord::getStatus,0);
        List<LoanRecord> loanRecord=loanRecordMapper.selectList(loanRecordQueryWrapper);
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.DATE, -1);
        Date dateNew = cld.getTime();
        for(LoanRecord loanRecord1:loanRecord){
            try {
                Date dueDate = sdf1.parse(loanRecord1.getExpirationTime());
                if((dueDate.before(date)||dueDate==date)&&dateNew.before(dueDate)){
                    double fine=loanRecord1.getCurrentAccount()*0.05;
                    updateLoanFine(loanRecord1.getId(),fine);
                }
                if(dueDate.before(date)){
                    double fine=loanRecord1.getFine();
                    Account account=accountService.getAccountById(loanRecord1.getClientId());
                    if(account.getBalance()>=fine){
                        if(fine>0){
                            accountService.reduceAccountBalance(loanRecord1.getClientId(),fine);
                            String str="-"+fine;
                            waterLogService.createWaterLog(account.getId(),str,2);
                            updateLoanFine(loanRecord1.getId(),0);
                        }


                        if(account.getBalance()-fine>=loanRecord1.getCurrentAccount()){
                            accountService.reduceAccountBalance(loanRecord1.getClientId(),loanRecord1.getCurrentAccount());
                            repayTotal(loanRecord1.getId());
                        }

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
