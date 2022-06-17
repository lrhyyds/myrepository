package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/19 11:08
 * @description：
 */
public interface IAdminOrdersDao {
    List<Orders> getAllOrders(@Param("oStatus") Integer oStatus,
                              @Param("uno") Integer uno);//用户个人订单查询
    int updateOstastus(@Param("ostatus") int ostatus,@Param("oid") int oid);//修改订单状态
    List<Orders> getfuzzyOrdersByoid(@Param("str") String str,@Param("status") int status);//根据oid模糊查询
    int delbatchorders(@Param("list") List<Integer> list);//批量删除新闻
    String getonameByoid(int oId);//物流信息关联查询名字
    //根据日期范围和状态查询订单 ,
    List<Orders> getordersBydate(@Param("startdate") String startdate,
                                 @Param("enddate") String enddate,
                                 @Param("status") int status,
                                 @Param("str") String str,
                                 @Param("uno") Integer uno);

}
