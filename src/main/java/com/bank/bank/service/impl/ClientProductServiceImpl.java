package com.bank.bank.service.impl;

import com.bank.bank.dto.ClientProductResponseDto;
import com.bank.bank.entity.ClientProduct;
import com.bank.bank.mapper.ClientProductMapper;
import com.bank.bank.service.ClientProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ClientProductServiceImpl extends ServiceImpl<ClientProductMapper, ClientProduct> implements ClientProductService {

    @Autowired
    private ClientProductMapper clientProductMapper;

    @Override
    public void computeBenefit(){
        List<ClientProduct> list = clientProductMapper.selectList(null);
        for(ClientProduct clientProduct : list){
            int type = clientProduct.getType();
            if(type == 0){
                Double profit = clientProduct.getPrincipal() *  clientProduct.getInterestRate();
                clientProduct.setProfit(clientProduct.getProfit()+profit);
                saveOrUpdate(clientProduct);
            }else {
                double random = Math.random() * 0.1;
                double profit;
                if( Math.round(Math.random())== 1){
                    profit = (clientProduct.getPrincipal() + clientProduct.getProfit()) *  random ;
                }else {
                    profit = (clientProduct.getPrincipal() + clientProduct.getProfit()) * ( - random);
                }

                clientProduct.setProfit(clientProduct.getProfit() + profit);
                saveOrUpdate(clientProduct);
            }
        }
    }

    @Override
    public List<ClientProductResponseDto> getClientPros(Integer cid) throws ParseException {
        LambdaQueryWrapper<ClientProduct> wrapper = new QueryWrapper<ClientProduct>().lambda();
        wrapper.eq(ClientProduct::getClientId,cid);
        List<ClientProduct> list = getBaseMapper().selectList(wrapper);
        List<ClientProductResponseDto> re = new ArrayList<>();
        for (ClientProduct clientProduct : list){
            ClientProductResponseDto dto = new ClientProductResponseDto();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date da = (Date) sdf.parse(clientProduct.getBuyTime());
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            String buytime = sdf.format(da);
            dto.setBuyTime(buytime);
            dto.setClientId(clientProduct.getClientId());
            dto.setFpdId(clientProduct.getFpdId());
            dto.setInterestRate(clientProduct.getInterestRate());
            dto.setPrincipal(clientProduct.getPrincipal());
            dto.setProfit(clientProduct.getProfit());
            dto.setType(clientProduct.getType());
            dto.setHold(dto.getPrincipal() + dto.getProfit());
            re.add(dto);
        }
        return re;

    }
}
