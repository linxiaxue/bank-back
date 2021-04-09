package com.bank.bank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuwen
 * @since 2021-03-28
 */
@TableName("water_log")
@ApiModel(value = "银行流水表")
public class WaterLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "客户id")
    private Integer clientId;

    /**
     * 余额变动情况，e.g -100，+100
     */
    @ApiModelProperty(value = "余额变动情况，eg. -100，+100")
    private String accountChange;

    /**
     * 业务类型：0，存款；1，偿还贷款；2，扣除罚金；3，购买理财产品
     */
    @ApiModelProperty(value = "业务类型：0，存款；1，偿还贷款；2，扣除罚金；3，购买理财产品")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    public String getAccountChange() {
        return accountChange;
    }

    public void setAccountChange(String accountChange) {
        this.accountChange = accountChange;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WaterLog{" +
            "id=" + id +
            ", createTime=" + createTime +
            ", clientId=" + clientId +
            ", accountChange=" + accountChange +
            ", type=" + type +
            ", desc=" + remark +
        "}";
    }
}
