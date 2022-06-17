package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminLogisticsDao;
import com.nb.yyds220118.pojo.Logistics;
import com.nb.yyds220118.service.IAdminLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 12:39
 * @description：物流信息Service
 */
@Service
public class AdminLogisticsServiceImpl implements IAdminLogisticsService {
@Autowired
    IAdminLogisticsDao iAdminLogisticsDao;
/*
* 查询所有物流信息
* */
    @Override
    public List<Logistics> getAllLogis() {
     List<Logistics> logistics=   iAdminLogisticsDao.getAllLogis();
        return logistics;
    }
/*
* 增加物流信息
* */
    @Override
    public int addLogistics(Logistics logistics) {
      int m=  iAdminLogisticsDao.addLogistics(logistics);
        return m;
    }
/*
* 根据订单表编号查询物流信息
* */
    @Override
    public List<Logistics> getlogInfo(int oid) {
      List<Logistics> logisticsList=  iAdminLogisticsDao.getlogInfo(oid);
        return logisticsList;
    }
}
