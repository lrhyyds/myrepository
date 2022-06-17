package com.nb.yyds220118.dao;


import com.nb.yyds220118.pojo.Products2;
import com.nb.yyds220118.pojo.ProductsType;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IproductsDao2 {
    //List<Products>sel();
    List<ProductsType> getproductstype();

    List<Products2> sel1();

    int del(int pid);

    int add(@Param("pname") String pname,
            @Param("pprice") BigDecimal pprice,
            @Param("pnum") BigDecimal pnum,
            @Param("tid") Integer tid);

    int update(int pid, String pname, BigDecimal pprice, BigDecimal pnum);

    int addProductstype(int tid);

    int getIdByName(String pname);

    List<Products2> desc();

    Double getfourMonth();
}
