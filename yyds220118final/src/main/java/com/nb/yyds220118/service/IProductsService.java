package com.nb.yyds220118.service;

import com.nb.yyds220118.pojo.Products;
import com.nb.yyds220118.util.PageTable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : lq
 * @date : Created in 2022/1/18 20:25
 * @description：
 */
public interface IProductsService {
    //得到购物车的信息
    PageTable<Products> getMyShoppingCar(int uno,int page,int limit);
    //得到购物车的信息 jquery
    List<Products> getMyShoppingCarjq(int uno,int page,int limit);
    //删除选中的购物车信息
    Integer delCheckedProducts(List<Products> list,int uno);
    //删除选中的购物车信息 jq
    Integer delCheckedProductsjq(int carid,int pid,int uno);
    //根据pid得到商品信息
    Products getProductsByPid(Integer pid);
    //增加购物车的商品
    Integer addShoppingCar(int pid,int uno,int upnum);
    //随机得到商品
    List<Products> randomGetProduct();
}
