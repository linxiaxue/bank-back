package com.bank.bank.service.impl;

import com.bank.bank.entity.Account;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.mapper.WaterLogMapper;
import com.bank.bank.service.WaterLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
public class WaterLogServiceImpl extends ServiceImpl<WaterLogMapper, WaterLog> implements WaterLogService {

    @Autowired
    private WaterLogMapper waterLogMapper;

//    private Integer getfirstWaterid(){
//        try{
//            QueryWrapper<WaterLog> waterLogQueryWrapper=new QueryWrapper<>();
//            waterLogQueryWrapper.orderByDesc("id");
//            WaterLog waterLog=waterLogMapper.selectOne(waterLogQueryWrapper);
//            Integer ret= waterLog.getId()+1;
//            return ret;
//        }catch (Exception e){
//            System.out.println(e);
//
//        }
//        return 1;
//
//
//    }

    @Override
    public void createWaterLog(Integer clientid, String accountChange, Integer type){
        WaterLog waterLog=new WaterLog();
        waterLog.setAccountChange(accountChange);
        waterLog.setClientId(clientid);
        Date date=new Date();

        waterLog.setCreateTime(date.toString());
        waterLog.setType(type);
        saveOrUpdate(waterLog);

    }

    @Override
    public List<WaterLog> getList(Integer clientId){
        LambdaQueryWrapper<WaterLog> wrapper = new QueryWrapper<WaterLog>().lambda();
        wrapper.eq(WaterLog::getClientId,clientId);
        return waterLogMapper.selectList(wrapper);
    }

    @Override
    public List<WaterLog> findByWID(Integer clientId,Integer wid){
        LambdaQueryWrapper<WaterLog> wrapper = new QueryWrapper<WaterLog>().lambda();
        wrapper.eq(WaterLog::getClientId,clientId);
        wrapper.eq(WaterLog::getId,wid);
        return waterLogMapper.selectList(wrapper);
    }
}
