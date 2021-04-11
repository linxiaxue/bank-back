package com.bank.bank.service;

import com.bank.bank.entity.LoanRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
public interface LoanRecordService extends IService<LoanRecord> {

    List<LoanRecord> getByUserId(Integer id) throws ParseException;

    LoanRecord getById(Integer id) throws ParseException;

    @Transactional
    Integer freeFine(Integer id) throws ParseException;

    @Transactional
    Integer repayTotal(Integer id) throws ParseException;

    @Transactional
    Integer repay(Integer id,Double account) throws ParseException;

    @Transactional
    Integer updateDate(Date date);

}
