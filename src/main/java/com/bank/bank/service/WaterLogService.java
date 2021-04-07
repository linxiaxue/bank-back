package com.bank.bank.service;

import com.bank.bank.entity.WaterLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
public interface WaterLogService extends IService<WaterLog> {

    void createWaterLog(Integer clientid, String accountChange, Integer type);

    List<WaterLog> getList(Integer clientId);
}

