package com.bank.bank.service.impl;

import com.bank.bank.entity.LoanRecord;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.mapper.LoanRecordMapper;
import com.bank.bank.mapper.WaterLogMapper;
import com.bank.bank.service.LoanRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
public class LoanRecordServiceImpl extends ServiceImpl<LoanRecordMapper, LoanRecord> implements LoanRecordService {

    @Autowired
    private LoanRecordMapper loanRecordMapper;

    @Autowired
    private WaterLogMapper waterLogMapper;

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
        Integer clientid=loan1.getClientId();
        Double fine=loan1.getFine();
        if(fine==null||fine<=0){
            return -1;
        }
        //更新
        LoanRecord loanRecord=new LoanRecord();
        loanRecord.setFine(0.0);
        UpdateWrapper<LoanRecord> loan=new UpdateWrapper<>();
        loan.eq("id",id).gt("fine",0.0);
        int ret=loanRecordMapper.update(loanRecord,loan);
        if(ret>0){
            WaterLog waterLog=new WaterLog();
            String str="-"+fine;
            waterLog.setAccountChange(str);
            waterLog.setClientId(clientid);
            Date date=new Date();
            waterLog.setCreateTime(date.toString());
            waterLog.setType(2);
            int result=waterLogMapper.insert(waterLog);

        }
        return ret;


    }

    @Override
    public Integer repayTotal(Integer id){
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
        Double fine=loan1.getFine();
        if(fine==null||fine>0){
            return -1;
        }
        Integer clientid=loan1.getClientId();
        Double currentAccount=loan1.getCurrentAccount();
        LoanRecord loanRecord=new LoanRecord();
        loanRecord.setCurrentAccount(0.0);
        loanRecord.setStatus(1);
        UpdateWrapper<LoanRecord> loan=new UpdateWrapper<>();
        loan.eq("id",id);
        int ret=loanRecordMapper.update(loanRecord,loan);
        if(ret>0){
            WaterLog waterLog=new WaterLog();
            String str="-"+currentAccount;
            waterLog.setAccountChange(str);
            waterLog.setClientId(clientid);
            Date date=new Date();
            waterLog.setCreateTime(date.toString());
            waterLog.setType(1);
            int result=waterLogMapper.insert(waterLog);

        }
        return ret;
    }
    @Override
    public Integer repay(Integer id,Double account){
        //查询clientid和总金额
        QueryWrapper<LoanRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        LoanRecord loan1 = loanRecordMapper.selectOne(queryWrapper);
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
        if(currentAccount==account){
            loanRecord.setStatus(1);
        }

        UpdateWrapper<LoanRecord> loan=new UpdateWrapper<>();
        loan.eq("id",id);
        int ret=loanRecordMapper.update(loanRecord,loan);
        if(ret>0){
            WaterLog waterLog=new WaterLog();
            String str="-"+account;
            waterLog.setAccountChange(str);
            waterLog.setClientId(clientid);
            Date date=new Date();
            waterLog.setCreateTime(date.toString());
            waterLog.setType(1);
            int result=waterLogMapper.insert(waterLog);

        }
        return ret;

    }
}
