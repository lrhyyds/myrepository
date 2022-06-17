package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.ProductsType;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IproductstypeDao {
    List<ProductsType> list();
    int delete(int tid);
    List<ProductsType> list1();
    int add1(@Param("tname") String tname,
             @Param("tisparenttype") BigDecimal tisparenttype,
             @Param("tparenttypeid") BigDecimal tparenttypeid);
    int update1(Integer tid,
                String tname,
                BigDecimal tisparenttype,
                BigDecimal tparenttypeid);
//改父类名字
    List<ProductsType> select();
    ProductsType getparentname(String tname, Integer tid);
    String selparentname(int tparenttypeid);
}
