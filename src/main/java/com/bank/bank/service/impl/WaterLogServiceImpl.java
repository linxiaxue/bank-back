package com.bank.bank.service.impl;

import com.bank.bank.entity.Account;
import com.bank.bank.entity.WaterLog;
import com.bank.bank.mapper.AccountMapper;
import com.bank.bank.mapper.WaterLogMapper;
import com.bank.bank.service.AccountService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private AccountService accountService;

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
    public void createWaterLog(Integer clientid, String accountChange, Integer type) throws ParseException {
        WaterLog waterLog=new WaterLog();
        waterLog.setAccountChange(accountChange);
        waterLog.setClientId(clientid);
        Date date=new Date();
        //Tue Apr 06 17:44:42 CST 2021
        Date nowDate=accountService.getNowTime();

        waterLog.setCreateTime(nowDate.toString());
        waterLog.setType(type);
        saveOrUpdate(waterLog);

    }

    @Override
    public List<WaterLog> getList(Integer clientId) throws ParseException {
        LambdaQueryWrapper<WaterLog> wrapper = new QueryWrapper<WaterLog>().lambda();
        wrapper.eq(WaterLog::getClientId,clientId);
        List<WaterLog> list = waterLogMapper.selectList(wrapper);
        for(WaterLog log : list){
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date da = (Date) sdf.parse(log.getCreateTime().toString());
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = sdf.format(da);
            log.setCreateTime(createTime);
        }
        return list;
    }

    @Override
    public List<WaterLog> findByWID(Integer clientId,Integer wid){
        LambdaQueryWrapper<WaterLog> wrapper = new QueryWrapper<WaterLog>().lambda();
        wrapper.eq(WaterLog::getClientId,clientId);
        wrapper.eq(WaterLog::getId,wid);
        return waterLogMapper.selectList(wrapper);
    }
}
