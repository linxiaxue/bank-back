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
}
