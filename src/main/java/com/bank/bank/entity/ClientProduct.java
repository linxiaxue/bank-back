package com.bank.bank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@TableName("client_product")
@ApiModel(value = "客户理财产品关联表")
@Data
public class ClientProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Integer clientId;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private Integer fpdId;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间")
    private String buyTime;

    /**
     * 本金
     */
    @ApiModelProperty(value = "本金")
    private Double principal;

    /**
     * 利率（对于定期
     */
    @ApiModelProperty(value = "利率，对于定期")
    private Double interestRate;

    /**
     * 收益
     */
    @ApiModelProperty(value = "收益" )
    private Double profit;

    /**
     * 类型，0为定期，1为股票
     */
    @ApiModelProperty(value = "类型，0为定期，1为股票")
    private Integer type;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间，对于定期")
    private String expirationTime;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String fpdName;


    @Override
    public String toString() {
        return "ClientProduct{" +
            "id=" + id +
            ", clientId=" + clientId +
            ", fpdId=" + fpdId +
            ", buyTime=" + buyTime +
            ", principal=" + principal +
            ", interestRate=" + interestRate +
            ", profit=" + profit +
            ", type=" + type +
            ", expirationTime=" + expirationTime +
        "}";
    }
}
