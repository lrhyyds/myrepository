package com.nb.yyds220118.control;

import com.github.pagehelper.PageInfo;
import com.nb.yyds220118.pojo.Logisticstype;

import com.nb.yyds220118.service.IAdminLogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/23 19:43
 * @description：物流类型
 */
@RestController
public class AdminLogtypeControl {
    @Autowired
    IAdminLogTypeService iAdminLogTypeService;
    @RequestMapping("getallLogtype")
    public List<Logisticstype> getalllogtype(){
      List<Logisticstype> logisticstypes=  iAdminLogTypeService.getAllLogType();
      return logisticstypes;
    }
}
