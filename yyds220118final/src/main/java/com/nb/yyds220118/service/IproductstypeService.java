package com.nb.yyds220118.service;


import com.nb.yyds220118.pojo.ProductsType;
import com.nb.yyds220118.util.PageTable;


import java.math.BigDecimal;
import java.util.List;

public interface IproductstypeService {
    PageTable<ProductsType> list(int page, int limit);
    int delete(int tid);
    List<ProductsType> list1();

    int add1(String tname, BigDecimal tisparenttype, BigDecimal tparenttypeid);

    int update1(Integer tid, String tname, BigDecimal tisparenttype, BigDecimal tparenttypeid);
    //用来改父类名字
    PageTable<ProductsType> select(int page, int limit);
    ProductsType getparentname(String tname, Integer tid);
}
