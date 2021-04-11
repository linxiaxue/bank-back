package com.bank.bank.service;

import com.bank.bank.dto.ClientProductResponseDto;
import com.bank.bank.entity.ClientProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
public interface ClientProductService extends IService<ClientProduct> {

    void computeBenefit();


    List<ClientProductResponseDto> getClientPros(Integer cid) throws ParseException;
}
