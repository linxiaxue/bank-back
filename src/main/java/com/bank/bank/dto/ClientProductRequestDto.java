package com.bank.bank.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
