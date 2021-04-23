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
@TableName("loan_record")
@ApiModel(value = "贷款记录")
@Data
public class LoanRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    /**
     * 到期时间
     */
    @ApiModelProperty(value = "到期时间")
    private String expirationTime;

    /**
     * 状态：0，未还；1，已还
     */
    @ApiModelProperty(value = "状态，0：未还，1：已还")
    private Integer status;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private Integer clientId;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private Double totalAccount;

    /**
     * 本期待还金额
     */
    @ApiModelProperty(value = "本期待还金额")
    private Double currentAccount;

    /**
     * 期数
     */
    @ApiModelProperty(value = "期数，可忽略")
    private Integer nper;

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    /**
     * 罚金：0为无罚金或已还清
     */
    @ApiModelProperty(value = "罚金：0为无罚金或已还清")
    private Double fine;



    @Override
    public String toString() {
        return "LoanRecord{" +
            "id=" + id +
            ", createTime=" + createTime +
            ", expirationTime=" + expirationTime +
            ", status=" + status +
            ", clientId=" + clientId +
            ", totalAccount=" + totalAccount +
            ", currentAccount=" + currentAccount +
            ", nper=" + nper +
            ", fine=" + fine +
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Double getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(Double totalAccount) {
        this.totalAccount = totalAccount;
    }

    public Double getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Double currentAccount) {
        this.currentAccount = currentAccount;
    }

    public Integer getNper() {
        return nper;
    }

    public void setNper(Integer nper) {
        this.nper = nper;
    }
}
