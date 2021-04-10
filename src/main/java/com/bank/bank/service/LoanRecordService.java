package com.bank.bank.service;

import com.bank.bank.entity.LoanRecord;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import javax.transaction.Transactional;
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

    List<LoanRecord> getByUserId(Integer id);

    LoanRecord getById(Integer id);

    @Transactional
    Integer freeFine(Integer id);

    @Transactional
    Integer repayTotal(Integer id);

    @Transactional
    Integer repay(Integer id,Double account);

    @Transactional
    Integer updateDate(Date date);

}
