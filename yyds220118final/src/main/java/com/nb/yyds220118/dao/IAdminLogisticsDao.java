package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Logistics;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 12:30
 * @description：物流信息
 */
public interface IAdminLogisticsDao {
    List<Logistics> getAllLogis();//查询所有物流信息
    int addLogistics(Logistics logistics);//增加物流信息
    List<Logistics> getlogInfo(int oid);//根据订单编号查询物流信息
 }
