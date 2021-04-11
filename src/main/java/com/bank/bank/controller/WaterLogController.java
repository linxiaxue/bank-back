package com.bank.bank.controller;


import com.bank.bank.entity.WaterLog;
import com.bank.bank.service.WaterLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

/**
 *
 *  流水
 *
 *
 * @author yuwen
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/bank/water-log")
public class WaterLogController {
    @Autowired
    public WaterLogService waterLogService;

    /**
     * 查询账户流水
     */
    @GetMapping("/{id}")
    @ApiOperation("查询账户流水")
    public List<WaterLog> getList(@PathVariable(value = "id")@ApiParam(value = "账户id" ) Integer id) throws ParseException {
        return waterLogService.getList(id);
    }

    /**
     * 查询账户流水
     */
    @GetMapping("/{id}/{wid}")
    @ApiOperation("查询账户流水")
    public List<WaterLog> findByWID(@PathVariable(value = "id")@ApiParam(value = "账户id" ) Integer id,
                                  @PathVariable(value = "wid")@ApiParam(value = "流水id" ) Integer wid){
        return waterLogService.findByWID(id,wid);
    }


}
