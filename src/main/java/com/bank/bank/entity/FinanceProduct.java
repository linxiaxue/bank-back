package com.bank.bank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@TableName("finance_product")
@ApiModel(value = "理财产品")
public class FinanceProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类型，0为定期，1为股票,2为基金
     */
    @ApiModelProperty(value = "类型，0为定期，1为股票,2为基金")
    private Integer type;

    /**
     * 价格，定期的金额或股票的每股价格
     */
    @ApiModelProperty(value = "价格，对于股票是每股的价格")
    private Double price;

    /**
     * 日利率，单位为 %
     */
    @ApiModelProperty(value = "日利率，小数形式")
    private Double interestRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public String toString() {
        return "FinanceProduct{" +
            "id=" + id +
            ", name=" + name +
            ", type=" + type +
            ", price=" + price +
            ", interestRate=" + interestRate +
        "}";
    }
}
