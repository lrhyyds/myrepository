package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Logisticstype;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/23 19:36
 * @description：
 */
public interface IAdminLogtypeDao {
    List<Logisticstype> getAllLogType();//得到所有的物流类型
}
