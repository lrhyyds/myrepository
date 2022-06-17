package com.nb.yyds220118.service.impl;

import com.nb.yyds220118.dao.IAdminOrdersDao;
import com.nb.yyds220118.pojo.Orders;
import com.nb.yyds220118.service.IAdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 11:21
 * @description：订单Service
 */
@Service
public class AdminOrderServiceImpl implements IAdminOrderService {
    @Autowired
    IAdminOrdersDao iAdminOrdersDao;
    //用户个人订单查询
    @Override
    public List<Orders> getAllOrders(int status,int uno) {
        System.out.println("进入getAllOrder");
        List<Orders> list=iAdminOrdersDao.getAllOrders(status,uno);
        System.out.println("没问题");
        return list;
    }
  //修改订单发货状态
    @Override
    public int updateOstastus(int ostatus,int oid) {
        int m=iAdminOrdersDao.updateOstastus(ostatus,oid);
        return m;
    }
//模糊查询订单
    @Override
    public List<Orders> getfuzzyOrdersByoid(String str,int status) {
       List<Orders> list= iAdminOrdersDao.getfuzzyOrdersByoid(str,status);
        return list;
    }
//批量删除订单
    @Override
    public int delbatchorders(List<Integer> list) {
       return  iAdminOrdersDao.delbatchorders(list);

    }
/*
* 根据日期范围和发货状态查询订单
* */
    @Override
    public List<Orders> getordersBydate(String startdate, String enddate, int status,String str,Integer uno) {
      List<Orders> list= iAdminOrdersDao.getordersBydate(startdate,enddate,status,str,uno);
        return list;
    }
}
