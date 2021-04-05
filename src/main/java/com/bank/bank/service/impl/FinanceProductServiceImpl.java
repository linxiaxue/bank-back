package com.bank.bank.service.impl;

import com.bank.bank.entity.FinanceProduct;
import com.bank.bank.mapper.FinanceProductMapper;
import com.bank.bank.service.FinanceProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@Service
public class FinanceProductServiceImpl extends ServiceImpl<FinanceProductMapper, FinanceProduct> implements FinanceProductService {

    @Override
    public Page<FinanceProduct> getProducts() {
        return null;
    }
}
