package com.bank.bank.service;

import com.bank.bank.dto.ClientProductRequestDto;
import com.bank.bank.entity.FinanceProduct;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
public interface FinanceProductService extends IService<FinanceProduct> {

    List<FinanceProduct> getProducts();

    List<FinanceProduct> getProById(Integer clientId);

    String buyPro(ClientProductRequestDto clientProductRequestDto);
}
