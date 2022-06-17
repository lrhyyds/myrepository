package com.nb.yyds220118.service;

import com.github.pagehelper.PageInfo;

import com.nb.yyds220118.pojo.Products;
import com.nb.yyds220118.pojo.Products2;
import com.nb.yyds220118.pojo.ProductsType;
import com.nb.yyds220118.util.PageTable;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IproductsService2 {
//    PageTable<Products> list(int page,int limit);
    PageTable<Products2> biglist(int page, int limit);
    PageTable<ProductsType> biglist1(int page, int limit);
    int del(int pid);
    int add(String pname, BigDecimal pprice, BigDecimal pnum, Integer tid);

    int update(int pid, String pname, BigDecimal pprice, BigDecimal pnum);
    int addProductstype(int tid);
    int getIdByName(String pname);
    PageTable<Products2> desc(int page, int limit);
    PageInfo<Products2> select1(int page, int limit);
    Double getfourMonth();

}
