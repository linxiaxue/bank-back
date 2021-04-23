package com.bank.bank.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@ApiModel("客户理财产品表请求对象")
public class ClientProductRequestDto {
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
     * 本金
     */
    @ApiModelProperty(value = "本金")
    private Double principal;

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

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }
}
