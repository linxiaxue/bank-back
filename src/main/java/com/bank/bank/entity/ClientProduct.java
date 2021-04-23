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


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getFpdId() {
        return fpdId;
    }

    public void setFpdId(Integer fpdId) {
        this.fpdId = fpdId;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getFpdName() {
        return fpdName;
    }

    public void setFpdName(String fpdName) {
        this.fpdName = fpdName;
    }
}
