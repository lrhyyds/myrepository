package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminLogtypeDao;
import com.nb.yyds220118.pojo.Logisticstype;
import com.nb.yyds220118.service.IAdminLogTypeService;
import com.nb.yyds220118.service.IAdminLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/23 19:41
 * @description：物流类型Service
 */
@Service
public class AdminLogTypeServiceImpl implements IAdminLogTypeService {
    @Autowired
    IAdminLogtypeDao iAdminLogtypeDao;
    /*
    * 得到所有的物流类型
    * */
    @Override
    public List<Logisticstype> getAllLogType() {
        List<Logisticstype> logisticstypes=iAdminLogtypeDao.getAllLogType();
        return logisticstypes;
    }
}
