package com.nb.yyds220118.dao;

import com.nb.yyds220118.pojo.ProductsType;

import java.util.List;

/**
 * @author ：yyds
 * @date ：Created in 2022/1/18 20:24
 * @description：
 */
public interface IProTypeDao {
    List<ProductsType> getprotype();//查询商品类别
    String gettnameBytid(int tid);//查询类型名称
}
