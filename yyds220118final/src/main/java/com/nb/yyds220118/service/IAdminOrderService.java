package com.nb.yyds220118.service;

import com.nb.yyds220118.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 11:20
 * @description：
 */
public interface IAdminOrderService {
    List<Orders> getAllOrders(int ostatus,int uno);//用户个人订单查询

    int updateOstastus(int ostatus, int oid);//修改订单状态

    List<Orders> getfuzzyOrdersByoid(String str, int status);//根据oid模糊查询

    int delbatchorders(List<Integer> list);//批量删除订单

    //根据日期范围和状态查询订单 ,根据oid模糊查询
    List<Orders> getordersBydate(String startdate,
                                 String enddate,
                                 int status,
                                 String str,
                                 Integer uno);
}
