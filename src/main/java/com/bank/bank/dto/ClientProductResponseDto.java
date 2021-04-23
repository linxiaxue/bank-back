package com.bank.bank.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClientProductResponseDto {

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
     * 本金（购买时的
     */
    @ApiModelProperty(value = "本金")
    private Double principal;

    /**
     * 持仓
     */
    @ApiModelProperty(value = "持仓")
    private Double hold;

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

    public Double getHold() {
        return hold;
    }

    public void setHold(Double hold) {
        this.hold = hold;
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
