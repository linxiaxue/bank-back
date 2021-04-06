package com.bank.bank.service.impl;

import com.bank.bank.entity.Account;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.mapper.WaterLogMapper;
import com.bank.bank.service.WaterLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class WaterLogServiceImpl extends ServiceImpl<WaterLogMapper, WaterLog> implements WaterLogService {
    private Integer sum;
    @Autowired
    private WaterLogMapper waterLogMapper;

    private Integer getfirstWaterid(){
        try{
            QueryWrapper<WaterLog> waterLogQueryWrapper=new QueryWrapper<>();
            waterLogQueryWrapper.orderByDesc("id");
            WaterLog waterLog=waterLogMapper.selectOne(waterLogQueryWrapper);
            Integer ret= waterLog.getId()+1;
            return ret;
        }catch (Exception e){
            System.out.println(e);

        }
        return 1;


    }
    @Override
    public Integer createWaterLog(Integer clientid,String account_change,Integer type){
        WaterLog waterLog=new WaterLog();
        if(sum==null){
            sum=getfirstWaterid();
        }

        waterLog.setId(sum);
        sum++;

        waterLog.setAccountChange(account_change);
        waterLog.setClientId(clientid);
        Date date=new Date();
        waterLog.setCreateTime(date.toString());
        waterLog.setType(type);
        int result=waterLogMapper.insert(waterLog);
        return result;
    }
}
